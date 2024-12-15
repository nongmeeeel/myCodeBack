package com.example.mycodeBack.chat;

import com.example.mycodeBack.chat.domain.ChatMessage;
import com.example.mycodeBack.chat.dto.request.CreateChatRoomRequestDTO;
import com.example.mycodeBack.chat.dto.response.ChatMessageResponseDTO;
import com.example.mycodeBack.chat.dto.response.ChatResponseDTO;
import com.example.mycodeBack.code.dto.response.CodeTypeResponseDTO;
import com.example.mycodeBack.common.config.auth.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public ChatMessageResponseDTO sendMessage(@Payload ChatMessage chatMessage) {
        return chatService.saveAndSendMessage(chatMessage);
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<ChatResponseDTO>> selectChatRoomList(Authentication authentication) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();

        List<ChatResponseDTO> chatRooms = chatService.findChatRoomsByUserId(customUser.getEmail());
        return ResponseEntity.ok(chatRooms);
    }

    @PostMapping("/room")
    public ResponseEntity<ChatResponseDTO> createChatRoom(@RequestBody CreateChatRoomRequestDTO createChatRoomRequestDTO, Authentication authentication) {
        CustomUser thisUser = (CustomUser) authentication.getPrincipal();

        createChatRoomRequestDTO.addChatMemberIdListAtFirst(thisUser.getId());

        ChatResponseDTO chatRoom = chatService.createChatRoom(createChatRoomRequestDTO);
        return ResponseEntity.ok(chatRoom);
    }

    @GetMapping("/messages/{chatId}")
    public ResponseEntity<List<ChatMessageResponseDTO>> selectChatMessageList(@PathVariable Long chatId) {
        List<ChatMessageResponseDTO> messages = chatService.getChatMessages(chatId);
        return ResponseEntity.ok(messages);
    }


    @PostMapping("/messages/read")
    public ResponseEntity<Void> markMessagesAsRead(
            @RequestParam Long chatId,
            @RequestParam Long memberId
    ) {
            chatService.markMessagesAsRead(chatId, memberId);
            return ResponseEntity.ok().build();
    }
}
