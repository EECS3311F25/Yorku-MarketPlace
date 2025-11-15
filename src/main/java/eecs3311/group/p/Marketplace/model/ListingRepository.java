package eecs3311.group.p.Marketplace.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ListingRepository extends JpaRepository<Listing, Long> {
    List<Listing> findByOwner(User owner);
}
