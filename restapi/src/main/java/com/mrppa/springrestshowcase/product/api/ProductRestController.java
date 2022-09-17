package com.mrppa.springrestshowcase.product.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrppa.springrestshowcase.base.api.BaseRestController;
import com.mrppa.springrestshowcase.product.dao.ProductDao;
import com.mrppa.springrestshowcase.product.model.Product;
import com.mrppa.springrestshowcase.product.services.ProductService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/product")
@SecurityRequirement(name = "Bearer Authentication")
public class ProductRestController extends BaseRestController<ProductDao, Product, Long> {

	@Autowired
	public ProductRestController(ProductService productService) {
		super(productService);
	}

}
