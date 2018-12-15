package gxy.flume.source;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import org.apache.flume.Context;
import org.apache.flume.CounterGroup;
import org.apache.flume.Event;
import org.apache.flume.EventDrivenSource;
import org.apache.flume.conf.Configurable;
import org.apache.flume.conf.Configurables;
import org.apache.flume.event.EventBuilder;
import org.apache.flume.source.AbstractSource;
import org.apache.flume.source.SyslogSourceConfigurationConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gxy on 2016/5/30.
 */
public class MySource extends AbstractSource implements EventDrivenSource, Configurable {
    private static final Logger logger = LoggerFactory.getLogger(MySource.class);
    private int port;
    private EventLoopGroup boss;
    private EventLoopGroup worker;
    private CounterGroup counterGroup = new CounterGroup();

    public class MyHandler extends SimpleChannelInboundHandler<Object>{
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            // TODO Auto-generated method stub
            logger.info("Active!!!");
        }        
        
        /*@Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            logger.info("xxxxxxxxxxxxxx");
            
            ByteBuf buf = (ByteBuf) msg;
            byte[] req = new byte[buf.readableBytes()];
            buf.readBytes(req);
            String body = new String(req,"UTF-8");
            ctx.writeAndFlush(msg);
            
            logger.info("xxxxxxxxxxxxxx1");
            while (body != null) {
                logger.info("xxxxxxxxxxxxxx2");
                Event e = EventBuilder.withBody(body, Charset.forName("UTF-8"));
                if (e == null) {
                    logger.debug("Parsed partial event, event will be generated when " +
                            "rest of the event is received.");
                    continue;
                }
                try {
                    getChannelProcessor().processEvent(e);
                    counterGroup.incrementAndGet("events.success");
                    body = null;
                } catch (ChannelException ex) {
                    counterGroup.incrementAndGet("events.dropped");
                    logger.error("Error writting to channel, event dropped", ex);
                } catch (RuntimeException ex) {
                    counterGroup.incrementAndGet("events.dropped");
                    logger.error("Error parsing event from syslog stream, event dropped", ex);
                    return;
                }
            }
        }*/

        protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
            logger.info("receive data:" + msg);
            XMLSerializer xmlSerializer = new XMLSerializer();  
            JSON json = xmlSerializer.read((String) msg);
            logger.info("JSON: \n" + json.toString());
            
            List<String> toKafka= new ArrayList<String>();
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("hbase", "hbase");
            
          //解析成json对象
            JSONObject jo = JSONObject.fromObject(json.toString());
            
            long time = jo.getJSONObject("data").getLong("time");
            logger.info("time: " + time);
            
            JSONArray meterArray = jo.getJSONObject("data").getJSONArray("meter");
            for(int i = 0; i < meterArray.size(); i++) {
                JSONObject meter = (JSONObject)meterArray.get(i);
                String meter_id = meter.getString("@id");
                logger.info("meter_id: " + meter_id);
                
                JSONArray functionArray = meter.getJSONArray("function");
                
                for(int j = 0; j < functionArray.size(); j++) {
                    JSONObject function = (JSONObject)functionArray.get(j);
                    String function_id = function.getString("@id");
                    String coding = function.getString("@coding");
                    String value = function.getString("#text");
                        
                    logger.info("function_id: " + function_id);
                    logger.info("coding: " + coding);
                    logger.info("value: " + value);
                    
                    toKafka.add(time + "," + meter_id + "," + function_id + "," + coding + "," + value);
                }
            }
            
            while (msg != null) {
                Event e = EventBuilder.withBody((String)json.toString(), Charset.forName("UTF-8"), headers);
                if (e == null) {
                    logger.debug("Parsed partial event, event will be generated when " +
                            "rest of the event is received.");
                    continue;
                }
                try {
                    getChannelProcessor().processEvent(e);
                    for(String s: toKafka){
                        Event kafka = EventBuilder.withBody(s, Charset.forName("UTF-8"));
                        getChannelProcessor().processEvent(kafka);
                        logger.info("The tokafka: " + s);
                    }
                    counterGroup.incrementAndGet("events.success");
                    ctx.writeAndFlush("\nanswer: "+ msg);
                    msg = null;
                } catch (ChannelException ex) {
                    counterGroup.incrementAndGet("events.dropped");
                    logger.error("Error writting to channel, event dropped", ex);
                } catch (RuntimeException ex) {
                    counterGroup.incrementAndGet("events.dropped");
                    logger.error("Error parsing event from syslog stream, event dropped", ex);
                    return;
                }
            }
        }

        @Override
        protected void messageReceived(ChannelHandlerContext ctx, Object msg)
                throws Exception {
            // TODO Auto-generated method stub
            
        }
    }

    @Override
    public void start() {
        boss = new NioEventLoopGroup();
        worker = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap=new ServerBootstrap();
            bootstrap.group(boss, worker);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.option(ChannelOption.SO_BACKLOG, 128);
            bootstrap.option(ChannelOption.TCP_NODELAY, true);
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline p = socketChannel.pipeline();
                    ByteBuf delimiter = Unpooled.copiedBuffer("</root>".getBytes());
                    p.addLast(new DelimiterBasedFrameDecoder(30000, false, delimiter));
                    p.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
                    p.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
                    p.addLast(new MyHandler());
                }
            });
        
            ChannelFuture f = bootstrap.bind(port).sync();
            if(f.isSuccess()){
                logger.info("Source starting...");
            }
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        super.start();
    }

    @Override
    public void stop() {
        logger.info("Source stopping...");
        logger.info("Metrics:{}", counterGroup);

        worker.shutdownGracefully();
        boss.shutdownGracefully();

        super.stop();
    }

    public void configure(Context context) {
        Configurables.ensureRequiredNonNull(context,
                SyslogSourceConfigurationConstants.CONFIG_PORT);
        port = context.getInteger(SyslogSourceConfigurationConstants.CONFIG_PORT);
    }
    
    /*public static void main(String[] args) {
        MySource ms = new MySource();
        ms.port = 50000;
        ms.start();
    }*/
}
