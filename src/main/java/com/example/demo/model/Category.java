package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name="CATEGORIES")
public class Category extends BaseEntity {

	private static final long serialVersionUID = 3837898451020582048L;

	@Column(name="TYPE")
	private String type;
	
	@Column(name="DESCRIPTION")
	private String description;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
