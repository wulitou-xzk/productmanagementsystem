package com.productmanage.service;

import com.productmanage.bean.Product;

import java.util.List;

public interface ProductService {
    String insertProduct(Product product);

    int deleteProductById(String pdIds);

    Product updateProduct(Product product);

    String selectNewPdId();

    Product selectProductById(String pdId);

    List<Product> selectProductByPage(String pdName, String sortField, String sortOrder,
                                      Integer pageIndex, Integer pageSize);

    List<Product> selectProductIdName();

    List<Product> selectProductBySheet();

    int findLength(String pdName);
}
