package me.yoon.myshop.dto;

import lombok.Getter;
import lombok.Setter;
import me.yoon.myshop.entity.Order;
import me.yoon.myshop.entity.OrderStatusEnum;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class OrderHistoryDto {
    public OrderHistoryDto(Order order){
        this.orderId = order.getId();
        this.orderDate = order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.orderStatus = order.getOrderStatus();
    }
    private Long orderId;
    private String orderDate;
    private OrderStatusEnum orderStatus;

    private List<OrderItemDto> orderItemDtoList = new ArrayList<>();

    public void addOrderItemDto(OrderItemDto orderItemDto){
        orderItemDtoList.add(orderItemDto);
    }
}
