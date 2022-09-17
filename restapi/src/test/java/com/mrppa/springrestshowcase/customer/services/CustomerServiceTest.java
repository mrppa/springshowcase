package com.mrppa.springrestshowcase.customer.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mrppa.showcase.base.CustomServiceException;
import com.mrppa.showcase.base.model.SearchBindOpperation;
import com.mrppa.showcase.base.model.SearchCriteria;
import com.mrppa.showcase.base.model.SearchOpperation;
import com.mrppa.showcase.base.services.BaseSpecification;
import com.mrppa.showcase.customer.dao.CustomerDao;
import com.mrppa.showcase.customer.model.Customer;
import com.mrppa.showcase.customer.services.CustomerService;
import com.mrppa.springrestshowcase.base.services.BaseCrudServiceTest;

@SpringBootTest
public class CustomerServiceTest extends BaseCrudServiceTest<CustomerDao, Customer, Long> {

	@Autowired
	public CustomerServiceTest(CustomerService customerService) {
		super(customerService);
	}

	@Override
	public Customer generateSampleModel() {
		Customer customer = new Customer();
		customer.setFirstName("FName");
		customer.setLastName("LName");
		customer.setBirthday(LocalDate.now());
		return customer;
	}

	@Test
	public void basicCrudTest() throws CustomServiceException {
		super.basicCrudTest(t -> {
			t.setLastName("Updated LName");
			return t;
		});
	}

	@Override
	public List<Customer> generateSampleModelsForSearch() {
		List<Customer> objList = new ArrayList<Customer>();

		Customer c1 = new Customer();
		c1.setFirstName("c1Fn");
		c1.setLastName("c1Ln");
		objList.add(c1);

		Customer c2 = new Customer();
		c2.setFirstName("c2Fn");
		c2.setLastName("c2Ln");
		objList.add(c2);

		Customer c3 = new Customer();
		c3.setFirstName("c3Fn");
		c3.setLastName("c3Ln");
		objList.add(c3);

		Customer c4 = new Customer();
		c4.setFirstName("c4Fn");
		c4.setLastName("c4Ln");
		objList.add(c4);
		return objList;
	}

	@Test
	public void testFindWithPredicate() throws CustomServiceException {
		BaseSpecification<CustomerDao> specification = new BaseSpecification<>();
		specification.getSpecList().add(new SearchCriteria("firstName", "c1Fn", SearchOpperation.EQUAL));
		specification.getSpecList().add(new SearchCriteria("lastName", "c2Ln", SearchOpperation.EQUAL));
		specification.setBindOpperation(SearchBindOpperation.OR);
		super.testFindWithPredicate(specification, 2,
				List.of(cust -> "c1Fn".equals(cust.getFirstName()), cust -> "c2Ln".equals(cust.getLastName())));
	}

}
