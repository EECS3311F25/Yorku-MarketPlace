package eecs3311.group.p.Marketplace.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    // all messages involving the user, newest first (useful for inbox)
    @Query("SELECT m FROM ChatMessage m WHERE m.sender.id = :userId OR m.recipient.id = :userId ORDER BY m.timestamp DESC")
    List<ChatMessage> findByUserInvolvement(@Param("userId") Long userId);

    // conversation for a listing between two users, ordered oldest->newest
    @Query("SELECT m FROM ChatMessage m WHERE m.listing.id = :listingId AND " +
           "((m.sender.id = :user1Id AND m.recipient.id = :user2Id) OR " +
           "(m.sender.id = :user2Id AND m.recipient.id = :user1Id)) " +
           "ORDER BY m.timestamp ASC")
    List<ChatMessage> findChatHistory(
            @Param("listingId") Long listingId,
            @Param("user1Id") Long user1Id,
            @Param("user2Id") Long user2Id
    );
}