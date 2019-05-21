package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity(name="EMPLOYEES")
public class Employee extends BaseEntity {

	private static final long serialVersionUID = 8010790339173405735L;
	
	@Column(name="FIRSTNAME")
	private String firstName;

	@Column(name="LASTNAME")
	private String lastName;

	@Column(name="USERNAME")
	private String username;

	@Column(name="PASSWORD")
	private String password;

	@Column(name="PHONE")
	private String phone;

	@Column(name="ADDRESS")
	private String address;

	@Column(name="TYPE")
	private String type;
	
	@Transient
	private String fullName;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFullName() {
		return firstName.concat(" ").concat(lastName);
	}

	public void setFullName(String fullName) {
		String[] splitName = fullName.split(" ");
		this.firstName = splitName[0];
		this.lastName = splitName[1];
		this.fullName = fullName;
	}
	
}
