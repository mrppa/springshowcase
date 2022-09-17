package com.mrppa.springrestshowcase.base.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.mrppa.showcase.CustomServiceException;
import com.mrppa.showcase.base.dao.BaseDao;
import com.mrppa.showcase.base.model.BaseModel;
import com.mrppa.showcase.base.services.BaseCrudService;
import com.mrppa.showcase.base.services.BaseSpecification;

public abstract class BaseCrudServiceTest<T extends BaseDao<ID>, U extends BaseModel<ID>, ID> {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private BaseCrudService<T, U, ID> service;

	public BaseCrudServiceTest(BaseCrudService<T, U, ID> service) {
		this.service = service;
	}

	public abstract U generateSampleModel();

	public void basicCrudTest(Function<U, U> modifyFunction) throws CustomServiceException {
		logger.info("BASIC CRUD TEST STARTED");
		U model = generateSampleModel();
		logger.info("MODEL:{}", model);
		model = service.create(model);
		logger.info("MODEL AFTER SAVING:{}", model);

		Optional<U> optCust = service.getById(model.getId());
		assertTrue(optCust.isPresent());
		assertEquals(model, optCust.get());

		model = modifyFunction.apply(model);
		logger.info("MODEL AFTER MODIFY:{}", model);
		service.update(model);

		optCust = service.getById(model.getId());
		assertTrue(optCust.isPresent());
		assertEquals(model, optCust.get());

		service.delete(model.getId());

		optCust = service.getById(model.getId());
		assertTrue(optCust.isEmpty());
		logger.info("BASIC CRUD TEST COMPLETED");
	}

	public abstract List<U> generateSampleModelsForSearch();

	public void testFindWithPredicate(BaseSpecification<T> specification, int expectedItems,
			List<Predicate<U>> itemVerifyPredicates) throws CustomServiceException {
		logger.info("SEARCH TEST STARTED");

		List<U> objList = generateSampleModelsForSearch();
		for (U model : objList) {
			model = service.create(model);
		}
		Page<U> custPage = service.findWithPredicate(specification, PageRequest.of(0, 10));

		assertEquals(expectedItems, custPage.getTotalElements());

		for (Predicate<U> predicate : itemVerifyPredicates) {
			assertTrue(custPage.getContent().stream().filter(predicate).findAny().isPresent());
		}
		logger.info("SEARCH TEST STARTED");
	}

}
