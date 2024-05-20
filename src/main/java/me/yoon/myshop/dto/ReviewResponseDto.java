package me.yoon.myshop.dto;

import lombok.Getter;
import lombok.Setter;
import me.yoon.myshop.entity.Review;

import java.time.format.DateTimeFormatter;

@Getter @Setter
public class ReviewResponseDto {
    private Long id;
    private String writer;
    private int star;
    private String content;
    private String registerTime;

    public ReviewResponseDto(Review review){
//        String[] emailFront = review.getUser().getEmail().split("@")[0].split("");
//        System.out.println("*********************************"+ Arrays.toString(emailFront));
//        StringBuilder sb = new StringBuilder();
//        if(emailFront.length<=2) sb.append("*");
//        else {
//            for(int i=0; i< emailFront.length-3;i++){
//                sb.append(emailFront[i]);
//            }
//            sb.append("**");
//        }

//        this.writer = sb.toString();
        this.id = review.getId();
        this.writer = review.getUser().getEmail();
        this.content = review.getContent();
        this.star = review.getStar();
        this.registerTime = review.getRegisterTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));;
    }
}
