package fr.vadimsoude.repo;

import fr.vadimsoude.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

import static fr.vadimsoude.repo.RedisUtils.addSearchToCache;
import static fr.vadimsoude.repo.RedisUtils.getUpdatedCacheContent;

public interface ProductRepo extends MongoRepository<Product, String> {

    default List<Product> findByNameOrContains(String name){

        if(getUpdatedCacheContent().contains(name)){
            System.out.println("Used REDIS");
            return RedisUtils.getProductFromCachedSearch(name);
        }

        List<Product> allProduct = this.findAll();
        System.out.println("Used MONGO, Added for 1h to REDIS");
        allProduct = allProduct.stream().filter(product -> product.getName().toLowerCase().contains(name.toLowerCase())).toList();
        addSearchToCache(name,allProduct);
        return allProduct;

    }

}