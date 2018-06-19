package com.pingan.fast.ping;


import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class PortCheckerClient {
	
	public static void main(String[] args) {
		
		List<String> endpoints = new ArrayList<String>();
		
		List<String> ports = PortCheckerUtil.getEndPoints(endpoints, args[0],args[1]);
		
		ports.forEach(item->{
			 String ip = null;
			 int port = 0;
			 try {
				ip = item.split(":")[0];
				port = Integer.valueOf(item.split(":")[1]);
				Socket socket = new Socket(ip,port);
				InputStream inputStream = socket.getInputStream();
		    byte[] bytes = new byte[1024];
		    int len;
		    StringBuilder sb = new StringBuilder();
		    while ((len = inputStream.read(bytes)) != -1) {
		      //注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
		      sb.append(new String(bytes, 0, len,"UTF-8"));
		    }
		    System.out.println("get message from server: " + sb);
		    
		    inputStream.close();
		    socket.close();
			} catch (Exception e) {
				System.out.println("can not create connection to ["+ip+":"+port+"] and will contiune....");
			}
			
		});
	}

}

