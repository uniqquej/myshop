package me.yoon.myshop.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import me.yoon.myshop.dto.OrderDto;
import me.yoon.myshop.dto.OrderHistoryDto;
import me.yoon.myshop.dto.OrderItemDto;
import me.yoon.myshop.entity.*;
import me.yoon.myshop.repository.ItemImgRepository;
import me.yoon.myshop.repository.ItemRepository;
import me.yoon.myshop.repository.OrderRepository;
import me.yoon.myshop.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ItemImgRepository itemImgRepository;

    public Long order(OrderDto orderDto, String email){
        Item item = itemRepository.findById(orderDto.getItemId())
                .orElseThrow(EntityNotFoundException::new);
        User user = userRepository.findByEmail(email);

        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem =
                OrderItem.createOrderItem(item, orderDto.getCount());

        orderItemList.add(orderItem);

        Order order = Order.createOrder(user, orderItemList);
        orderRepository.save(order);

        return order.getId();
    }

    @Transactional(readOnly = true)
    public Page<OrderHistoryDto> getOrderList(String email, Pageable pageable){
        List<Order> orders = orderRepository.findOrders(email,pageable);
        Long totalCount = orderRepository.countOrder(email);

        List<OrderHistoryDto> orderHistoryDtoList = new ArrayList<>();

        for(Order order: orders){
            OrderHistoryDto orderHistoryDto = new OrderHistoryDto(order);
            List<OrderItem> orderItemList = order.getOrderItems();
            for(OrderItem orderItem:orderItemList){
                ItemImg itemImg = itemImgRepository.findByItemIdAndRepresentationImgYn(orderItem.getItem().getId(),"Y");
                OrderItemDto orderItemDto = new OrderItemDto(orderItem, itemImg.getImgUrl());
                orderHistoryDto.addOrderItemDto(orderItemDto);
            }
            orderHistoryDtoList.add(orderHistoryDto);
        }
        return new PageImpl<OrderHistoryDto>(orderHistoryDtoList,pageable,totalCount);
    }

}
