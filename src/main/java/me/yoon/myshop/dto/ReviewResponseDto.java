package me.yoon.myshop.dto;

import lombok.Getter;
import lombok.Setter;
import me.yoon.myshop.entity.Review;

import java.time.LocalDateTime;

@Getter @Setter
public class ReviewResponseDto {
    private String writer;
    private int star;
    private String content;
    private LocalDateTime registerTime;

    public ReviewResponseDto(Review review){
        String[] emailFront = review.getUser().getEmail().split("@")[0].split("");
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< emailFront.length-3;i++){
            sb.append(emailFront[i]);
        }
        sb.append("**");

        this.writer = sb.toString();
        this.content = review.getContent();
        this.star = review.getStar();
        this.registerTime = getRegisterTime();
    }
}
