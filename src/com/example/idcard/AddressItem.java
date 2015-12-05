package com.example.idcard;

import java.io.Serializable;


public class AddressItem implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String shen;
	private String shi;
	private String qu;
	private String rejname;
	private String mob;
	private String youbian;
	private String more;
	private int isdefault;

	public AddressItem() {
		shen = "";
		shi = "";
		qu = "";
		rejname = "";
		mob = "";
		youbian = "";
		more = "";
		isdefault = 1;
	}

	public AddressItem(int id, String shen, String shi, String qu,
			String rejname, String mob, String youbian, String more,
			int isdefault) {
		super();
		this.id = id;
		this.shen = shen;
		this.shi = shi;
		this.qu = qu;
		this.rejname = rejname;
		this.mob = mob;
		this.youbian = youbian;
		this.more = more;
		this.isdefault = isdefault;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getShen() {
		return shen;
	}

	public void setShen(String shen) {
		this.shen = shen;
	}

	public String getShi() {
		return shi;
	}

	public void setShi(String shi) {
		this.shi = shi;
	}

	public String getQu() {
		return qu;
	}

	public void setQu(String qu) {
		this.qu = qu;
	}

	public String getRejname() {
		return rejname;
	}

	public void setRejname(String rejname) {
		this.rejname = rejname;
	}

	public String getMob() {
		return mob;
	}

	public void setMob(String mob) {
		this.mob = mob;
	}

	public String getYoubian() {
		return youbian;
	}

	public void setYoubian(String youbian) {
		this.youbian = youbian;
	}

	public String getMore() {
		return more;
	}

	public void setMore(String more) {
		this.more = more;
	}

	public int getIsdefault() {
		return isdefault;
	}

	public void setIsdefault(int isdefault) {
		this.isdefault = isdefault;
	}

}
