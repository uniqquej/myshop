package me.yoon.myshop.dto;

import lombok.Getter;
import lombok.Setter;
import me.yoon.myshop.entity.ItemSellStatusEnum;

@Getter @Setter
public class ItemSearchDto {
    private String searchDateType;
    private ItemSellStatusEnum searchSellStatus;
    private String searchBy;
    private String searchQuery;
}
