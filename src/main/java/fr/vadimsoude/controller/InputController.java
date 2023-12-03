package fr.vadimsoude.controller;
import fr.vadimsoude.entity.Product;
import fr.vadimsoude.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@org.springframework.stereotype.Controller
@RequestMapping("/input")
public class InputController {
    private final ProductService productService;
    @Autowired
    public InputController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping
    public String showForm(Model model) {
        model.addAttribute("product", new Product());
        return "input";
    }
    @PostMapping
    public String processForm(@ModelAttribute("product") Product product) {
        productService.saveNewProduct(product);
        return "redirect:/input";
    }
}
