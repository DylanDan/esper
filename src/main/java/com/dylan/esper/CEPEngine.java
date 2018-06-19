package com.dylan.esper;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.dylan.esper.bo.Quotes;
import com.dylan.esper.bo.TradeOrder;
import com.dylan.esper.bo.UserSession;
import com.dylan.esper.listener.MyErrorFilterListener;
import com.dylan.esper.listener.MyOrderFilterListener;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPOnDemandQueryResult;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
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
//			config.addEventTypeAutoName("com.dylan.esper.bo");
			config.addEventType("TradeOrder",TradeOrder.class);
			config.addEventType("OrderEvent",TradeOrder.class);
			config.addEventType("OrderExchCheckEvent",TradeOrder.class);
			config.addEventType("OrderCheckFailEvent",TradeOrder.class);
			config.addEventType("UserSession",UserSession.class);
			epService = EPServiceProviderManager.getDefaultProvider(config);
		}
		return  epService;
	}
	
	public static void deployModel() throws Exception{
		EPAdministrator  adm = EPServiceProviderManager.getDefaultProvider().getEPAdministrator();
		
		EPDeploymentAdmin deployAdm = adm.getDeploymentAdmin();
		
		Module  module = deployAdm.read("my_3.epl");
		
		DeploymentResult deployResult = deployAdm.deploy(module, new DeploymentOptions());
		
//		adm.getStatement("OrderExchCheckStream").addListener(new MyOrderFilterListener("OrderExchCheckStream"));
//		adm.getStatement("OrderCheckFailStream").addListener(new MyOrderFilterListener("OrderCheckFailStream"));
//		adm.getStatement("koOrder").addListener(new MyOrderFilterListener("koOrder"));
//		adm.getStatement("onOk").addListener(new MyOrderFilterListener("onOk"));
//		adm.getStatement("ErrorStream").addListener(new MyErrorFilterListener("ErrorStream"));
		adm.getStatement("Order").addListener(new MyOrderFilterListener("Order"));
		
	}
	
	public static void addRule(String epl,UpdateListener listener){
		
		EPStatement statement = epService.getEPAdministrator().createEPL(epl);
		
		statement.addListener(listener);
		
		statement.start();
		
	}
	
	public static void sendMsg(TradeOrder msg){
		epService.getEPRuntime().sendEvent(msg);
	}
	public static void sendMsg(UserSession msg){
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
//		String expression5 = "select istream * from Orders where bid[0].size > 500";
		
//		CEPEngine.addRule(expression1, new MyListener());
//		CEPEngine.addRule(expression2, new MyTradeFilterListener());
//		CEPEngine.addRule(expression3, new MyIRStreamListener());
//		CEPEngine.addRule(expression4, new MyIRStreamListener());
//		CEPEngine.addRule(expression5, new MyIRStreamListener());
		
		
		
//		UserSession userSession = new UserSession();
//		userSession.setUserId("dx0002");
//		userSession.setLoginStatus("2");
//		CEPEngine.sendMsg(userSession);
		
//		EPAdministrator  adm = EPServiceProviderManager.getDefaultProvider().getEPAdministrator();
//		EPOnDemandQueryResult  result = EPServiceProviderManager.getDefaultProvider().getEPRuntime().executeQuery("select * from userSessionWin");
//		Iterator<EventBean>  itor = result.iterator();
//		while(itor.hasNext()){
//			System.out.println(((UserSession)itor.next().getUnderlying()).toString());
//		}
		
		
		String[] ids = {"1","2","3","4","5"};
		
		Random rd = new Random();
		for(int i =0 ;i<10;i++){
			TradeOrder order = new TradeOrder();
			order.setUserId("dx0001");
			int num = rd.nextInt(4);
			if(num == 2) {
				order.setOrderId(ids[num]);
			}
			if(num == 3) {
				order.setOrderId("");
			}
			order.setSize(rd.nextInt(100)+10);
			order.setPrice(rd.nextDouble());
			
			Quotes bid = new Quotes();
			bid.setPrice(rd.nextDouble());
			bid.setSize(rd.nextInt(1000));
			List<Quotes> bids = new ArrayList<Quotes>();
			bids.add(bid);
			order.setBid(bids);
			System.out.println(new Date()+"<++++++++num="+num+"==="+order.getOrderId()+"======"+order.getPrice()+"==="+order.getSize());
			CEPEngine.sendMsg(order);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
//		System.out.println("=============================================================");
//		EPOnDemandQueryResult  result_1 = EPServiceProviderManager.getDefaultProvider().getEPRuntime().executeQuery("select * from Order_win_1");
//		Iterator<EventBean>  itor_1 = result_1.iterator();
//		while(itor_1.hasNext()){
//			System.out.println(((TradeOrder)itor_1.next().getUnderlying()).toString());
//		}
//		
//		System.out.println("=============================================================");
//		EPOnDemandQueryResult  result_2 = EPServiceProviderManager.getDefaultProvider().getEPRuntime().executeQuery("select * from Order_win_2");
//		Iterator<EventBean>  itor_2 = result_2.iterator();
//		while(itor_2.hasNext()){
//			System.out.println(((TradeOrder)itor_2.next().getUnderlying()).toString());
//		}
//		System.out.println("=============================================================");
		
//		EPAdministrator  adm = EPServiceProviderManager.getDefaultProvider().getEPAdministrator();
//		EPOnDemandQueryResult  result = EPServiceProviderManager.getDefaultProvider().getEPRuntime().executeQuery("select * from OrderTable");
//		Iterator<EventBean>  itor = result.iterator();
//		while(itor.hasNext()){
//			ObjectArrayEventBean bean = (ObjectArrayEventBean)itor.next();
//			System.out.println(bean.getProperties()[0]+"==="+bean.getProperties()[1]+"===="+bean.getProperties()[2]);
//		}		
//		
	}

}
