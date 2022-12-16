package com.newgo.activity.supermarketapp.bootstrap;

import com.newgo.activity.supermarketapp.domain.Product;
import com.newgo.activity.supermarketapp.domain.Role;
import com.newgo.activity.supermarketapp.domain.RoleName;
import com.newgo.activity.supermarketapp.domain.User;
import com.newgo.activity.supermarketapp.repository.RoleRepository;
import com.newgo.activity.supermarketapp.repository.UserRepository;
import com.newgo.activity.supermarketapp.service.ProductService;

import org.springframework.boot.CommandLineRunner;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

import java.math.BigDecimal;

import java.nio.file.Files;

import java.util.HashSet;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProductService productService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    public DataLoader(UserRepository userRepository, ProductService productService, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.productService = productService;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) throws Exception {

        if(dataNotLoaded())
            loadData();
    }

    private boolean dataNotLoaded() {
        return roleRepository.count() == 0;
    }
    private void loadData() throws IOException {
        Role userRole = new Role();
        userRole.setRoleName(RoleName.ROLE_USER);
        userRole = roleRepository.save(userRole);

        Role adminRole = new Role();
        adminRole.setRoleName(RoleName.ROLE_ADMINISTRATOR);
        adminRole = roleRepository.save(adminRole);

        User user = new User();
        user.setUsername("username");
        user.setPassword(encoder.encode("password"));
        user.setRoles(new HashSet<>());
        user.getRoles().add(userRole);
        userRepository.save(user);

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(encoder.encode("adminPassword"));
        admin.setRoles(new HashSet<>());
        admin.getRoles().add(adminRole);
        userRepository.save(admin);

        File file = new File("src/main/resources/static/images/coconut.jpg");

        byte[] byteArray = Files.readAllBytes(file.toPath());
        Byte[] wrapByteArray = new Byte[byteArray.length];

        for(int i = 0; i < byteArray.length; i++)
            wrapByteArray[i] = byteArray[i];

        Product product = new Product();
        product.setName("First Product");
        product.setDescription("This is my new product by the way");
        product.setPhoto(wrapByteArray);
        product.setPrice(BigDecimal.valueOf(10L));

        productService.save(product);

        Product product2 = new Product();
        product2.setName("Second Product");
        product2.setDescription("This is my second product by the way");
        product2.setPhoto(wrapByteArray);
        product2.setPrice(BigDecimal.valueOf(20L));
        product2.setActive(true);

        productService.save(product2);

        Product product3 = new Product();
        product3.setName("Third Product");
        product3.setDescription("This is my third product by the way");
        product3.setPhoto(wrapByteArray);
        product3.setPrice(BigDecimal.valueOf(30L));
        product3.setActive(false);

        productService.save(product3);
    }
}
