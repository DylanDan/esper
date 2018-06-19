package com.pingan.fast.ping;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PortCheckerUtil {
	
	public static List<String> getPorts(List<String> ports,String path){
		
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(path);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));  
			String str = null;  
			while((str = bufferedReader.readLine()) != null)  
			{  
				ports.add(str); 
			}  
			//close  
			inputStream.close();  
			bufferedReader.close();  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  		
		
		return ports;
	}
	public static List<String> getEndPoints(List<String> list,String path_ip,String path_port){
		
		Set<String> _ports = new HashSet<String>();
		
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(path_port);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));  
			String str = null;  
			while((str = bufferedReader.readLine()) != null)  
			{  
				_ports.add(str); 
			}  
			//close  
			inputStream.close();  
			bufferedReader.close();  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		Set<String> _ips = new HashSet<String>();
	
		try {
			inputStream = new FileInputStream(path_ip);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));  
			String str = null;  
			while((str = bufferedReader.readLine()) != null)  
			{  
				_ips.add(str); 
			}  
			//close  
			inputStream.close();  
			bufferedReader.close();  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		_ips.forEach(item->{
			
			_ports.forEach(item2->{
				list.add(item+":"+item2);
			});
			
		});
		
		return list;
	}

}
