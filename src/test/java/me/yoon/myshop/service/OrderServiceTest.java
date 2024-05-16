package me.yoon.myshop.service;

import me.yoon.myshop.entity.ItemSellStatusEnum;
import me.yoon.myshop.entity.OrderStatusEnum;
import me.yoon.myshop.dto.OrderDto;
import me.yoon.myshop.entity.Item;
import me.yoon.myshop.entity.User;
import me.yoon.myshop.entity.Order;
import me.yoon.myshop.entity.OrderItem;
import me.yoon.myshop.repository.ItemRepository;
import me.yoon.myshop.repository.UserRepository;
import me.yoon.myshop.repository.OrderRepository;
import me.yoon.myshop.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    UserRepository userRepository;

    public Item saveItem(){
        Item item = new Item();
        item.setItemName("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatusEnum.SELL);
        item.setStockNumber(100);
        return itemRepository.save(item);
    }

    public User saveUser(){
        User user = new User();
        user.setEmail("test@test.com");
        return userRepository.save(user);

    }

    @Test
    @DisplayName("주문 테스트")
    public void order(){
        Item item = saveItem();
        User user = saveUser();

        OrderDto orderDto = new OrderDto();
        orderDto.setCount(10);
        orderDto.setItemId(item.getId());

        Long orderId = orderService.order(orderDto, user.getEmail());
        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);

        List<OrderItem> orderItems = order.getOrderItems();

        int totalPrice = orderDto.getCount()*item.getPrice();

        assertEquals(totalPrice, order.getTotalPrice());
    }


}