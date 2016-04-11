package com.dylan.esper.listener;

import java.util.Date;
import java.util.List;

import com.dylan.esper.bo.Quotes;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class MyIRStreamListener implements UpdateListener {
    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
    	if(null != newEvents){
    		System.out.println("new start=========================================");
    		for(EventBean event : newEvents){
    			System.out.println(new Date()+">>>>>>>>>>>>"+event.get("orderId")+"xxxxxxxxxx"+event.get("price")+"===="+((Quotes)((List)event.get("bid")).get(0)).getSize());
    		}
    		System.out.println("new end===========================================");
    	}
    	if(null != oldEvents){
    		System.out.println("old start=========================================");
    		for(EventBean event : oldEvents){
    			System.out.println(new Date()+"<<<<<<<<<<<<<"+event.get("orderId")+"yyyyyyyyyy"+event.get("price"));
    		}
    		System.out.println("old end===========================================");
    	}
    }
}
