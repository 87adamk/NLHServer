package hander;

import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.UUID;

import main.ClientTest;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter{

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
			
		String sendMessage = "Hello, Netty!";
		Scanner in = new Scanner(System.in);
		sendMessage = in.next();

		in.close();
	     
	    ByteBuf messageBuffer = Unpooled.buffer();
	    messageBuffer.writeBytes(sendMessage.getBytes());
	     
	    StringBuilder builder = new StringBuilder();
	    builder.append("전송한 문자열 [");
	    builder.append(sendMessage);
	    builder.append("]");
	     
	    System.out.println(builder.toString());
	    ctx.writeAndFlush(messageBuffer);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// TODO Auto-generated method stub
		//3.서버로부터 수신된 데이터가 있을 때 호출되는 메서드
	     
	    String readMessage = ((ByteBuf)msg).toString(Charset.defaultCharset());
	    //4.서버로 부터 수신된 데이터가 저장된 msg 객체에서 문자열 데이터 추출
	     
	    StringBuilder builder = new StringBuilder();
	    builder.append(ctx.channel().id()+"수신한 문자열 [");
	    builder.append(readMessage);
	    builder.append("]");
	     
	    System.out.println(builder.toString());
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		ctx.close();
		
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		cause.printStackTrace();
		ctx.close();
	}
}
