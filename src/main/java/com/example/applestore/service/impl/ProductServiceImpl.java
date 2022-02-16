package com.example.applestore.service.impl;

import com.example.applestore.model.Product;
import com.example.applestore.repository.ProductRepository;
import com.example.applestore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }//findAll

    public void updateProduct(Product product){
        productRepository.save(product);
    }//add or update (tuy vao pri-key)

    public void removeProductById(long id){
        productRepository.deleteById(id);
    }//delete dua vao pri-key

    public Optional<Product> getProductById(long id){
        return productRepository.findById(id);
    }//search theo id

    public List<Product> getAllProductByCategoryId(int id){ return productRepository.findAllByCategory_Id(id); }
    //findList theo ProductDTO.categoryId
}
