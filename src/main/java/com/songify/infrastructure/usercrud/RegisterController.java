package com.songify.infrastructure.usercrud;

import com.songify.domain.usercrud.UserConformer;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
class RegisterController {

    private final UserDetailsManager userDetailsManager;
    private final UserConformer userConformer;


    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponseDto> register(@RequestBody RegisterUserRequestDto dto) {
        String password = dto.password();
        String username = dto.username();
        UserDetails user = User.builder()
                .username(username)
                .password(password)
                .build();
        userDetailsManager.createUser(user);
        return ResponseEntity.ok(new RegisterUserResponseDto("Created user"));
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam String token) {
        boolean isConfirmed = userConformer.confirmUser(token);
        return isConfirmed ? "Registration confirmed!" : "Invalid confirmation token.";
    }
}
