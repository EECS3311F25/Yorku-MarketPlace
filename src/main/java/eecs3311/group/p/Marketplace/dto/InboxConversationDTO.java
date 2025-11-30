package eecs3311.group.p.Marketplace.dto;

import eecs3311.group.p.Marketplace.model.Listing;
import eecs3311.group.p.Marketplace.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InboxConversationDTO {
    private Listing listing;
    private User otherUser;
    private String lastMessage;
}