package com.mrppa.showcase.customer.model;

import java.time.LocalDate;

import com.mrppa.showcase.base.model.BaseModel;

import lombok.Data;

@Data
public class Customer implements BaseModel<Long> {
	private Long id;
	private String firstName;
	private String lastName;
	private LocalDate birthday;
}
