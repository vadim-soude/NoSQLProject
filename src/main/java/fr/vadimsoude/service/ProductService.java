package fr.vadimsoude.service;

import fr.vadimsoude.entity.Product;
import fr.vadimsoude.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static fr.vadimsoude.repo.RedisUtils.updateCache;

@Service
public class ProductService {
    private final ProductRepo productRepo;

    @Autowired
    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public void saveNewProduct(Product newProduct) {
        productRepo.save(newProduct);
        updateCache(newProduct.getName());
    }

    public List<Product> findByNameOrContains(String nom) {
        return productRepo.findByNameOrContains(nom);
    }
}
