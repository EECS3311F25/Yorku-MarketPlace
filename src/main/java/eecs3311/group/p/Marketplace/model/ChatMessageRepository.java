package eecs3311.group.p.Marketplace.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    // Fetch history for a specific context (Listing + Buyer + Seller)
    List<ChatMessage> findByListingIdAndSenderIdAndRecipientIdOrListingIdAndSenderIdAndRecipientIdOrderByTimestampAsc(
            Long listingId1, Long senderId1, Long recipientId1,
            Long listingId2, Long senderId2, Long recipientId2
    );

    // Fetch all messages involving a user (for the inbox)
    @Query("SELECT m FROM ChatMessage m WHERE m.sender.id = :userId OR m.recipient.id = :userId ORDER BY m.timestamp DESC")
    List<ChatMessage> findByUserInvolvement(@Param("userId") Long userId);
}