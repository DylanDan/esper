package com.dylan.esper.bo;

import java.io.Serializable;
import java.util.List;

public class TradeOrder implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int size;
	
	private String orderId;
	
	private double price;
	
	private List<Quotes> bid;

	private List<Quotes> offer;
	
	public List<Quotes> getBid() {
		return bid;
	}

	public void setBid(List<Quotes> bid) {
		this.bid = bid;
	}

	public List<Quotes> getOffer() {
		return offer;
	}

	public void setOffer(List<Quotes> offer) {
		this.offer = offer;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	
}
