package eecs3311.group.p.Marketplace.dto;

import lombok.Data;

@Data
public class ChatMessageDTO {
    private String chatId;      // e.g. "42_5_8" -> listingId_userA_userB
    private Long senderId;
    private Long receiverId;
    private String senderName;  // filled by server when broadcasting
    private String content;
    private String timestamp;   // ISO string provided by server when broadcasting
}
