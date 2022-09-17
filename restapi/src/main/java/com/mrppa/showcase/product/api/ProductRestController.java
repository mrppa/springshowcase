package com.mrppa.showcase.product.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrppa.showcase.base.api.BaseRestController;
import com.mrppa.showcase.product.dao.ProductDao;
import com.mrppa.showcase.product.model.Product;
import com.mrppa.showcase.product.services.ProductService;

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
