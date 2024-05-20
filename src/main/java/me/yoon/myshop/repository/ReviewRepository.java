package me.yoon.myshop.repository;

import me.yoon.myshop.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("select ri from Review ri " +
            "where ri.item.id = :itemId " +
            "order by ri.registerTime desc")
    Page<Review> getReviewList(@Param("itemId") Long itemId, PageRequest pageRequest);
}
