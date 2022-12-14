package com.mrppa.showcase.customer.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrppa.showcase.base.api.BaseRestController;
import com.mrppa.showcase.customer.dao.CustomerDao;
import com.mrppa.showcase.customer.model.Customer;
import com.mrppa.showcase.customer.services.CustomerService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/customer")
@SecurityRequirement(name = "Bearer Authentication")
public class CustomerRestController extends BaseRestController<CustomerDao, Customer, Long> {

	@Autowired
	public CustomerRestController(CustomerService customerService) {
		super(customerService);
	}

}
