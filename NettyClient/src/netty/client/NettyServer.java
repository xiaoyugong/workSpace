package netty.server;

import io.netty.bootstrap.ServerBootstrap;
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
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Netty服务器端
 * @author gxy
 */
public class MyServer {
	
	//端口
	private int port;
	
	public MyServer(int port) throws Exception {
		this.port = port;
		run();
	}
	
	public void run() throws Exception {
		// 服务器线程组 用于网络事件的处理 一个用于服务器接收客户端的连接
        // 另一个线程组用于处理SocketChannel的网络读写
		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();
		try {
			// NIO服务器端的辅助启动类
			ServerBootstrap server = new ServerBootstrap();
			server.group(boss, worker)
			            .channel(NioServerSocketChannel.class)
			            .childHandler(new ChildChannelHandler())                         //绑定I/O事件的处理类   处理网络IO事件
			            .option(ChannelOption.SO_BACKLOG, 128)                        // 配置TCP参数
			            .childOption(ChannelOption.SO_KEEPALIVE, true); 
			// 服务器启动后 绑定监听端口 同步等待成功 主要用于异步操作的通知回调 回调处理用的ChildChannelHandler
            ChannelFuture f = server.bind(port).sync();
            if (f.isSuccess()) {
            	System.out.println("Server启动");
            }
            // 等待服务端监听端口关闭
            f.channel().closeFuture().sync();
		} finally {
			// 释放线程池资源
            boss.shutdownGracefully();
            worker.shutdownGracefully();
		}
	}
	
	/**
     * 添加网络事件处理器
     */
    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new MyServerHandler());
        }
    }
    
    /**
     * 网络事件处理器
     */
    private class MyServerHandler extends ChannelHandlerAdapter {
    	
    	public void channelRegistered(ChannelHandlerContext ctx) {
    		System.out.println("有客户端链接");
    	}
    	
    	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    		System.out.println("服务器读取到客户端请求...");
            ByteBuf buf = (ByteBuf) msg;
            byte[] req = new byte[buf.readableBytes()];
            buf.readBytes(req);
            String body = new String(req,"UTF-8");
            System.out.println("客户端消息:\n"+body);
            ByteBuf resp = Unpooled.copiedBuffer("hello client".getBytes());
            ctx.writeAndFlush(resp);
    	}
        
    	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.close();
            System.out.println("服务器响应完成");
        }
        
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			ctx.close();
			System.out.println("服务器异常退出" + cause.getMessage());
		}
    }
    
    public static void main(String[] args) throws Exception {
        int port = 8000;
        new MyServer(port);
    }
    
}

