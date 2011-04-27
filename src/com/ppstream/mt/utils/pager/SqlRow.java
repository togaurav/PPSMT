package com.ppstream.mt.utils.pager;

import java.io.Serializable;

public class SqlRow implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Object A;
	private Object B;
	private Object C;
	
	public Object getA() {
		return A;
	}
	public void setA(Object a) {
		A = a;
	}
	public Object getB() {
		return B;
	}
	public void setB(Object b) {
		B = b;
	}
	public Object getC() {
		return C;
	}
	public void setC(Object c) {
		C = c;
	}

}
