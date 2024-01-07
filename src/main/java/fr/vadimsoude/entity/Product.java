package fr.vadimsoude.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "product")
public class Product {
    private String name;
    private double price;
    private Integer quantity;
    public Product(){}

    public Product(String name, double price, Integer quantity){
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public String toString(){
        return (name + "|" + price + "|" + quantity);
    }

}
