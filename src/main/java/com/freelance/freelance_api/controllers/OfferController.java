package com.freelance.freelance_api.controllers;

import com.freelance.freelance_api.dtos.OfferRequestDto;
import com.freelance.freelance_api.entities.Offer;
import com.freelance.freelance_api.services.OfferService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/offers")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }
    @PostMapping
    public ResponseEntity<Offer> createOffer(@Valid @RequestBody OfferRequestDto dto, Principal principal){
        Offer createdOffer = offerService.createOffer(dto,principal.getName());
        return new ResponseEntity<>(createdOffer, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<Offer>>getAllOffers(){
        return ResponseEntity.ok(offerService.getAllOffers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Offer> getOfferById(@PathVariable Long id){
        return ResponseEntity.ok(offerService.getOfferById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Offer> updateOffer(@PathVariable Long id, @Valid @RequestBody OfferRequestDto dto, Principal principal) {
        Offer updatedOffer = offerService.updateOffer(id, dto, principal.getName());
        return ResponseEntity.ok(updatedOffer);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteOffer(@PathVariable Long id, Principal principal){
        offerService.deleteOffer(id, principal.getName());
        return ResponseEntity.ok("Offer deleted successfully");
    }
}
