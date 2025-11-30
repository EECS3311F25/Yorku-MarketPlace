package eecs3311.group.p.Marketplace.dto;

import lombok.Data;

@Data
public class ChatMessageDTO {
    private String chatId; // Used for the WebSocket room ID
    private Long senderId;
    private Long receiverId;
    private String content;
    // We will parse the listingId from the chatId or add it here. 
    // Ideally, add listingId to the JS object in chat.html, but we can parse it from chatId string.
}