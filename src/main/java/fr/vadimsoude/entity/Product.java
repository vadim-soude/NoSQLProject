package fr.vadimsoude.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "product")
public class Product {
    @Setter private String name;
    @Setter private double price;
}
