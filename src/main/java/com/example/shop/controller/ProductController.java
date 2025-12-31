package com.example.shop.controller;

import com.example.shop.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {

    private final List<Product> products = new ArrayList<>();
    private final List<Product> cart = new ArrayList<>();

    public ProductController() {
        // Mock Data
        products.add(new Product("1", "Wireless Headphones", 199.99, "🎧", "Premium noise cancelling headphones."));
        products.add(new Product("2", "Smart Watch", 249.50, "⌚", "Fitness tracker and smartwatch."));
        products.add(new Product("3", "E-Reader", 129.00, "📚", "Glare-free display for reading."));
        products.add(new Product("4", "Gaming Mouse", 59.99, "🖱️", "High precision gaming mouse."));
        products.add(new Product("5", "Mechanical Keyboard", 110.00, "⌨️", "Clicky mechanical keys."));
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("products", products);
        model.addAttribute("cartSize", cart.size());
        return "index";
    }

    @GetMapping("/cart")
    public String viewCart(Model model) {
        double total = cart.stream().mapToDouble(Product::getPrice).sum();
        model.addAttribute("cart", cart);
        model.addAttribute("total", total);
        return "cart";
    }

    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam String productId) {
        Optional<Product> product = products.stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst();
        
        product.ifPresent(cart::add);
        return "redirect:/";
    }

    @PostMapping("/remove-from-cart")
    public String removeFromCart(@RequestParam String productId) {
        // Remove first occurrence
        for (Product p : cart) {
            if (p.getId().equals(productId)) {
                cart.remove(p);
                break;
            }
        }
        return "redirect:/cart";
    }
}
