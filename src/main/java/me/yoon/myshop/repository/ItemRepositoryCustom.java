package me.yoon.myshop.repository;

import me.yoon.myshop.dto.ItemSearchDto;
import me.yoon.myshop.dto.MainItemDto;
import me.yoon.myshop.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {
    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
