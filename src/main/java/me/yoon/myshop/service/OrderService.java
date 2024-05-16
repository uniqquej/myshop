package me.yoon.myshop.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import me.yoon.myshop.dto.OrderDto;
import me.yoon.myshop.entity.Item;
import me.yoon.myshop.entity.Order;
import me.yoon.myshop.entity.OrderItem;
import me.yoon.myshop.entity.User;
import me.yoon.myshop.repository.ItemRepository;
import me.yoon.myshop.repository.OrderRepository;
import me.yoon.myshop.repository.UserRepository;
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

}
