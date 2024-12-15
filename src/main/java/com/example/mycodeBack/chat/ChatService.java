package com.example.mycodeBack.chat;

import com.example.mycodeBack.chat.domain.Chat;
import com.example.mycodeBack.chat.domain.ChatMember;
import com.example.mycodeBack.chat.domain.ChatMessage;
import com.example.mycodeBack.chat.domain.repository.ChatMemberRepository;
import com.example.mycodeBack.chat.domain.repository.ChatMessageRepository;
import com.example.mycodeBack.chat.domain.repository.ChatRepository;
import com.example.mycodeBack.chat.dto.request.CreateChatRoomRequestDTO;
import com.example.mycodeBack.chat.dto.response.ChatMessageResponseDTO;
import com.example.mycodeBack.chat.dto.response.ChatResponseDTO;
import com.example.mycodeBack.code.domain.CodeType;
import com.example.mycodeBack.code.domain.repository.CodeItemRepository;
import com.example.mycodeBack.code.domain.repository.CodeTypeRepository;
import com.example.mycodeBack.code.dto.response.CodeTypeResponseDTO;
import com.example.mycodeBack.common.exception.type.ExceptionCode;
import com.example.mycodeBack.common.exception.type.UserNotFoundException;
import com.example.mycodeBack.member.domain.Member;
import com.example.mycodeBack.member.domain.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.mycodeBack.common.exception.type.ExceptionCode.NOT_FOUND_USER_ID;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final ChatMemberRepository chatMemberRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ChatResponseDTO createChatRoom(CreateChatRoomRequestDTO createChatRoomRequestDTO) {
        // 채팅방 생성
        Chat chat = Chat.builder()
                .title(createChatRoomRequestDTO.getTitle())
                .type(createChatRoomRequestDTO.getType())
                .build();
        chatRepository.save(chat);

        // 참여자 추가
        for (Long memberId : createChatRoomRequestDTO.getChatMemberIdList()) {
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new UserNotFoundException(NOT_FOUND_USER_ID));

            ChatMember chatMember = ChatMember.builder()
                    .chat(chat)
                    .member(member)
                    .role(memberId.equals(createChatRoomRequestDTO.getChatMemberIdList().get(0)) ? "ADMIN" : "MEMBER")
                    .build();

            chatMemberRepository.save(chatMember);
        }

        return ChatResponseDTO.toDTO(chat);
    }

    public List<ChatResponseDTO> findChatRoomsByUserId(String email) {
        Long memberId = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(NOT_FOUND_USER_ID))
                .getId();

        List<Chat> chat = chatMemberRepository.findChatByMemberId(memberId);
        return chat.stream()
                .map(ChatResponseDTO::toDTO)
                .toList();
    }

    @Transactional
    public ChatMessageResponseDTO saveAndSendMessage(ChatMessage chatMessage) {
        // 메시지 저장
        ChatMessage savedMessage = chatMessageRepository.save(chatMessage);

        // 채팅방 마지막 메시지 업데이트
        Chat chat = chatMessage.getChat();
        chat.setLastMessage(chatMessage.getSendAt());
        chatRepository.save(chat);

        return ChatMessageResponseDTO.toDTO(savedMessage);
    }

    public List<ChatMessageResponseDTO> getChatMessages(Long chatId) {
        return chatMessageRepository.findByChatIdOrderBySendAtDesc(chatId)
                .stream()
                .map(ChatMessageResponseDTO::toDTO)
                .toList();
    }

    @Transactional
    public void markMessagesAsRead(Long chatId, Long memberId) {
        List<ChatMessage> unreadMessages = chatMessageRepository.findUnreadMessages(chatId, memberId);
        unreadMessages.forEach(msg -> msg.setReadYn("Y"));
        chatMessageRepository.saveAll(unreadMessages);
    }
}
