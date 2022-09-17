package com.mrppa.showcase.product.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrppa.showcase.base.services.BaseCrudService;
import com.mrppa.showcase.product.dao.ProductDao;
import com.mrppa.showcase.product.model.Product;
import com.mrppa.showcase.product.repository.ProductRepository;

@Service
@Transactional
public class ProductService extends BaseCrudService<ProductDao, Product, Long> {

	@Autowired
	public ProductService(ProductRepository productRepository) {
		super(productRepository);
	}

	@Override
	public Product toUi(ProductDao dao) {
		Product model = new Product();
		model.setId(dao.getId());
		model.setName(dao.getName());
		model.setDescription(dao.getDescription());
		return model;
	}

	@Override
	public ProductDao setToDao(ProductDao dao, Product model) {
		if (dao == null) {
			dao = new ProductDao();
		}
		dao.setId(model.getId());
		dao.setName(model.getName());
		dao.setDescription(model.getDescription());
		return dao;
	}

}
