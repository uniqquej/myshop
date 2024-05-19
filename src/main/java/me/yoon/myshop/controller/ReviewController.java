package me.yoon.myshop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.yoon.myshop.dto.ItemSearchDto;
import me.yoon.myshop.dto.MainItemDto;
import me.yoon.myshop.dto.ReviewFormDto;
import me.yoon.myshop.dto.ReviewResponseDto;
import me.yoon.myshop.entity.Review;
import me.yoon.myshop.service.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j(topic = "review controller")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/review")
    public String review(@RequestParam("id") Long id, Model model){
        String itemName = reviewService.findItemName(id);
        model.addAttribute("itemName", itemName);
        model.addAttribute("reviewFormDto",new ReviewFormDto());

        return "review/reviewForm";
    }

    @PostMapping("/review")
    public String saveReview(
            @RequestParam("id") Long id,
            @Valid ReviewFormDto reviewFormDto,
            BindingResult bindingResult,
            Model model,
            Principal principal
            ){
        log.info(id+","+reviewFormDto.toString());
        if(bindingResult.hasErrors()) return "redirect:/orders";

        try {
            reviewService.saveReview(id,reviewFormDto,principal.getName());
        }catch (Exception e){
            model.addAttribute("errorMessage", "리뷰 등록 중 에러가 발생했습니다.");
            return "review/reviewForm";
        }
        return "redirect:/orders";
    }

    @GetMapping("/reviews/{itemId}")
    public String reviews(@PathVariable("itemId") Long itemId, Model model){
        List<ReviewResponseDto> reviewResponseDtos =
                reviewService.findAllByItemId(itemId)
                        .stream()
                        .map(ReviewResponseDto::new)
                        .toList();
        model.addAttribute("reviews",reviewResponseDtos);

        return "review/reviewList";
    }

}
