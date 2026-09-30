package com.example.product_management.Service;

import com.example.product_management.entity.Product;
import com.example.product_management.repo.Productrepo;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ProductViewService {

    private final StringRedisTemplate redisTemplate;
    private final Productrepo productRepository;

    public ProductViewService(
            StringRedisTemplate redisTemplate,
            Productrepo productRepository) {

        this.redisTemplate = redisTemplate;
        this.productRepository = productRepository;
    }

    public Long getViews(Long productId) {

        String key = "product:" + productId + ":views";

        String value = redisTemplate.opsForValue().get(key);

        if (value == null) {
            return 0L;
        }

        return Long.valueOf(value);
    }

    public void incrementViews(Long productId) {

        String key = "product:" + productId + ":views";

        redisTemplate.opsForValue().increment(key);
        redisTemplate.opsForZSet()
                .incrementScore("product:popular", productId.toString(), 1);
        redisTemplate.opsForList()
                .remove("recent:products", 0, productId.toString());
        redisTemplate.opsForList()
                .leftPush("recent:products", productId.toString());
        redisTemplate.opsForList()
                .trim("recent:products", 0, 9);
    }
    public Set<String> getPopularProducts() {

        return redisTemplate.opsForZSet()
                .reverseRange("product:popular", 0, 4);
    }
    public List<Product> getPopularProductDetails() {

        Set<String> productIds =
                redisTemplate.opsForZSet()
                        .reverseRange("product:popular", 0, 4);

        List<Product> products = new ArrayList<>();

        for (String id : productIds) {

            productRepository.findById(Long.valueOf(id))
                    .ifPresent(products::add);
        }

        return products;
    }
    //This gets the latest 10 product IDs so we can see which brought view more
    public List<String> getRecentProducts() {

        return redisTemplate.opsForList()
                .range("recent:products", 0, 9);
    }
}