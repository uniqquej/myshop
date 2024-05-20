package me.yoon.myshop.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.TransactionScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.yoon.myshop.dto.ReviewFormDto;
import me.yoon.myshop.dto.ReviewResponseDto;
import me.yoon.myshop.entity.Item;
import me.yoon.myshop.entity.OrderItem;
import me.yoon.myshop.entity.Review;
import me.yoon.myshop.entity.User;
import me.yoon.myshop.repository.ItemRepository;
import me.yoon.myshop.repository.OrderItemRepository;
import me.yoon.myshop.repository.ReviewRepository;
import me.yoon.myshop.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "review service")
@Transactional
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;

    public String findItemName(Long orderItemId){
        OrderItem orderItem = orderItemRepository.findById(orderItemId).orElseThrow(EntityNotFoundException::new);
        return orderItem.getItem().getItemName();
    }

    public Long saveReview(Long id, ReviewFormDto reviewFormDto, String email) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        Item item = itemRepository.findById(orderItem.getItem().getId())
                .orElseThrow(EntityNotFoundException::new);

        User user = userRepository.findByEmail(email);

        Review  review = Review.createReview(user, item, reviewFormDto);
        log.info("review : "+review);
        Review savedReview = reviewRepository.save(review);
        return savedReview.getId();
    }

    public Long updateReview(ReviewFormDto reviewFormDto){
        Review review = reviewRepository.findById(reviewFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);
        review.updateReview(reviewFormDto);
        return review.getId();
    }

    public boolean validateReview(Long reviewId,String email){
        User currentUser = userRepository.findByEmail(email);
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(EntityNotFoundException::new);
        return StringUtils.equals(review.getUser().getEmail(), currentUser.getEmail());
    }

    public Page<Review> findAllByItemId(Long itemId,Pageable pageable){
        int page = pageable.getPageNumber()-1;
        int pageSize = 5;
        Page<Review> reviewList = reviewRepository.getReviewList(itemId,PageRequest.of(page, pageSize));
        return reviewList;
    }

    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(EntityNotFoundException::new);
        reviewRepository.delete(review);
    }

    public Review findById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(EntityNotFoundException::new);
    }
}
