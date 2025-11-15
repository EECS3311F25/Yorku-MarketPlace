package eecs3311.group.p.Marketplace.controller;

import eecs3311.group.p.Marketplace.model.Listing;
import eecs3311.group.p.Marketplace.model.User;
import eecs3311.group.p.Marketplace.service.AuthService;
import eecs3311.group.p.Marketplace.service.FileStorageService;
import eecs3311.group.p.Marketplace.service.ListingService;

import java.io.IOException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ListingController {
    @Autowired
    private final ListingService listingService;
    private final AuthService authService;

    private final FileStorageService fileStorageService;

    public ListingController(ListingService listingService,
                            AuthService authService,
                            FileStorageService fileStorageService) {
        this.listingService = listingService;
        this.authService = authService;
        this.fileStorageService = fileStorageService;
    }

    // Show the listing creation form
    @GetMapping("/create-listing")
    public String showCreateListing(Model model) {
        model.addAttribute("listing", new Listing());
        return "create-listing";
    }

        // Process the form
    @PostMapping("/create-listing")
    public String createListing(@ModelAttribute Listing listing,
                                @RequestParam("image") MultipartFile imageFile,
                                Authentication auth) throws IOException {

        User owner = authService.findByUsername(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        listing.setOwner(owner);

        if (!imageFile.isEmpty()) {
            String imageUrl = fileStorageService.saveFile(imageFile);
            listing.setImageUrl(imageUrl);
        }

        listingService.createListing(listing);

        return "redirect:/home";
    }

    @GetMapping("/listing/{id}")
    public String viewListing(@PathVariable Long id, Model model, Principal principal) {
        Listing listing = listingService.getListingById(id);
        model.addAttribute("listing", listing);

        boolean isOwner = principal != null && 
            listing.getOwner().getUsername().equals(principal.getName());
        model.addAttribute("isOwner", isOwner);

        return "listing-details";
    }

    @GetMapping("/listing/{id}/edit")
    public String editListingPage(@PathVariable Long id, Model model, Principal principal) {
        if (!listingService.userOwnsListing(id, principal.getName())) {
            return "redirect:/access-denied";
        }

        model.addAttribute("listing", listingService.getListingById(id));
        return "edit-listing";
    }

    @PostMapping("/listing/{id}/edit")
    public String submitEdit(@PathVariable Long id, Listing form, Principal principal) {
        Listing updated = listingService.updateListing(id, form, principal.getName());
        if (updated == null) return "redirect:/access-denied";

        return "redirect:/listing/" + id;
    }

    @PostMapping("/listing/{id}/delete")
    public String deleteListing(@PathVariable Long id, Principal principal) {
        boolean ok = listingService.deleteListing(id, principal.getName());
        if (!ok) return "redirect:/access-denied";

        return "redirect:/home";
    }

}
