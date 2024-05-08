package me.yoon.myshop.dto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import me.yoon.myshop.entity.Item;
import me.yoon.myshop.entity.ItemImg;
import me.yoon.myshop.entity.ItemSellStatusEnum;
import org.modelmapper.ModelMapper;
import org.springframework.boot.availability.AvailabilityState;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ItemFormDto {
    private Long id;

    @NotBlank(message = "상품명은 필수 입력값입니다.")
    private String itemName;

    @NotNull(message = "가격은 필수 입력값입니다.")
    private int price;

    @NotBlank(message = "재고는 필수 입력값입니다.")
    private int stockNumber;

    @NotBlank(message = "이름은 필수 입력값입니다.")
    private String itemDetail;

    private ItemSellStatusEnum itemSellStatus;

    private List<ItemImgDto> itemImgDtos = new ArrayList<>();
    private List<Long> itemImgIds = new ArrayList<>();
    private static ModelMapper modelMapper = new ModelMapper();

    public Item createItem(){
        return modelMapper.map(this, Item.class);
    }

    public static ItemFormDto of(Item item){
        return modelMapper.map(item, ItemFormDto.class);
    }

}
