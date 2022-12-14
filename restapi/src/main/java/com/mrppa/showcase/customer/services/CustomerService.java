package com.mrppa.showcase.customer.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrppa.showcase.base.services.BaseCrudService;
import com.mrppa.showcase.customer.dao.CustomerDao;
import com.mrppa.showcase.customer.model.Customer;
import com.mrppa.showcase.customer.repository.CustomerRepository;

@Service
@Transactional
public class CustomerService extends BaseCrudService<CustomerDao, Customer, Long> {

	@Autowired
	public CustomerService(CustomerRepository customerRepository) {
		super(customerRepository);
	}

	@Override
	public Customer toUi(CustomerDao dao) {
		Customer customer = new Customer();
		customer.setId(dao.getId());
		customer.setFirstName(dao.getFirstName());
		customer.setLastName(dao.getLastName());
		customer.setBirthday(dao.getBirthday());
		return customer;
	}

	@Override
	public CustomerDao setToDao(CustomerDao dao, Customer model) {
		if (dao == null) {
			dao = new CustomerDao();
		}
		dao.setId(model.getId());
		dao.setFirstName(model.getFirstName());
		dao.setLastName(model.getLastName());
		dao.setBirthday(model.getBirthday());
		return dao;
	}

}
