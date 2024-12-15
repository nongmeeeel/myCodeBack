package com.example.mycodeBack.chat.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreateChatRoomRequestDTO {
    private String title;
    private String type;
    private List<Long> chatMemberIdList;

    public void addChatMemberIdListAtFirst(Long id) {
        chatMemberIdList.add(0, id);
    }
}