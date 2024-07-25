package com.example.mycodeBack.user;

import com.example.mycodeBack.user.dto.request.TownRequestDTO;
import com.example.mycodeBack.user.dto.request.UserRequestDTO;
import com.example.mycodeBack.user.dto.response.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    final UserService userService;

    @GetMapping("/user")
    public ResponseEntity<UserResponseDTO> getUser() {
        UserResponseDTO user = userService.selectUser();
        return ResponseEntity.ok()
                .body(user);
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserResponseDTO>> getUserList() {
        List<UserResponseDTO> userList = userService.selectUserList();
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

    @PostMapping("/filter")
    public ResponseEntity<Void> insertUserCodeFilterMap(@RequestBody List<Long> itemIdList) {
        userService.insertUserCodeFilterMap(itemIdList);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> sendAuthCodeToServer(@RequestBody String authId) {

        String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
        String CLIENT_ID = "38d56bcf00c8417e2f08d3843c3bc49e";
        String REDIRECT_URI = "http://10.0.2.2:8080/api/v1/user/auth";
        String CODE = authId;

        System.out.println(CODE);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", CLIENT_ID);
        body.add("redirect_uri", REDIRECT_URI);
        body.add("code", CODE);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                KAKAO_TOKEN_URL,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        HttpStatusCode statusCode = responseEntity.getStatusCode();

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/auth")
    public ResponseEntity<Void> getKakaoToken(@RequestBody int authId) {

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
