package me.yoon.myshop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.yoon.myshop.entity.Item;

@Getter
@Setter
@NoArgsConstructor
public class ItemResponseDto {
    private String itemName;
    private int price;
    private int stockNumber;
    private String itemDetail;
    private String sellStatus;

    public ItemResponseDto(Item item){
        this.itemName = item.getItemName();
        this.price = item.getPrice();
        this.stockNumber = item.getStockNumber();
        this.itemDetail = item.getItemDetail();
        this.sellStatus = item.getItemSellStatus().toString();
    }
}
