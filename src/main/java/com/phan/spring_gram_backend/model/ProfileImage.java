package com.phan.spring_gram_backend.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "ProfileImg")
public class ProfileImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] profileImage;

//    @OneToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "user_id")
//    private User user;

//    public ProfileImage(byte[] profileImage, User user) {
//        this.profileImage = profileImage;
//        this.user = user;
//    }
}
