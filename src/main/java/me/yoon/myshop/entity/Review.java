package me.yoon.myshop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.*;
import me.yoon.myshop.dto.ReviewFormDto;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Review extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int star;

    public static Review createReview(User user, Item item, ReviewFormDto reviewFormDto){
        return Review.builder()
                .star(reviewFormDto.getStar())
                .content(reviewFormDto.getContent())
                .user(user)
                .item(item)
                .build();
    }

}
