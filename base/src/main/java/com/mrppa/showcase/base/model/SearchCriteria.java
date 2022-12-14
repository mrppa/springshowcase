package com.mrppa.showcase.base.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchCriteria {
	private String key;
	private Object value;
	private SearchOpperation operation;
}
