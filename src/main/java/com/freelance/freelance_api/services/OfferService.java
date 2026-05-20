package com.freelance.freelance_api.services;

import com.freelance.freelance_api.dtos.OfferRequestDto;
import com.freelance.freelance_api.dtos.UserRegisterDto;
import com.freelance.freelance_api.entities.Category;
import com.freelance.freelance_api.entities.Offer;
import com.freelance.freelance_api.entities.User;
import com.freelance.freelance_api.repositories.CategoryRepository;
import com.freelance.freelance_api.repositories.OfferRepository;
import com.freelance.freelance_api.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OfferService {
    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public OfferService(OfferRepository offerRepository, UserRepository userRepository, CategoryRepository categoryRepository){
        this.offerRepository=offerRepository;
        this.userRepository=userRepository;
        this.categoryRepository=categoryRepository;
    }
    @Transactional
    public Offer createOffer(OfferRequestDto dto, String username){
        User author = userRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("User must be logged in"));
        Offer offer=new Offer();
        offer.setTitle(dto.getTitle());
        offer.setDescription(dto.getDescription());
        offer.setPrice(dto.getPrice());
        offer.setAuthor(author);
        if (dto.getCategoryIds() !=null && !dto.getCategoryIds().isEmpty()){
            Set<Category> categories = new HashSet<>(categoryRepository.findAllById(dto.getCategoryIds()));
            offer.setCategories(categories);
        }else{
            offer.setCategories(new HashSet<>());
        }
        return offerRepository.save(offer);
    }
    public List<Offer> getAllOffers(){
        return offerRepository.findAll();
    }
    public Offer getOfferById(Long id){
        return offerRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Offer not found with id: "+id));
    }
    @Transactional
    public Offer updateOffer(Long id, OfferRequestDto dto, String currentUsername){
        Offer offer = getOfferById(id);

        if (!offer.getAuthor().getUsername().equals(currentUsername)){
            throw new RuntimeException("You are not authorized to update this offer");
        }
        offer.setTitle(dto.getTitle());
        offer.setDescription(dto.getDescription());
        offer.setPrice(dto.getPrice());

        if (dto.getCategoryIds() !=null && !dto.getCategoryIds().isEmpty()){
            Set<Category> categories = new HashSet<>(categoryRepository.findAllById(dto.getCategoryIds()));
            offer.setCategories(categories);
        }else{
            offer.setCategories(new HashSet<>());
        }
        return offerRepository.save(offer);
    }
    @Transactional
    public void deleteOffer( Long id, String currentUsername){
        Offer offer = getOfferById(id);

        if (!offer.getAuthor().getUsername().equals(currentUsername)){
            throw new RuntimeException("You are not authorized to delete this offer");
        }
        offerRepository.delete(offer);
    }

}
