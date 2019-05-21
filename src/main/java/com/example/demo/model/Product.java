package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="PRODUCTS")
public class Product extends BaseEntity {

	private static final long serialVersionUID = -1688827776264711692L;

	@ManyToOne
	@JoinColumn(name = "CATEGORYID")
	private Category category;
	
	@ManyToOne
	@JoinColumn(name = "SUPPLIERID")
	private Supplier supplier;
	
	@Column(name = "NAME")
	private String name;

	@Column(name = "PRICE")
	private Float price;

	@Column(name = "QUANTITY")
	private Integer quantity;

	@Column(name = "DESCRIPTION")
	private String description;

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
