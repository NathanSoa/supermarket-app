package com.newgo.activity.supermarketapp.bootstrap;

import com.newgo.activity.supermarketapp.domain.Product;
import com.newgo.activity.supermarketapp.domain.Role;
import com.newgo.activity.supermarketapp.domain.User;
import com.newgo.activity.supermarketapp.repository.ProductRepository;
import com.newgo.activity.supermarketapp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.io.File;
import java.nio.file.Files;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public DataLoader(UserRepository userRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setRole(Role.USER);

        userRepository.save(user);

        System.out.println("Loaded first user");

        User user2 = new User();
        user2.setUsername("username2");
        user2.setPassword("password2");
        user2.setRole(Role.ADMINISTRATOR);

        userRepository.save(user2);

        System.out.println("Loaded second user");

        File file = new File("src/main/resources/static/images/coconut.jpg");
        System.out.println(file.getAbsolutePath());

        byte[] byteArray = Files.readAllBytes(file.toPath());
        Byte[] wrapByteArray = new Byte[byteArray.length];

        for(int i = 0; i < byteArray.length; i++)
            wrapByteArray[i] = byteArray[i];

        Product product = new Product();
        product.setDescription("This is my new product by the way");
        product.setPhoto(wrapByteArray);

        productRepository.save(product);
    }
}
