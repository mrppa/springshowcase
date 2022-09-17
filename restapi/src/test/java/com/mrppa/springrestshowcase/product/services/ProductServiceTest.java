package com.mrppa.springrestshowcase.product.services;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mrppa.springrestshowcase.base.CustomServiceException;
import com.mrppa.springrestshowcase.base.model.SearchBindOpperation;
import com.mrppa.springrestshowcase.base.model.SearchCriteria;
import com.mrppa.springrestshowcase.base.model.SearchOpperation;
import com.mrppa.springrestshowcase.base.services.BaseCrudServiceTest;
import com.mrppa.springrestshowcase.base.services.BaseSpecification;
import com.mrppa.springrestshowcase.product.dao.ProductDao;
import com.mrppa.springrestshowcase.product.model.Product;

@SpringBootTest
public class ProductServiceTest extends BaseCrudServiceTest<ProductDao, Product, Long> {

	@Autowired
	public ProductServiceTest(ProductService productService) {
		super(productService);
	}

	@Override
	public Product generateSampleModel() {
		Product product = new Product();
		product.setName("p0");
		product.setDescription("product 0");
		return product;
	}

	@Test
	public void basicCrudTest() throws CustomServiceException {
		super.basicCrudTest(t -> {
			t.setDescription("updated desc");
			return t;
		});
	}

	@Override
	public List<Product> generateSampleModelsForSearch() {
		List<Product> objList = new ArrayList<>();

		Product p1 = new Product();
		p1.setName("p1");
		p1.setDescription("product 1");
		objList.add(p1);

		Product p2 = new Product();
		p2.setName("p2");
		p2.setDescription("product 2");
		objList.add(p2);

		Product p3 = new Product();
		p3.setName("p3");
		p3.setDescription("product 3");
		objList.add(p3);

		Product p4 = new Product();
		p4.setName("p4");
		p4.setDescription("product 4");
		objList.add(p4);

		return objList;
	}

	@Test
	public void testFindWithPredicate() throws CustomServiceException {
		BaseSpecification<ProductDao> specification = new BaseSpecification<>();
		specification.getSpecList().add(new SearchCriteria("name", "p1", SearchOpperation.EQUAL));
		specification.getSpecList().add(new SearchCriteria("description", "product 2", SearchOpperation.EQUAL));
		specification.setBindOpperation(SearchBindOpperation.OR);
		super.testFindWithPredicate(specification, 2,
				List.of(prod -> "p1".equals(prod.getName()), prod -> "product 2".equals(prod.getDescription())));
	}
	
	

}
