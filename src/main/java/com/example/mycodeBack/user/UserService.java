package com.example.mycodeBack.user;

import com.example.mycodeBack.code.domain.CodeItem;
import com.example.mycodeBack.code.domain.repository.CodeItemRepository;
import com.example.mycodeBack.user.domain.Town;
import com.example.mycodeBack.user.domain.User;
import com.example.mycodeBack.user.domain.UserCodeFilterMap;
import com.example.mycodeBack.user.domain.repository.TownRepository;
import com.example.mycodeBack.user.domain.repository.UserCodeFilterMapRepository;
import com.example.mycodeBack.user.domain.repository.UserRepository;
import com.example.mycodeBack.user.dto.request.TownRequestDTO;
import com.example.mycodeBack.user.dto.request.UserRequestDTO;
import com.example.mycodeBack.user.dto.response.UserResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TownRepository townRepository;
    private final CodeItemRepository codeItemRepository;
    private final UserCodeFilterMapRepository userCodeFilterMapRepository;

    public UserResponseDTO selectUser() {
        Long tempUserId = 1L;
        User user = userRepository.findById(tempUserId).orElseThrow();
        return UserResponseDTO.toDTO(user);
    }

    public List<UserResponseDTO> selectUserList() {
        List<User> userList = userRepository.findAllByUseYn("Y");
        return userList.stream()
                .map(user -> UserResponseDTO.toDTO(user))
                .collect(Collectors.toList());
    }
    @Transactional
    public void updateUserTown(TownRequestDTO townRequestDTO) {
        Long tempUserId = 1L;
        Town town = TownRequestDTO.toEntity(townRequestDTO);

        Optional<Town> townOp = townRepository.findById(town.getTownCode());
        if (!townOp.isPresent()) {
            townRepository.saveAndFlush(town);
        }

        User user = userRepository.findById(tempUserId).orElseThrow();
        user.updateTownCode(town);
    }

    @Transactional
    public void insertTownTemp(TownRequestDTO townRequestDTO) {
        Town town = TownRequestDTO.toEntity(townRequestDTO);

        Optional<Town> townOp = townRepository.findById(town.getTownCode());
        if (!townOp.isPresent()) {
            townRepository.saveAndFlush(town);
            System.out.println("저장완료 : " + town.getTownCode());
        }
    }

    @Transactional
    public void insertUserCodeFilterMap(List<Long> itemIdList) {
        Long tempUserId = 1L;

        User user = userRepository.findById(tempUserId).orElseThrow();
        userCodeFilterMapRepository.deleteByUser(user);

        List<UserCodeFilterMap> userCodeFilterMapList = itemIdList.stream()
            .map(itemId -> {
                CodeItem codeItem = codeItemRepository.findById(itemId).orElseThrow();
                return UserCodeFilterMap.builder()
                .user(user)
                .codeItem(codeItem)
                .build();
            }).collect(Collectors.toList());

        userCodeFilterMapRepository.saveAll(userCodeFilterMapList);

    }



}
