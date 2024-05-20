package me.yoon.myshop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.yoon.myshop.dto.ReviewFormDto;
import me.yoon.myshop.dto.ReviewResponseDto;
import me.yoon.myshop.entity.Review;
import me.yoon.myshop.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

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

    @PostMapping("/review/{reviewId}")
    public String updateReview(
            @Valid ReviewFormDto reviewFormDto,
            BindingResult bindingResult,
            Model model,
            Principal principal
    ) {
        if (bindingResult.hasErrors()) return "review/reviewForm";
        if (!reviewService.validateReview(reviewFormDto.getId(), principal.getName())) {
            model.addAttribute("errorMessage", "리뷰 수정 권한이 없습니다.");
            return "redirect:/";
        }
            try {
                reviewService.updateReview(reviewFormDto);
            } catch (Exception e) {
                model.addAttribute("errorMessage", "리뷰 수정 중 오류가 발생했습니다.");
                return "review/reviewForm";
            }
            return "redirect:/";
        }

    @GetMapping("/review/{reviewId}")
    public String reviewUpdatePage(@PathVariable("reviewId") Long reviewId, Model model){
        Review review = reviewService.findById(reviewId);
        ReviewFormDto reviewFormDto = new ReviewFormDto(review);
        model.addAttribute("reviewFormDto", reviewFormDto);
        return "review/reviewForm";
    }

    @DeleteMapping("/review/{reviewId}")
    public @ResponseBody ResponseEntity deleteReview(
            @PathVariable("reviewId") Long reviewId,
            Principal principal
    ){
        if(!reviewService.validateReview(reviewId, principal.getName())){
            return new ResponseEntity<String>("주문 취소 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        reviewService.deleteReview(reviewId);
        return new ResponseEntity<Long>(reviewId, HttpStatus.OK);
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
