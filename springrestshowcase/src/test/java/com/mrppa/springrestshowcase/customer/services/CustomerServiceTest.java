package com.mrppa.springrestshowcase.customer.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.mrppa.springrestshowcase.base.CustomServiceException;
import com.mrppa.springrestshowcase.base.model.SearchBindOpperation;
import com.mrppa.springrestshowcase.base.model.SearchCriteria;
import com.mrppa.springrestshowcase.base.model.SearchOpperation;
import com.mrppa.springrestshowcase.base.services.BaseSpecification;
import com.mrppa.springrestshowcase.customer.dao.CustomerDao;
import com.mrppa.springrestshowcase.customer.model.Customer;

@SpringBootTest
public class CustomerServiceTest {

	@Autowired
	CustomerService customerService;

	private List<Customer> testCustomers1 = new ArrayList<>();

	public CustomerServiceTest() {
		Customer c1 = new Customer();
		c1.setFirstName("c1Fn");
		c1.setLastName("c1Ln");
		testCustomers1.add(c1);

		Customer c2 = new Customer();
		c2.setFirstName("c2Fn");
		c2.setLastName("c2Ln");
		testCustomers1.add(c2);

		Customer c3 = new Customer();
		c3.setFirstName("c3Fn");
		c3.setLastName("c3Ln");
		testCustomers1.add(c3);

		Customer c4 = new Customer();
		c4.setFirstName("c4Fn");
		c4.setLastName("c4Ln");
		testCustomers1.add(c4);
	}

	private Customer generateModel() {
		Customer customer = new Customer();
		customer.setFirstName("FName");
		customer.setLastName("LName");
		customer.setBirthday(LocalDate.now());
		return customer;
	}

	@Test
	void testCrudFlow() throws CustomServiceException {
		Customer customer = generateModel();
		customer = customerService.create(customer);

		Optional<Customer> optCust = customerService.getById(customer.getId());
		assertTrue(optCust.isPresent());
		assertEquals(customer, optCust.get());

		customer.setFirstName("UpdatedFName");
		customerService.update(customer);

		optCust = customerService.getById(customer.getId());
		assertTrue(optCust.isPresent());
		assertEquals(customer, optCust.get());

		customerService.delete(customer.getId());

		optCust = customerService.getById(customer.getId());
		assertTrue(optCust.isEmpty());
	}

	@BeforeEach
	private void setup() throws CustomServiceException {
		for (Customer customer : testCustomers1) {
			Customer resultCustomer = customerService.create(customer);
			customer.setId(resultCustomer.getId());
		}
	}

	@AfterEach
	private void cleanup() throws CustomServiceException {
		for (Customer customer : testCustomers1) {
			customerService.delete(customer.getId());
		}
	}

	@Test
	void testFindWithPredicate() throws CustomServiceException {
		BaseSpecification<CustomerDao> specification = new BaseSpecification<>();
		specification.getSpecList().add(new SearchCriteria("firstName", "c1Fn", SearchOpperation.EQUAL));
		specification.getSpecList().add(new SearchCriteria("lastName", "c2Ln", SearchOpperation.EQUAL));
		specification.setBindOpperation(SearchBindOpperation.OR);
		Page<Customer> custPage = customerService.findWithPredicate(specification, PageRequest.of(0, 10));

		assertEquals(2, custPage.getTotalElements());
		assertTrue(custPage.getContent().stream().filter(cust -> "c1Fn".equals(cust.getFirstName())).findAny()
				.isPresent());
		assertTrue(
				custPage.getContent().stream().filter(cust -> "c2Ln".equals(cust.getLastName())).findAny().isPresent());

	}

}
