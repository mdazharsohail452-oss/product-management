package com.example.product_management.Service;

import com.example.product_management.entity.Product;
import com.example.product_management.exception.ProductNotFoundException;
import com.example.product_management.repo.Productrepo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@Service
public class ProductService {
    private final Productrepo productrepo;
    public ProductService(Productrepo productrepo) {
        this.productrepo = productrepo;
    }
    public List<Product> getAllProducts()
    {
        return productrepo.findAll();
    }
    public Product createProduct(@RequestBody Product product) {
        return productrepo.save(product);
    }
    @Cacheable(value = "products", key = "#id")
    public Product getProductById(Long id) {
        return productrepo.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Product with ID " + id + " not found"
                        ));
    }
    @CacheEvict(value = "products", key = "#id")
    public void deleteProduct(Long id) {

        if (!productrepo.existsById(id)) {
            throw new ProductNotFoundException(
                    "Product with ID " + id + " not found"
            );
        }
    productrepo.deleteById(id);

    }
    @CachePut(value = "products", key = "#id")
    public Product updateProduct(Long id, Product product) {

        Product existingProduct = productrepo.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Product with ID " + id + " not found"
                        ));

        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());

        return productrepo.save(existingProduct);
    }

}
