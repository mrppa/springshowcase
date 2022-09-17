package com.mrppa.springrestshowcase.product.repository;

import org.springframework.stereotype.Repository;

import com.mrppa.springrestshowcase.base.repository.BaseRepository;
import com.mrppa.springrestshowcase.product.dao.ProductDao;

@Repository
public interface ProductRepository extends BaseRepository<ProductDao, Long> {

}
