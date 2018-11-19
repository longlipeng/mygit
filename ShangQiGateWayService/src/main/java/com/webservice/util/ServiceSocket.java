package com.webservice.util;

import com.huateng.framework.util.ConfigPosp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ServiceSocket implements Runnable {
	@Override
	public void run() {
		//配置NIO线程组
				EventLoopGroup bossGroup = new NioEventLoopGroup();
				EventLoopGroup workerGroup = new NioEventLoopGroup();
				try {
					//服务器辅助启动类配置
					ServerBootstrap server = new ServerBootstrap();
					server.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(
							new ChannelInitializer<SocketChannel>() {
								@Override
								protected void initChannel(SocketChannel ch) throws Exception {
//									ch.pipeline().addLast(new HttpRequestDecoder());
//									ch.pipeline().addLast(new HttpResponseEncoder());
									ch.pipeline().addLast(new HttpServerInboundHandler());
								}
					}).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);
					//绑定端口 同步等待绑定成功
					int port = Integer.parseInt( ConfigPosp.getListenerPort());
					ChannelFuture f = server.bind(port).sync();
					//等待服务器端监听端口关闭
					f.channel().closeFuture().sync();
				} catch (InterruptedException e) {
				}finally{
					//释放线程资源
					bossGroup.shutdownGracefully();
					workerGroup.shutdownGracefully();
				}
	}		
}
