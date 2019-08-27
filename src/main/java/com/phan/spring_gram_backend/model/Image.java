package com.phan.spring_gram_backend.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] image;

    @NotBlank(message = "Caption is required")
    private String caption;

//    @JsonFormat(pattern = "yyyy-mm-dd")
//    @Column(updatable = false)
//    private Date created_At;
//
//    @JsonFormat(pattern = "yyyy-mm-dd")
//    private Date updated_At;

    @CreationTimestamp
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;

    private int likeCount = 0;

}
