package com.freelance.freelance_api.config;

import com.freelance.freelance_api.entities.Category;
import com.freelance.freelance_api.entities.Offer;
import com.freelance.freelance_api.entities.Role;
import com.freelance.freelance_api.entities.User;
import com.freelance.freelance_api.repositories.CategoryRepository;
import com.freelance.freelance_api.repositories.OfferRepository;
import com.freelance.freelance_api.repositories.RoleRepository;
import com.freelance.freelance_api.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;


@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(RoleRepository roleRepo,
                                   CategoryRepository catRepo,
                                   UserRepository userRepo,
                                   OfferRepository offerRepo) {
        return args -> {
            System.out.println(">> Starting database seeding...");

            if (roleRepo.count() == 0) {
                roleRepo.save(new Role("ROLE_ADMIN"));
                roleRepo.save(new Role("ROLE_SELLER"));
                roleRepo.save(new Role("ROLE_USER"));
                System.out.println(">> Roles seeded.");
            }

            if (catRepo.count() == 0) {
                catRepo.save(new Category("Programming"));
                catRepo.save(new Category("Graphic Design"));
                catRepo.save(new Category("Copywriting"));
                System.out.println(">> Categories seeded.");
            }

            if (userRepo.count() == 0) {
                Role adminRole = roleRepo.findByName("ROLE_ADMIN").orElse(null);

                User admin = new User("zhivko_dev", "zhivko@example.com", "password123");
                if (adminRole != null) {
                    admin.addRole(adminRole);
                }
                userRepo.save(admin);
                System.out.println(">> Test user seeded.");
            }

            if (offerRepo.count() == 0) {
                User author = userRepo.findByUsername("zhivko_dev").orElse(null);
                Category category = catRepo.findByName("Programming").orElse(null);

                if (author != null && category != null) {
                    Offer offer = new Offer();
                    offer.setTitle("Разработка на Spring Boot API");
                    offer.setDescription("Търся фрийлансър за довършване на backend проект.");
                    offer.setPrice(new BigDecimal("123.45"));
                    offer.setAuthor(author);
                    offer.getCategories().add(category);

                    offerRepo.save(offer);
                    System.out.println(">> Test offer seeded.");
                }
            }

            System.out.println(">> Database seeding completed successfully!");
        };
    }
}