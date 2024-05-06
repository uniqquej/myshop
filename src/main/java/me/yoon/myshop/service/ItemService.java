package me.yoon.myshop.service;

import lombok.RequiredArgsConstructor;
import me.yoon.myshop.dto.ItemResponseDto;
import me.yoon.myshop.entity.Item;
import me.yoon.myshop.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Item findById(Long id){
        return itemRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 아이템이 없습니다."));
    }

}
