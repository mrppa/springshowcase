package com.mrppa.springrestshowcase.product.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import com.mrppa.springrestshowcase.base.dao.BaseDao;

import lombok.Data;

@Entity
@Data
public class ProductDao implements BaseDao<Long> {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private String name;
	private String description;
	@Version
	private Long version;
}
