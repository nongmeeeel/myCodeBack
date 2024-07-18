package com.example.mycodeBack.user;

import com.example.mycodeBack.user.dto.request.TownRequestDTO;
import com.example.mycodeBack.user.dto.request.UserRequestDTO;
import com.example.mycodeBack.user.dto.response.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    final UserService userService;

    @GetMapping("/user")
    public ResponseEntity<UserResponseDTO> getUser() {
        UserResponseDTO user = userService.getUser();
        return ResponseEntity.ok()
                .body(user);
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserResponseDTO>> getUserList() {
        List<UserResponseDTO> userList = userService.getUserList();
        return ResponseEntity.ok()
                .body(userList);
    }

    @PutMapping("/town")
    public ResponseEntity<Void> updateUserTown(@RequestBody TownRequestDTO townRequestDTO, Principal principal) {
        userService.updateUserTown(townRequestDTO);

        return ResponseEntity.noContent()
                .build();
    }

    @PostMapping("/town")
    public ResponseEntity<Void> insertTownTemp(@RequestBody TownRequestDTO townRequestDTO, Principal principal) {
        userService.insertTownTemp(townRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
