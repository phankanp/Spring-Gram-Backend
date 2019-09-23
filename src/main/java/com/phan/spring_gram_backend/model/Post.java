package com.phan.spring_gram_backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    @NotNull(message = "Must include an image")
    @JsonIgnore
    private byte[] image;

    @NotBlank(message = "Caption is required")
    private String caption;

    @CreationTimestamp
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "post")
    private Set<Like> likes = new HashSet<>();

    @OneToMany(mappedBy = "post")
    private Set<Comment> comments;

    private String userAlias;

    private int likeCount = 0;

    public Post(byte[] image, @NotBlank(message = "Caption is required") String caption) {
        this.image = image;
        this.caption = caption;
    }

}
