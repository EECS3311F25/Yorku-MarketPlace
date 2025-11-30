package eecs3311.group.p.Marketplace.service;

import eecs3311.group.p.Marketplace.dto.InboxConversationDTO;
import eecs3311.group.p.Marketplace.model.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final AuthService authService;
    private final ListingService listingService;

    public ChatService(ChatMessageRepository chatMessageRepository, AuthService authService, ListingService listingService) {
        this.chatMessageRepository = chatMessageRepository;
        this.authService = authService;
        this.listingService = listingService;
    }

    public ChatMessage saveMessage(Long senderId, Long recipientId, Long listingId, String content) {
        User sender = authService.findById(senderId).orElseThrow();
        User recipient = authService.findById(recipientId).orElseThrow();
        Listing listing = listingService.getListingById(listingId);

        ChatMessage msg = new ChatMessage();
        msg.setSender(sender);
        msg.setRecipient(recipient);
        msg.setListing(listing);
        msg.setContent(content);
        msg.setTimestamp(LocalDateTime.now());

        return chatMessageRepository.save(msg);
    }

    public List<ChatMessage> getChatHistory(Long listingId, Long user1Id, Long user2Id) {
        return chatMessageRepository.findByListingIdAndSenderIdAndRecipientIdOrListingIdAndSenderIdAndRecipientIdOrderByTimestampAsc(
            listingId, user1Id, user2Id, listingId, user2Id, user1Id
        );
    }

    public List<InboxConversationDTO> getUserConversations(Long currentUserId) {
        List<ChatMessage> allMessages = chatMessageRepository.findByUserInvolvement(currentUserId);
        
        // Map to store unique conversations. Key: ListingID + "_" + OtherUserID
        Map<String, InboxConversationDTO> conversationMap = new LinkedHashMap<>();

        for (ChatMessage msg : allMessages) {
            User otherUser = msg.getSender().getId().equals(currentUserId) ? msg.getRecipient() : msg.getSender();
            String key = msg.getListing().getId() + "_" + otherUser.getId();

            if (!conversationMap.containsKey(key)) {
                conversationMap.put(key, new InboxConversationDTO(
                    msg.getListing(),
                    otherUser,
                    msg.getContent() // Since we sorted by DESC, the first one found is the latest
                ));
            }
        }

        return new ArrayList<>(conversationMap.values());
    }
}