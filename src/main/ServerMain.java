package main;
import hander.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class ServerMain {
	
	static final int PORT = Integer.parseInt(System.getProperty("port", "8007"));
        
    public static void main(String[] args) throws Exception {
    	
	      EventLoopGroup bossGroup = new NioEventLoopGroup();
	        EventLoopGroup workerGroup = new NioEventLoopGroup();
	        try {
	            ServerBootstrap b = new ServerBootstrap();
	            b.group(bossGroup, workerGroup)
	             .channel(NioServerSocketChannel.class)
	             .handler(new LoggingHandler(LogLevel.INFO))
	             .childHandler(new ChannelInitializer<SocketChannel>() {
	                 @Override
	                 public void initChannel(SocketChannel ch) throws Exception {
	                	 ChannelPipeline p = ch.pipeline();	                     
	                     p.addLast(new ServerHandler());
	                 }
	             })
	             .option(ChannelOption.SO_BACKLOG, 128)
	             .childOption(ChannelOption.SO_KEEPALIVE, true);
	    
	            // Bind and start to accept incoming connections.
	            ChannelFuture f = b.bind(PORT).sync();
	    
	            // Wait until the server socket is closed.
	            // In this example, this does not happen, but you can do that to gracefully
	            // shut down your server.
	            f.channel().closeFuture().sync();
	        } finally {
	            workerGroup.shutdownGracefully();
	            bossGroup.shutdownGracefully();
	        }
    }
}
