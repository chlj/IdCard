package com.example.idcard;

import java.io.Serializable;
import java.util.List;

public class Shen implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private List<Shi> shiArray;

	public Shen() {
		super();
	}

	public Shen(String name, List<Shi> shiArray) {
		super();
		this.name = name;
		this.shiArray = shiArray;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Shi> getShiArray() {
		return shiArray;
	}

	public void setShiArray(List<Shi> shiArray) {
		this.shiArray = shiArray;
	}

}
