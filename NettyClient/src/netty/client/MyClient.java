package netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Netty client
 * @author gxy
 */
public class MyClient {

	private String ip;
	private int port;
	private SocketChannel sc;
	
	public MyClient(String ip, int port) throws Exception {
		this.ip = ip;
		this.port = port;
		run();
	}
	
	public void run() throws Exception {
		EventLoopGroup worker =  new NioEventLoopGroup();
		try{
			Bootstrap client = new Bootstrap();
			client.group(worker)
			      .channel(NioSocketChannel.class)
			      .option(ChannelOption.SO_KEEPALIVE, true)
			      .option(ChannelOption.TCP_NODELAY, true)
			      .handler(new ChannelHandler());
			ChannelFuture f = client.connect(ip,port).sync();
			System.out.println("Client success");
			sc = (SocketChannel) f.channel();
			f.channel().closeFuture().sync();
		} finally {
			worker.shutdownGracefully();
		}
	}
	
	private class ChannelHandler extends ChannelInitializer<SocketChannel> {
		public void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new MyClientHandler());
            sc = ch;
        }
	}
	
	private class MyClientHandler extends ChannelHandlerAdapter {
		
		private ByteBuf msg;
		public void channelActive(ChannelHandlerContext ctx) throws Exception {	
			System.out.println("active");
			FileInputStream in = new FileInputStream("/home/gxy/input/flumedata");
			BufferedReader buf = new BufferedReader(new InputStreamReader(in));
			String data = null;
			String line = null;
			boolean flag = true;
			while((line = buf.readLine()) != null){
				if (flag) {
					data = line;
					flag = false;
				} else {
					data = data + "\n" + line;
				}
			}
			in.close();
			byte[] s = data.getBytes();
			msg = Unpooled.buffer(s.length);
			msg.writeBytes(s);
			
			/*byte[] req = new byte[msg.readableBytes()];
			msg.readBytes(req);
            String body = new String(req,"UTF-8");
			System.out.println("send data: " + body);*/
			
			ctx.writeAndFlush(msg);
		}
		
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			System.out.println("recevice answer");
	        ByteBuf buf = (ByteBuf) msg;
	        byte[] req = new byte[buf.readableBytes()];
	        buf.readBytes(req);
	        String body = new String(req,"UTF-8");
			System.out.println("answer:\n"+body);
		}

		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
			cause.printStackTrace();
			ctx.close();
			System.out.println("exception:"+cause.getMessage());
		}
	}
	
	public static void main(String args[]) throws Exception {
		String ip = "localhost";
		int port = 50000;
		MyClient mc = new MyClient(ip, port);
//		mc.sc.writeAndFlush(Unpooled.copiedBuffer("xxxxxxxx".getBytes()));
//		while(true){
//			
//			Thread.sleep(2000);
//		}

	}
}
