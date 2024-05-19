package me.yoon.myshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.yoon.myshop.entity.OrderItem;

@Getter @Setter
public class OrderItemDto {
    Long id;
    private String itemName;
    private int count;
    private int orderPrice;
    private String imgUrl;

    public OrderItemDto(OrderItem orderItem, String imgUrl){
        this.id = orderItem.getId();
        this.itemName = orderItem.getItem().getItemName();
        this.count = orderItem.getCount();
        this.orderPrice = orderItem.getOrderPrice();
        this.imgUrl = imgUrl;
    }
}
