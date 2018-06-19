package com.pingan.fast.ping;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class PortCheckerServer {
	
	public static void main(String[] args) {
		
		List<String> endpoints = new ArrayList<String>();
		String path = args[0];
		List<String> ports = PortCheckerUtil.getPorts(endpoints, path);
	
		ports.forEach((item)->{
			new Thread(new Waiter(item)).start();
      System.out.println("the checker "+item+" is runnning.....");
		});
	}

}

class Waiter implements Runnable{
	
	int port;
	
	public Waiter(String _port) {
		port = Integer.valueOf(_port);
	}
	
	public void run() {
		
		ServerSocket server = null;
		Socket socket = null;
		OutputStream outputStream = null;
		try {
			server = new ServerSocket(port);
			while(true) {
				socket = server.accept();
				outputStream = socket.getOutputStream();
				String ack = "Hello Client,the port "+port+"is work";
				// 建立好连接后，从socket中获取输入流，并建立缓冲区进行读取
				outputStream.write(ack.getBytes("UTF-8"));	
				outputStream.close();
        socket.close();
			}
			
		} catch (Exception e) {
			System.out.println("can not listen the port :"+port+" will continue to next");
		}finally{
			 try {
			 server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
