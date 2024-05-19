package me.yoon.myshop.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.yoon.myshop.entity.Review;
import me.yoon.myshop.entity.User;

@Getter @Setter
@ToString
public class ReviewFormDto {
    private Long id;

    @Min(value = 1, message = "최소 점수는 1점입니다.")
    @Max(value =5, message = "최대 점수는 5점입니다.")
    private int star;

    private String content;
}
