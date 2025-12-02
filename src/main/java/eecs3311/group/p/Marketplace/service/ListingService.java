package eecs3311.group.p.Marketplace.service;

import eecs3311.group.p.Marketplace.model.ChatMessageRepository;
import eecs3311.group.p.Marketplace.model.Listing;
import eecs3311.group.p.Marketplace.model.ListingRepository;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListingService {

    private final ListingRepository listingRepository;
    private  ChatMessageRepository chatMessageRepository;

    public ListingService(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    public Listing createListing(Listing listing) {
        return listingRepository.save(listing);
    }

    public List<Listing> getAllListings() {
        return listingRepository.findAll();
    }
    public Listing getListingById(Long id) {
        return listingRepository.findById(id).orElseThrow(() -> new RuntimeException("Listing not found"));
    }

    // @Transactional // <--- IMPORTANT: Required for delete operations
    // public void deleteListing(Long listingId) {
    //     // 1. Delete all chat messages for this listing first
    //     chatMessageRepository.deleteByListingId(listingId);
        
    //     // 2. NOW you can delete the listing safely
    //     listingRepository.deleteById(listingId);
    // }
    public boolean userOwnsListing(Long listingId, String username) {
        Listing listing = getListingById(listingId);
        if (listing == null) return false;
        return listing.getOwner().getUsername().equals(username);
    }

    public Listing updateListing(Long id, Listing updated, String username) {
        if (!userOwnsListing(id, username)) return null;

        Listing listing = getListingById(id);
        listing.setTitle(updated.getTitle());
        listing.setDescription(updated.getDescription());
        listing.setPrice(updated.getPrice());

        return listingRepository.save(listing);
    }

    public boolean deleteListing(Long id, String username) {
        if (!userOwnsListing(id, username)) return false;

        listingRepository.deleteById(id);
        return true;
    }
}
