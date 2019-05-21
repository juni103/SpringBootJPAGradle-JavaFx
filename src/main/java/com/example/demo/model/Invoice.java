package com.example.demo.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="INVOICES")
public class Invoice extends BaseEntity {

	private static final long serialVersionUID = -6217133431930492327L;

	@ManyToOne
	@JoinColumn(name="EMPLOYEEID")
	private Employee employee;
	
	@Column(name="TOTAL")
	private Float total;
	
	@Column(name="VAT")
	private Float vat;
	
	@Column(name="DISCOUNT")
	private Float discount;
	
	@Column(name="PAYABLE")
	private Float payable;
	
	@Column(name="PAID")
	private Float paid;
	
	@Column(name="RETURNED")
	private Float returned;
	
	@Column(name="DATETIME")
	private LocalDate datetime;

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	public Float getVat() {
		return vat;
	}

	public void setVat(Float vat) {
		this.vat = vat;
	}

	public Float getDiscount() {
		return discount;
	}

	public void setDiscount(Float discount) {
		this.discount = discount;
	}

	public Float getPayable() {
		return payable;
	}

	public void setPayable(Float payable) {
		this.payable = payable;
	}

	public Float getPaid() {
		return paid;
	}

	public void setPaid(Float paid) {
		this.paid = paid;
	}

	public Float getReturned() {
		return returned;
	}

	public void setReturned(Float returned) {
		this.returned = returned;
	}

	public LocalDate getDatetime() {
		return datetime;
	}

	public void setDatetime(LocalDate datetime) {
		this.datetime = datetime;
	}
	
}
