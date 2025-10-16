package com.songify.domain.usercrud;

import com.songify.domain.crud.util.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(generator = "users_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "users_id_seq",
            sequenceName = "users_id_seq",
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @Column(nullable = false)
    @Size(min = 8)
    private String password;

    private String confirmationToken;

    private boolean enabled = false;

    private Collection<String> authorities = new HashSet<>();

    public boolean confirm() {
        this.setEnabled(true);
        this.setConfirmationToken(null);
        return true;
    }

    public User(final String email, final String password, String confirmationToken, final Collection<String> authorities) {
        this.email = email;
        this.password = password;
        this.confirmationToken = confirmationToken;
        this.authorities = authorities;
    }
}
