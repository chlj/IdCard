package com.example.idcard;

import java.io.Serializable;
import java.util.List;

public class Shi implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String name;
	private List<Qu> quArray;

	public Shi() {
		super();
	}

	public Shi(String name, List<Qu> quArray) {
		super();
		this.name = name;
		this.quArray = quArray;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Qu> getQuArray() {
		return quArray;
	}

	public void setQuArray(List<Qu> quArray) {
		this.quArray = quArray;
	}
	
	
	
	
}
