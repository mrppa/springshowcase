package com.mrppa.showcase.base.api;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.mrppa.showcase.base.CustomServiceException;
import com.mrppa.showcase.base.ErrorCodes;
import com.mrppa.showcase.base.dao.BaseDao;
import com.mrppa.showcase.base.model.BaseModel;
import com.mrppa.showcase.base.services.BaseCrudService;
import com.mrppa.showcase.base.services.BaseSpecification;


public class BaseRestController<T extends BaseDao<ID>, U extends BaseModel<ID>, ID> {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private BaseCrudService<T, U, ID> service;

	public BaseRestController(BaseCrudService<T, U, ID> service) {
		this.service = service;
	}

	@PostMapping("/")
	public U create(@RequestBody U model) throws CustomServiceException {
		logger.info("Creating {}", model);
		U resObj = service.create(model);
		logger.info("Result Object {}", resObj);
		return resObj;
	}

	@GetMapping("/{id}")
	public Optional<U> get(@PathVariable ID id) throws CustomServiceException {
		logger.info("get by id {}", id);
		Optional<U> optResObj = service.getById(id);
		logger.info("Result Object {}", optResObj);
		return optResObj;
	}

	@PostMapping("/{id}")
	public void update(@RequestBody U model, @PathVariable ID id) throws CustomServiceException {
		logger.info("Updating {} with id {]", model, id);
		if (id != model.getId()) {
			logger.warn("ID mismatch. Invalid request");
			throw new CustomServiceException(ErrorCodes.ID_MISMATCH);
		}
		service.update(model);
	}

	@DeleteMapping("/{id}")
	public void update(@PathVariable ID id) throws CustomServiceException {
		logger.info("Deleting with id {]", id);
		service.delete(id);
	}

	@GetMapping("/search")
	public Page<U> search(@RequestBody BaseSpecification<T> baseSpec, Pageable pageble) throws CustomServiceException {
		logger.info("Search By  {} , in Page {}", baseSpec, pageble);
		return service.findWithPredicate(baseSpec, pageble);
	}

}
