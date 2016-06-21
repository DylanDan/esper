package com.dylan.esper.listener;

import java.util.Date;

import com.dylan.esper.bo.TradeOrder;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class MyErrorFilterListener implements UpdateListener {
	
	private String prefix; 
	
	public MyErrorFilterListener(String prefix){
		
		this.prefix = prefix;
	}
	
	
    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
    	if(null != newEvents){
    		System.out.println(prefix+ " new start=========================================");
    		for(EventBean event : newEvents){
    		System.out.println(new Date()+"===="+event.get("id")+"--"+event.get("res")+"--"+event.get("errorCode"));
    		}
    		System.out.println(prefix + " new end===========================================");
    	}
    }
}
