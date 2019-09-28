package com.phan.spring_gram_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "Users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "Username needs to be an valid email")
    @NotBlank(message = "username is required")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "Please enter alias.")
    @Column(nullable = false, unique = true)
    private String alias;

    @NotBlank(message = "Please enter your full name")
    private String fullName;

    @NotBlank(message = "Password field is required")
    private String password;

    @Transient
    private String confirmPassword;

    @CreationTimestamp
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;

    @JsonIgnore
    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<Comment> comments;

    @JsonIgnore
    @OneToMany(
            mappedBy = "following",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Followers> followers = new HashSet<>();

    @JsonIgnore
    @OneToMany(
            mappedBy = "userFollower",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Followers> following = new HashSet<>();

    public User(@Email(message = "Username needs to be an valid email") @NotBlank(message = "username is required") String username, @NotBlank(message = "Please enter alias.") String alias, @NotBlank(message = "Please enter your full name") String fullName, @NotBlank(message = "Password field is required") String password, String confirmPassword) {
        this.username = username;
        this.alias = alias;
        this.fullName = fullName;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
