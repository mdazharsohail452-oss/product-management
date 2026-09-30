package com.example.product_management.Controller;

import com.example.product_management.Service.ProductService;
import com.example.product_management.entity.Product;
import org.springframework.web.bind.annotation.*;
import com.example.product_management.Service.ProductViewService;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {
    //connect with service
    private final ProductService productService;
    private final ProductViewService productViewService;
    public ProductController(ProductService productService , ProductViewService productViewService) {
        this.productViewService = productViewService;
        this.productService = productService;
    }
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
@PostMapping
   public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
}
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {

         productViewService.incrementViews(id);

        return productService.getProductById(id);
    }
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {

        productService.deleteProduct(id);

        return "Product deleted successfully";
    }
    @PutMapping("/{id}")
    public Product updateProduct(
            @PathVariable Long id,
            @RequestBody Product product) {

        return productService.updateProduct(id, product);
    }
   //for views  on product
   @GetMapping("/{id}/views")
   public Long incrementProductViews(@PathVariable Long id) {
       return productViewService.getViews(id);
   }
  //fro popular product
  @GetMapping("/popular")
  public List<Product> getPopularProducts() {
      return productViewService.getPopularProductDetails();
  }
    @GetMapping("/recent")
    public List<String> getRecentProducts() {
        return productViewService.getRecentProducts();
    }
}
