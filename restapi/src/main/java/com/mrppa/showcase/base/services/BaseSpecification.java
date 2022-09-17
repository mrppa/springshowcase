package com.mrppa.showcase.base.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import com.mrppa.showcase.base.model.SearchBindOpperation;
import com.mrppa.showcase.base.model.SearchCriteria;
import com.mrppa.showcase.base.model.SearchOpperation;

public class BaseSpecification<T> implements Specification<T> {
	private Logger logger = LoggerFactory.getLogger(BaseSpecification.class);

	private static final long serialVersionUID = 1L;

	private List<SearchCriteria> specList = new ArrayList<>();
	private SearchBindOpperation bindOpperation = SearchBindOpperation.AND;

	public SearchBindOpperation getBindOpperation() {
		return bindOpperation;
	}

	public void setBindOpperation(SearchBindOpperation bindOpperation) {
		this.bindOpperation = bindOpperation;
	}

	public List<SearchCriteria> getSpecList() {
		return specList;
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		List<Predicate> predicates = new ArrayList<>();

		for (SearchCriteria criteria : specList) {
			if (SearchOpperation.EQUAL == criteria.getOperation()) {
				predicates.add(builder.equal(root.get(criteria.getKey()), criteria.getValue()));
			} else if (SearchOpperation.LIKE == criteria.getOperation()) {
				predicates.add(builder.like(root.get(criteria.getKey()), "%" + criteria.getValue().toString() + "%"));
			} else if (SearchOpperation.GREATER_THAN == criteria.getOperation()) {
				predicates.add(builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString()));
			} else if (SearchOpperation.GREATER_THAN_EQUAL == criteria.getOperation()) {
				predicates
						.add(builder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
			} else if (SearchOpperation.LESS_THAN == criteria.getOperation()) {
				predicates.add(builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString()));
			} else if (SearchOpperation.LESS_THAN_EQUAL == criteria.getOperation()) {
				predicates
						.add(builder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
			} else {
				logger.warn("Non supported SearchOpperation :{} ", criteria.getOperation());
			}
		}

		if (SearchBindOpperation.AND == bindOpperation) {
			return builder.and(predicates.toArray(new Predicate[0]));
		} else if (SearchBindOpperation.OR == bindOpperation) {
			return builder.or(predicates.toArray(new Predicate[0]));
		} else {
			logger.error("Non supported searchBindOpperation :{} ", bindOpperation);
			return null;
		}
	}

}
