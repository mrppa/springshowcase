package com.mrppa.showcase.product.repository;

import org.springframework.stereotype.Repository;

import com.mrppa.showcase.base.repository.BaseRepository;
import com.mrppa.showcase.product.dao.ProductDao;

@Repository
public interface ProductRepository extends BaseRepository<ProductDao, Long> {

}
