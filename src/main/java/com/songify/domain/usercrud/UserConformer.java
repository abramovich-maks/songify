package com.songify.domain.usercrud;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserConformer {

    private final MailSender mailSender;
    private final UserRepository userRepository;

    public void sendConfirmationEmail(User user) {
        String to = user.getEmail();
        String subject = "Complete your registration";
        String text = "To confirm your account, please click here: "
                + "https://localhost:8443/users/confirm?token=" + user.getConfirmationToken();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    @Transactional
    public boolean confirmUser(String confirmationToken) {
        User user = userRepository.findByConfirmationToken(confirmationToken)
                .orElseThrow(() -> new RuntimeException("user not found"));
        return user.confirm();
    }
}

