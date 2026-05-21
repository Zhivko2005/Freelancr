package com.freelance.freelance_api.controllers;

import com.freelance.freelance_api.entities.Offer;
import com.freelance.freelance_api.services.OfferService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/view/offers")
public class OfferViewController {
    private final OfferService offerService;
    public OfferViewController(OfferService offerService){
        this.offerService=offerService;
    }

    @GetMapping
    public String displayOffersPage(Model model, Authentication authentication){
        List<Offer> offers = offerService.getAllOffers();
        model.addAttribute("offers", offers);
        if(authentication!=null){
            model.addAttribute("currentUsername", authentication.getName());
        }
        return "offers";
    }
}
