package fr.vadimsoude.repo;

import fr.vadimsoude.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.stream.Collectors;

public interface ProductRepo extends MongoRepository<Product, String> {

    default List<Product> findByNameOrContains(String nom){
        List<Product> allProduct = this.findAll();
        return allProduct.stream().filter(product -> product.getName().toLowerCase().contains(nom.toLowerCase())).toList();
    }
}