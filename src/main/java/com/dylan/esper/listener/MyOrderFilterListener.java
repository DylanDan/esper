package com.dylan.esper.listener;

import java.util.Date;

import com.dylan.esper.bo.TradeOrder;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class MyOrderFilterListener implements UpdateListener {
	
	private String prefix; 
	
	public MyOrderFilterListener(String prefix){
		
		this.prefix = prefix;
	}
	
	
    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
    	if(null != newEvents){
    		System.out.println(prefix+ " new start=========================================");
    		for(EventBean event : newEvents){
    		System.out.println(new Date()+"===="+(((TradeOrder)event.getUnderlying()).getOrderId()+"--"+((TradeOrder)event.getUnderlying()).getUserId()));
    		}
    		System.out.println(prefix + " new end===========================================");
    	}
    	if(null != oldEvents){
    		EventBean event = oldEvents[0];
    		System.out.println(new Date()+"xxxxxxxxxx"+event.get("orderId")+"xxxxxxxxxx"+event.get("price"));
    	}
    }
}
