package com.phan.spring_gram_backend.core;

import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.phan.spring_gram_backend.config.CloudinaryConfig;
import com.phan.spring_gram_backend.model.Comment;
import com.phan.spring_gram_backend.model.Like;
import com.phan.spring_gram_backend.model.Post;
import com.phan.spring_gram_backend.model.User;
import com.phan.spring_gram_backend.repository.LikeRepository;
import com.phan.spring_gram_backend.repository.PostRepository;
import com.phan.spring_gram_backend.repository.ProfileImageRepository;
import com.phan.spring_gram_backend.service.CommentService;
import com.phan.spring_gram_backend.service.PostService;
import com.phan.spring_gram_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class DatabaseLoader implements ApplicationRunner {

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    ProfileImageRepository profileImageRepository;

    @Autowired
    CloudinaryConfig cloudinaryConfig;

    private String getImageUrl(byte[] image) {
        Map uploadResult =  cloudinaryConfig.upload(image, ObjectUtils.asMap("resourcetype", "auto", "transformation", new Transformation().width(854).height(576) ));

        return (String) uploadResult.get("url");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        LocalDateTime createDateTime = LocalDateTime.now();
        LocalDateTime updateDateTime = LocalDateTime.now();

        User testUser1 = new User("guestUser@gmail.com", "Guest User", "Guest User", "password", "password", "https://res.cloudinary.com/djmrmontu/image/upload/v1571573333/profile_images/rmu1_tleftn.jpg");
        User testUser2 = new User("testUser2@gmail.com", "Martina Emiel", "Martina Emiel", "password", "password", "https://res.cloudinary.com/djmrmontu/image/upload/v1572746645/profile_images/56_rdib4j.jpg");
        User testUser3 = new User("testUser3@gmail.com", "Cailyn Lake", "Cailyn Lake", "password", "password", "https://res.cloudinary.com/djmrmontu/image/upload/v1572746626/profile_images/65_binraq.jpg");
        User testUser4 = new User("testUser4@gmail.com", "Benjamin Marlowe", "Benjamin Marlowe", "password", "password", "https://res.cloudinary.com/djmrmontu/image/upload/v1572746682/profile_images/18_f67t5e.jpg");
        User testUser5 = new User("testUser5@gmail.com", "Triston Raquel", "Triston Raquel", "password", "password", "https://res.cloudinary.com/djmrmontu/image/upload/v1572746670/profile_images/43_qvsn4h.jpg");
        User testUser6 = new User("testUser6@gmail.com", "Steve Byron", "Steve Byron", "password", "password", "https://res.cloudinary.com/djmrmontu/image/upload/v1572746661/profile_images/73_wvvisz.jpg");


        userService.saveUser(testUser1);
        userService.saveUser(testUser2);
        userService.saveUser(testUser3);
        userService.saveUser(testUser4);
        userService.saveUser(testUser5);
        userService.saveUser(testUser6);

        Post p1 = new Post(
                "https://res.cloudinary.com/djmrmontu/image/upload/v1571571108/ueh4cieej6kjtj5hsvfv.jpg",
                "Hanging out at Yosemite National Park",
                testUser1,
                testUser1.getAlias(),
                "Yosemite National Park, California"
        );
        Post p2 = new Post(
                "https://res.cloudinary.com/djmrmontu/image/upload/v1571625006/post_images/qzvi6bqfjekjzosretnx.jpg",
                "Visiting Mount Fuji!",
                testUser2,
                testUser2.getAlias(),
                "Shizuoka, Japan"
        );
        Post p3 = new Post(
                "https://res.cloudinary.com/djmrmontu/image/upload/c_scale,h_576,w_854/v1572748065/post_images/great_wall_ampog9.jpg",
                "Hiking the Great Wall!",
                testUser3,
                testUser3.getAlias(),
                "Huairou, China"
        );
        Post p4 = new Post(
                "https://res.cloudinary.com/djmrmontu/image/upload/v1572763732/post_images/bwdwobdpmzyuhjlhgik1.jpg",
                "Taking a helicopter tour around Hawaii!",
                testUser4,
                testUser4.getAlias(),
                "Honolulu, Hawaii"
        );
        Post p5 = new Post(
                "https://res.cloudinary.com/djmrmontu/image/upload/c_scale,h_576,w_854/v1572748304/post_images/snorkling_turtle_j0lt99.jpg",
                "Hanging out with the turtles",
                testUser5,
                testUser5.getAlias(),
                "Maui, Hawaii"
        );
        Post p6 = new Post(
                "https://res.cloudinary.com/djmrmontu/image/upload/c_scale,h_576,w_854/v1572748426/post_images/surfing_lj64xd.jpg",
                "Catching some waves in Hawaii!",
                testUser6,
                testUser6.getAlias(),
                "O'ahu, Hawaii"
        );
        Post p7 = new Post(
                "https://res.cloudinary.com/djmrmontu/image/upload/c_scale,h_576,w_854/v1572750509/post_images/nnzkZNYWHaU-unsplash_smho3c.jpg",
                "First time visiting the Eiffel Tower",
                testUser1,
                testUser1.getAlias(),
                "Paris, France"
        );
        Post p8 = new Post(
                "https://res.cloudinary.com/djmrmontu/image/upload/c_scale,h_576,w_854/v1572750476/post_images/photo-1486299267070-83823f5448dd_qet1ut.jpg",
                "Hanging out with Big Ben!",
                testUser2,
                testUser2.getAlias(),
                "London, United Kingdom"
        );
        Post p9 = new Post(
                "https://res.cloudinary.com/djmrmontu/image/upload/c_scale,h_576,w_854/v1572750442/post_images/bDn1Wi1ixLw-unsplash_lhfpsq.jpg",
                "Chilling in the desert",
                testUser3,
                testUser3.getAlias(),
                "Arizona, United States"
        );
        Post p10 = new Post(
                "https://res.cloudinary.com/djmrmontu/image/upload/c_scale,h_576,w_854/v1572753111/post_images/photo-1474524955719-b9f87c50ce47_newlhr.jpg",
                "Watching the sunrise!",
                testUser5,
                testUser5.getAlias(),
                "San Diego, United States"
        );
        Post p11 = new Post(
                "https://res.cloudinary.com/djmrmontu/image/upload/c_scale,h_576,w_854/v1572750401/post_images/1OtUkD_8svc-unsplash_lxloqb.jpg",
                "Looking at the stars!",
                testUser4,
                testUser4.getAlias(),
                "Arizona, United States"
        );
        Post p12 = new Post(
                "https://res.cloudinary.com/djmrmontu/image/upload/c_scale,h_576,w_854/v1572750372/post_images/eOpewngf68w-unsplash_eyppal.jpg",
                "Having fun walking through the woods",
                testUser6,
                testUser6.getAlias(),
                "Whangarei, New Zealand"
        );

        postRepository.save(p1);
        postRepository.save(p2);
        postRepository.save(p3);
        postRepository.save(p4);
        postRepository.save(p5);
        postRepository.save(p6);
        postRepository.save(p7);
        postRepository.save(p8);
        postRepository.save(p9);
        postRepository.save(p10);
        postRepository.save(p11);
        postRepository.save(p12);

        Comment dbLoaderComment1 = new Comment();
        Comment dbLoaderComment2 = new Comment();
        Comment dbLoaderComment3 = new Comment();
        Comment dbLoaderComment4 = new Comment();
        Comment dbLoaderComment5 = new Comment();
        Comment dbLoaderComment6 = new Comment();
        Comment dbLoaderComment7 = new Comment();
        Comment dbLoaderComment8 = new Comment();
        Comment dbLoaderComment9 = new Comment();
        Comment dbLoaderComment10 = new Comment();
        Comment dbLoaderComment11 = new Comment();
        Comment dbLoaderComment12 = new Comment();

        dbLoaderComment1.setComment("Have fun!");
        dbLoaderComment2.setComment("Looks amazing!");
        dbLoaderComment3.setComment("Have a blast!");
        dbLoaderComment4.setComment("Will be there soon!");
        dbLoaderComment5.setComment("Enjoy your trip!");
        dbLoaderComment6.setComment("Looking forward to meeting up!");
        dbLoaderComment7.setComment("Always wanted to visit!");
        dbLoaderComment8.setComment("Looks great!");
        dbLoaderComment9.setComment("The world is so amazing!");
        dbLoaderComment10.setComment("Nature is so beautiful!");
        dbLoaderComment11.setComment("Nature is amazing!");
        dbLoaderComment12.setComment("Wow! amazing views!");

        commentService.saveComment(dbLoaderComment1, p1.getId(), testUser2.getUsername());
        commentService.saveComment(dbLoaderComment2, p2.getId(), testUser3.getUsername());
        commentService.saveComment(dbLoaderComment3, p3.getId(), testUser4.getUsername());
        commentService.saveComment(dbLoaderComment4, p4.getId(), testUser5.getUsername());
        commentService.saveComment(dbLoaderComment5, p5.getId(), testUser6.getUsername());
        commentService.saveComment(dbLoaderComment6, p6.getId(), testUser2.getUsername());
        commentService.saveComment(dbLoaderComment7, p7.getId(), testUser3.getUsername());
        commentService.saveComment(dbLoaderComment8, p8.getId(), testUser4.getUsername());
        commentService.saveComment(dbLoaderComment9, p9.getId(), testUser5.getUsername());
        commentService.saveComment(dbLoaderComment10, p10.getId(), testUser6.getUsername());
        commentService.saveComment(dbLoaderComment11, p11.getId(), testUser2.getUsername());
        commentService.saveComment(dbLoaderComment12, p12.getId(), testUser3.getUsername());

        userService.addFollowDBLoader(testUser1.getId(), testUser2.getUsername());
        userService.addFollowDBLoader(testUser1.getId(), testUser3.getUsername());
        userService.addFollowDBLoader(testUser2.getId(), testUser1.getUsername());
        userService.addFollowDBLoader(testUser3.getId(), testUser1.getUsername());

        userService.addFollowDBLoader(testUser1.getId(), testUser4.getUsername());
        userService.addFollowDBLoader(testUser1.getId(), testUser5.getUsername());
        userService.addFollowDBLoader(testUser4.getId(), testUser1.getUsername());
        userService.addFollowDBLoader(testUser5.getId(), testUser1.getUsername());

        Like l1 = new Like();

        l1.setPost(p1);
        l1.setUser(testUser1);
        l1.setUsername(testUser1.getUsername());

        likeRepository.save(l1);
    }
}
