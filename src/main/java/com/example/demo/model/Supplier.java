package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "SUPPLIERS")
public class Supplier extends BaseEntity {

	private static final long serialVersionUID = -1681642797651459131L;

	@Column(name="NAME")
	private String name;

	@Column(name="PHONE")
	private String phone;

	@Column(name="ADDRESS")
	private String address;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
}
