package com.mrppa.springrestshowcase.customer.dao;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import com.mrppa.springrestshowcase.base.dao.BaseDao;

import lombok.Data;

@Entity
@Data
public class CustomerDao implements BaseDao<Long> {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private String firstName;
	private String lastName;
	private LocalDate birthday;
	@Version
	private Long version;
}
