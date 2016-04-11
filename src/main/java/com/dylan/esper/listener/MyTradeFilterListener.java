package com.dylan.esper.listener;

import java.util.Date;

import com.dylan.esper.bo.TradeOrder;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class MyTradeFilterListener implements UpdateListener {
    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
    	if(null != newEvents){
    		System.out.println("new start=========================================");
    		for(EventBean event : newEvents){
    		System.out.println(new Date()+"===="+((TradeOrder[])event.get("rows")).length);
//    		System.out.println(new Date()+"===="+event.get("win")+"======>"+event.get("queryEvent"));
///    		System.out.println(new Date()+"===="+event.get("orderId")+"======>"+event.get("price"));
//    			System.out.println(new Date()+"===="+event.get("count(*)"));
    		}
    		System.out.println("new end===========================================");
    	}
    	if(null != oldEvents){
    		EventBean event = oldEvents[0];
    		System.out.println(new Date()+"xxxxxxxxxx"+event.get("orderId")+"xxxxxxxxxx"+event.get("price"));
    	}
    }
}
