package me.yoon.myshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.yoon.myshop.entity.Item;
import me.yoon.myshop.entity.ItemSellStatusEnum;
import me.yoon.myshop.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ItemApiControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    ItemRepository itemRepository;

    @BeforeEach
    public void mockMvcSetUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        itemRepository.deleteAll();
    }

    @Test
    @DisplayName("상품 조회")
    public void findAllItems()throws Exception{
        //given
        final String url = "/api/items";

        List<Item> items = new ArrayList<>();
        for(int i=1;i<=5;i++){
            String name = "item"+i;
            String detail = "detail"+i;
            int price = 1000*i;
            int stockNumber = i;
            ItemSellStatusEnum sellStatus = ItemSellStatusEnum.SELL;

            items.add(Item.builder()
                    .itemName(name)
                    .itemDetail(detail)
                    .price(price)
                    .stockNumber(stockNumber)
                    .itemSellStatus(sellStatus)
                    .build());
        }
        itemRepository.saveAll(items);
        //when
        ResultActions result = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));
        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(5)));
    }
}