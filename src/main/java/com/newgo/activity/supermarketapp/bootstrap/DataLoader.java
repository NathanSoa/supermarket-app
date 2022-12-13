package com.newgo.activity.supermarketapp.bootstrap;

import com.newgo.activity.supermarketapp.domain.Product;
import com.newgo.activity.supermarketapp.domain.Role;
import com.newgo.activity.supermarketapp.domain.User;
import com.newgo.activity.supermarketapp.repository.UserRepository;
import com.newgo.activity.supermarketapp.service.ProductService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProductService productService;

    public DataLoader(UserRepository userRepository, ProductService productService) {
        this.userRepository = userRepository;
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setRole(Role.USER);

        userRepository.save(user);

        User user2 = new User();
        user2.setUsername("username2");
        user2.setPassword("password2");
        user2.setRole(Role.ADMINISTRATOR);

        userRepository.save(user2);

        File file = new File("src/main/resources/static/images/coconut.jpg");

        byte[] byteArray = Files.readAllBytes(file.toPath());
        Byte[] wrapByteArray = new Byte[byteArray.length];

        for(int i = 0; i < byteArray.length; i++)
            wrapByteArray[i] = byteArray[i];

        Product product = new Product();
        product.setName("First Product");
        product.setDescription("This is my new product by the way");
        product.setPhoto(wrapByteArray);

        productService.save(product);

        Product product2 = new Product();
        product2.setName("Second Product");
        product2.setDescription("This is my second product by the way");
        product2.setPhoto(wrapByteArray);
        product2.setActive(true);

        productService.save(product2);

        Product product3 = new Product();
        product3.setName("Third Product");
        product3.setDescription("This is my third product by the way");
        product3.setPhoto(wrapByteArray);
        product3.setActive(false);

        productService.save(product3);
    }
}
