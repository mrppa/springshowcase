package com.mrppa.springrestshowcase.product.model;

import com.mrppa.springrestshowcase.base.model.BaseModel;

import lombok.Data;

@Data
public class Product implements BaseModel<Long> {
	private Long id;
	private String name;
	private String description;
}
