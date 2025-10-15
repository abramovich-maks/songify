package com.songify.infrastructure.security.jwt;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessagesController {

    @GetMapping("/message")
    public ResponseEntity<MessageDto> message(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        String name;
        if (principal instanceof OAuth2User oauthUser) {
            name = oauthUser.getAttribute("given_name");
        } else {
            name = authentication.getName();
        }
        MessageDto message = new MessageDto("Hi " + name);
        return ResponseEntity.ok(message);
    }
}