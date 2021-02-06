package com.productmanage.mapper;

import com.productmanage.bean.Product;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ProductMapper {
    int insertProduct(Product product);

    int deleteProductById(@Param("pdId")String pdId);

    int updateProduct(Product product);

    String selectNewPdId();

    Product selectProductById(@Param("pdId")String pdId);

    List<Product> selectProductByPage(@Param("pdName")String pdName, @Param("sortField")String sortField, @Param("sortOrder")String sortOrder,
    		@Param("pageIndex")Integer pageIndex, @Param("pageSize")Integer pageSize);

    List<Product> selectProductIdName();

    List<Product> selectProductBySheet();

    int findLength(@Param("pdName")String pdName);
}
