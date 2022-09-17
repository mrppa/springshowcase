package com.mrppa.showcase.customer.repository;

import org.springframework.stereotype.Repository;

import com.mrppa.showcase.base.repository.BaseRepository;
import com.mrppa.showcase.customer.dao.CustomerDao;

@Repository
public interface CustomerRepository extends BaseRepository<CustomerDao, Long> {

}
