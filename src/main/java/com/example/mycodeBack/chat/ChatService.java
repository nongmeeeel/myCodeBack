package com.example.mycodeBack.chat;

import com.example.mycodeBack.chat.domain.Chat;
import com.example.mycodeBack.chat.domain.ChatMember;
import com.example.mycodeBack.chat.domain.ChatMessage;
import com.example.mycodeBack.chat.domain.repository.ChatMemberRepository;
import com.example.mycodeBack.chat.domain.repository.ChatMessageRepository;
import com.example.mycodeBack.chat.domain.repository.ChatRepository;
import com.example.mycodeBack.chat.dto.request.ChatMessageRequestDTO;
import com.example.mycodeBack.chat.dto.request.CreateChatRoomRequestDTO;
import com.example.mycodeBack.chat.dto.response.ChatMessageResponseDTO;
import com.example.mycodeBack.chat.dto.response.ChatResponseDTO;
import com.example.mycodeBack.chat.dto.response.ChatWithMemberResponseDTO;
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
import java.util.Optional;

import static com.example.mycodeBack.common.exception.type.ExceptionCode.NOT_FOUND_USER_ID;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final ChatMemberRepository chatMemberRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ChatWithMemberResponseDTO createChatRoom(CreateChatRoomRequestDTO createChatRoomRequestDTO, Long thisId) {

        createChatRoomRequestDTO.addChatMemberIdListAtFirst(thisId);

        // 1:1 채팅방인 경우 기존 채팅방 확인
        if ("PRIVATE".equals(createChatRoomRequestDTO.getType())
                && createChatRoomRequestDTO.getChatMemberIdList().size() == 2) {

            Optional<Chat> existingChat = chatMemberRepository
                    .findPrivateChatByMemberIds(
                            createChatRoomRequestDTO.getChatMemberIdList().get(0),
                            createChatRoomRequestDTO.getChatMemberIdList().get(1)
                    );
            if (existingChat.isPresent()) {
                Long chatId = existingChat.get().getId();
                ChatWithMemberResponseDTO chatWithMember = chatRepository.findChatWithMember(chatId, thisId);

                return chatWithMember;
            }
        }
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

            ChatMember savedChatMember = chatMemberRepository.save(chatMember);
            chat.addChatMember(savedChatMember);
        }

        ChatWithMemberResponseDTO chatWithMember = chatRepository.findChatWithMember(chat.getId(), thisId);
        // 채팅방과 멤버 정보를 함께 조회
//        Chat savedChat = chatRepository.findByIdWithMembers(chat.getId())
//                .orElseThrow(() -> new RuntimeException("채팅방을 찾을 수 없습니다"));

        return chatWithMember;
    }

    public List<ChatWithMemberResponseDTO> findChatRoomsByUserId(Long memberId) {

        List<ChatWithMemberResponseDTO> chatWithMemberResponseDTO = chatRepository.findChatListByMemberId(memberId);
        return chatWithMemberResponseDTO;
    }

    @Transactional
    public ChatMessageResponseDTO saveAndSendMessage(ChatMessageRequestDTO chatMessageRequestDTO, Long thisMemberId) {

        Chat chat = chatRepository.findById(chatMessageRequestDTO.getChatId())
                .orElseThrow(() -> new RuntimeException("Chat이 존재하지 않아!"));

        Member member = memberRepository.findById(thisMemberId).orElseThrow(() -> new UserNotFoundException(NOT_FOUND_USER_ID));

        ChatMessage chatMessage = ChatMessage.builder()
                .id(chatMessageRequestDTO.getId())
                .chat(chat)
                .member(member)
                .content(chatMessageRequestDTO.getContent())
                .type(chatMessageRequestDTO.getType())
                .readYn(chatMessageRequestDTO.getReadStatus())
                .sendAt(chatMessageRequestDTO.getSendAt())
                .build();

        // 메시지 저장
        ChatMessage savedMessage = chatMessageRepository.save(chatMessage);

        // 채팅방 마지막 메시지 업데이트
        chat.setLastMessage(chatMessage.getSendAt());
        chatRepository.save(chat);

        return ChatMessageResponseDTO.toDTO(savedMessage);
    }

    public List<ChatMessageResponseDTO> getChatMessages(Long chatId) {
        return chatMessageRepository.findByChatIdOrderBySendAt(chatId)
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
