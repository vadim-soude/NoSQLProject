package fr.vadimsoude.controller;
import fr.vadimsoude.entity.Product;
import fr.vadimsoude.entity.Search;
import fr.vadimsoude.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Controller
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public String showForm(Model model) {
        model.addAttribute("search", new Search());
        List<Product> allProduct = new ArrayList<>();
        model.addAttribute("product", allProduct);
        return "search";
    }

    @PostMapping
    public String processForm(@ModelAttribute("search") Search search, Model model) {
        String searchName = search.getName();
        List<Product> products = productService.findByNameOrContains(searchName);
        model.addAttribute("product", products);
        return "search";
    }
}
