package com.mrppa.showcase.product.model;

import com.mrppa.showcase.base.model.BaseModel;

import lombok.Data;

@Data
public class Product implements BaseModel<Long> {
	private Long id;
	private String name;
	private String description;
}
