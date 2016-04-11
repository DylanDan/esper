package com.dylan.esper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.dylan.esper.bo.Quotes;
import com.dylan.esper.bo.TradeOrder;
import com.dylan.esper.listener.MyIRStreamListener;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.client.deploy.DeploymentOptions;
import com.espertech.esper.client.deploy.DeploymentResult;
import com.espertech.esper.client.deploy.EPDeploymentAdmin;
import com.espertech.esper.client.deploy.Module;

public class CEPEngine {
	
	private static EPServiceProvider epService = null;
	
	public static EPServiceProvider startEngine(){
		if(epService == null){
			Configuration config = new Configuration();
			config.addEventTypeAutoName("com.dylan.esper.bo");
			config.addEventType("Orders",TradeOrder.class);
			epService = EPServiceProviderManager.getDefaultProvider(config);
		}
		return  epService;
	}
	
	public static void deployModel() throws Exception{
		EPAdministrator  adm = EPServiceProviderManager.getDefaultProvider().getEPAdministrator();
		
		EPDeploymentAdmin deployAdm = adm.getDeploymentAdmin();
		
		Module  module = deployAdm.read("trade_filter.epl");
		
		DeploymentResult deployResult = deployAdm.deploy(module, new DeploymentOptions());
		
//		adm.getStatement("order_1").addListener(new MyTradeFilterListener());
//		adm.getStatement("order_2").addListener(new MyTradeFilterListener());
		
	}
	
	public static void addRule(String epl,UpdateListener listener){
		
		EPStatement statement = epService.getEPAdministrator().createEPL(epl);
		
		statement.addListener(listener);
		
		statement.start();
		
	}
	
	public static void sendMsg(TradeOrder msg){
		epService.getEPRuntime().sendEvent(msg);
	}
	
	public static void main(String[] args) throws Exception {
		
		CEPEngine.startEngine();
		
//		String expression = "select * from Order.win:time(30 sec) where price > 20.0";
		
//		String expression_1 = "select * from TradeOrder.win:time(30 sec) where price > 0.5";
//		MyListener listener = new MyListener();
//		
//		CEPEngine.addRule(expression, listener);
		
		CEPEngine.deployModel();
		
//		String expression = "select * from Orders";
//		String expression = "on TradeOrder select count(*) from OrdersWindow";
//		String expression = "on TradeOrder as queryEvent select * from OrdersWindow as win";
		
//		CEPEngine.addRule(expression, new MyTradeFilterListener());
//		String expression1 = "on TradeOrder select count(win.*) as total from OrdersWindow as win";
//		String expression2 = "on TradeOrder(orderId='3') select and delete window(win.*) as rows from OrdersWindow as win";
//		String expression3 = "select irstream * from OrdersWindow_1";
//		String expression4 = "on TradeOrder(orderId='3') update OrdersWindow set price = 1";
		String expression5 = "select istream * from Orders where bid[0].size > 500";
		
//		CEPEngine.addRule(expression1, new MyListener());
//		CEPEngine.addRule(expression2, new MyTradeFilterListener());
//		CEPEngine.addRule(expression3, new MyIRStreamListener());
//		CEPEngine.addRule(expression4, new MyIRStreamListener());
		CEPEngine.addRule(expression5, new MyIRStreamListener());
		
		
		String[] ids = {"1","2","3","4","5"};
		
		Random rd = new Random();
		for(int i =0 ;i<1000;i++){
			TradeOrder order = new TradeOrder();
			order.setOrderId(ids[rd.nextInt(4)]);
			order.setSize(rd.nextInt(100)+10);
			order.setPrice(rd.nextDouble());
			
			Quotes bid = new Quotes();
			bid.setPrice(rd.nextDouble());
			bid.setSize(rd.nextInt(1000));
			List<Quotes> bids = new ArrayList<Quotes>();
			bids.add(bid);
			order.setBid(bids);
			System.out.println(new Date()+"<++++++++"+order.getOrderId()+"======"+order.getPrice()+"==="+order.getBid().get(0).getSize());
			CEPEngine.sendMsg(order);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
