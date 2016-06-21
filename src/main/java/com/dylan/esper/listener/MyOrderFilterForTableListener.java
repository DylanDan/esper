package com.dylan.esper.listener;

import java.util.Date;

import com.dylan.esper.bo.TradeOrder;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.event.arr.ObjectArrayEventBean;

public class MyOrderFilterForTableListener implements UpdateListener {
	
	private String prefix; 
	
	public MyOrderFilterForTableListener(String prefix){
		
		this.prefix = prefix;
	}
	
	
    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
    	if(null != newEvents){
    		System.out.println(prefix+ " new start=========================================");
    		for(EventBean event : newEvents){
    			ObjectArrayEventBean bean = (ObjectArrayEventBean)event;
    			System.out.println(bean.getProperties()[0]+"==="+bean.getProperties()[1]+"===="+bean.getProperties()[2]);    		}
    		System.out.println(prefix + " new end===========================================");
    	}
    	if(null != oldEvents){
    		EventBean event = oldEvents[0];
    		System.out.println(new Date()+"xxxxxxxxxx"+event.get("orderId")+"xxxxxxxxxx"+event.get("price"));
    	}
    }
}
