package com.example.product_management.exception;

public class ProductNotFoundException extends RuntimeException {

        public ProductNotFoundException(String message) {
            super(message);
        }
}
