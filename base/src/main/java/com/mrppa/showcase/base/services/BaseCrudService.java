package com.mrppa.showcase.base.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.mrppa.showcase.CustomServiceException;
import com.mrppa.showcase.ErrorCodes;
import com.mrppa.showcase.base.dao.BaseDao;
import com.mrppa.showcase.base.model.BaseModel;
import com.mrppa.showcase.base.repository.BaseRepository;

public abstract class BaseCrudService<T extends BaseDao<ID>, U extends BaseModel<ID>, ID> {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private BaseRepository<T, ID> repository;

	public BaseCrudService(BaseRepository<T, ID> repository) {
		this.repository = repository;
	}

	public U create(U model) throws CustomServiceException {
		logger.debug("Creating new {} ", model);
		model.setId(null);
		T dao = setToDao(null, model);
		dao = repository.save(dao);
		return toUi(dao);
	}

	public void update(U model) throws CustomServiceException {
		logger.debug("updating {} ", model);

		Optional<T> optExistingItem = repository.findById(model.getId());
		if (!optExistingItem.isPresent()) {
			logger.debug("Item does not exists ID:{}", model.getId());
			throw new CustomServiceException(ErrorCodes.ITEM_NOT_FOUND);
		}

		T dao = setToDao(optExistingItem.get(), model);
		repository.save(dao);
	}

	public void delete(ID id) throws CustomServiceException {
		logger.debug("deleting  {} ", id);

		if (id == null || !repository.existsById(id)) {
			logger.debug("Item not not exists ID:{}", id);
			throw new CustomServiceException(ErrorCodes.ITEM_NOT_FOUND);
		}
		repository.deleteById(id);
	}

	public Optional<U> getById(ID id) throws CustomServiceException {
		logger.debug("get by id  {} ", id);

		Optional<U> optU = Optional.empty();
		if (id == null) {
			return optU;
		}

		Optional<T> optExistingItem = repository.findById(id);
		if (optExistingItem.isPresent()) {
			optU = Optional.of(toUi(optExistingItem.get()));
		}

		return optU;
	}

	public Page<U> findWithPredicate(BaseSpecification<T> specification, Pageable pageable) {
		Page<T> daoList = repository.findAll(specification, pageable);
		if (daoList == null) {
			return null;
		}
		Page<U> page = new PageImpl<>(daoList.getContent().stream().map(dao -> toUi(dao)).toList(),
				daoList.getPageable(), daoList.getTotalElements());
		return page;
	}

	public abstract U toUi(T dao);

	public abstract T setToDao(T dao, U model);
}
