package me.yoon.myshop.entity;

import jakarta.persistence.*;
import lombok.*;
import me.yoon.myshop.dto.ItemFormDto;
import me.yoon.myshop.exception.OutOfStockException;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false)
    private String itemName;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int stockNumber;

    @Lob
    @Column(nullable = false)
    private String itemDetail;

    @Enumerated(EnumType.STRING)
    private ItemSellStatusEnum itemSellStatus;

    public void updateItem(ItemFormDto itemFormDto){
        this.itemName = itemFormDto.getItemName();
        this.price = itemFormDto.getPrice();
        this.stockNumber = itemFormDto.getStockNumber();
        this.itemDetail = itemFormDto.getItemDetail();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
    }

    public void addStock(int stockNumber){
        this.stockNumber += stockNumber;
    }

    public void removeStock(int stockNumber){
        int restStock = this.stockNumber-stockNumber;
        if(restStock<0){
            throw new OutOfStockException("상품재고가 부족합니다. (현재 재고량 : "+this.stockNumber+" )");
        }
        this.stockNumber = restStock;
    }
}
