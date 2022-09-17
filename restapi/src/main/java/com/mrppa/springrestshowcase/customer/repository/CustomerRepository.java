package com.mrppa.springrestshowcase.customer.repository;

import org.springframework.stereotype.Repository;

import com.mrppa.springrestshowcase.base.repository.BaseRepository;
import com.mrppa.springrestshowcase.customer.dao.CustomerDao;

@Repository
public interface CustomerRepository extends BaseRepository<CustomerDao, Long> {

}
