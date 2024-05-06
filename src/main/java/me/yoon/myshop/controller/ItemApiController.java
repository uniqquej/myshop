package me.yoon.myshop.controller;

import lombok.RequiredArgsConstructor;
import me.yoon.myshop.dto.ItemResponseDto;
import me.yoon.myshop.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ItemApiController {
    private final ItemService itemService;

    @GetMapping("/items")
    public ResponseEntity<List<ItemResponseDto>> findAllItems() {
        List<ItemResponseDto> items = itemService.findAll()
                .stream()
                .map(ItemResponseDto::new)
                .toList();

        return ResponseEntity.ok().body(items);
    }

    @GetMapping("/item/{id}")
    public ResponseEntity<ItemResponseDto> findItem(@PathVariable Long id){
        ItemResponseDto item = new ItemResponseDto(itemService.findById(id));
        return ResponseEntity.ok().body(item);
    }
}