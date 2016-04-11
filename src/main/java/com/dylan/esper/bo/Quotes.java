package com.dylan.esper.bo;

import java.io.Serializable;

public class Quotes implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private double price;
	
	private double size;

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}
	
	
}
