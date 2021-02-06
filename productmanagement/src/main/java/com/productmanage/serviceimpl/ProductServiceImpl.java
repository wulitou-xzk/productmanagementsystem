package com.productmanage.serviceimpl;

import com.productmanage.bean.Product;
import com.productmanage.mapper.DepotItemMapper;
import com.productmanage.mapper.NotificationMapper;
import com.productmanage.mapper.ProductMapper;
import com.productmanage.mapper.TurnOverMapper;
import com.productmanage.service.ProductService;
import com.productmanage.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public String insertProduct(Product product) {
        if(productMapper.insertProduct(product) <= 0){
            return "商品添加失败";
        }
        return "商品添加成功";
    }

    // 删除一个商品
    @Override
    @CacheEvict(value = "product", key = "#pdId")
    public int deleteProductById(String pdId) {
        return productMapper.deleteProductById(pdId);
    }

    // 更新产品信息，可更新产品价格、产品剩余数量
    @Override
    @CachePut(value = "product", key = "#product.pdId", unless = "#result == null")
    public Product updateProduct(Product product) {
        if(productMapper.updateProduct(product) <= 0){
            return null;
        }
        return product;
    }

    // 获取一条新的产品编号
    @Override
    public String selectNewPdId() {
        StringBuffer newPdId = new StringBuffer(productMapper.selectNewPdId());
        newPdId.append(StringUtil.getIdStr(2));
        return newPdId.toString();
    }

    // 根据编号获取产品
    @Override
    @Cacheable(cacheNames = {"product"}, key = "#pdId", unless = "#result == null")
    public Product selectProductById(String pdId) {
        Product product = productMapper.selectProductById(pdId);
        return product;
    }

    // 获取多个产品信息
    @Override
    public List<Product> selectProductByPage(String pdName, String sortField, String sortOrder, Integer pageIndex, Integer pageSize) {
        pdName = (pdName == null) ? "" : pdName.trim();
        pageIndex *= pageSize;
        List<Product> productList = productMapper.selectProductByPage(pdName, sortField, sortOrder, pageIndex, pageSize);
        return productList;
    }

    // 获取所有产品名称
    @Override
    public List<Product> selectProductIdName() {
        List<Product> productList = productMapper.selectProductIdName();
        return productList;
    }

    @Override
    public List<Product> selectProductBySheet() {
        return productMapper.selectProductBySheet();
    }

    @Override
    public int findLength(String pdName) {
        pdName = (pdName == null) ? "" : pdName.trim();
        int total = productMapper.findLength(pdName);
        return total;
    }
}
