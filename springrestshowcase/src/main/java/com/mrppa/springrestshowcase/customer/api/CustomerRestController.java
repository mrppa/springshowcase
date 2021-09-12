package com.mrppa.springrestshowcase.customer.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrppa.springrestshowcase.base.api.BaseRestController;
import com.mrppa.springrestshowcase.customer.dao.CustomerDao;
import com.mrppa.springrestshowcase.customer.model.Customer;
import com.mrppa.springrestshowcase.customer.services.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerRestController extends BaseRestController<CustomerDao, Customer, Long> {

	@Autowired
	public CustomerRestController(CustomerService customerService) {
		super(customerService);
	}

}
