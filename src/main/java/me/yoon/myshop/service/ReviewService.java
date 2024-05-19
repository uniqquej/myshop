package me.yoon.myshop.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.yoon.myshop.dto.ReviewFormDto;
import me.yoon.myshop.entity.Item;
import me.yoon.myshop.entity.OrderItem;
import me.yoon.myshop.entity.Review;
import me.yoon.myshop.entity.User;
import me.yoon.myshop.repository.ItemRepository;
import me.yoon.myshop.repository.OrderItemRepository;
import me.yoon.myshop.repository.ReviewRepository;
import me.yoon.myshop.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "review service")
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
}
