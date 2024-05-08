package me.yoon.myshop.dto;

import lombok.Getter;
import lombok.Setter;
import me.yoon.myshop.entity.ItemImg;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class ItemImgDto {
    private Long id;
    private String imgName;
    private String oriImgName;
    private String imgUrl;
    private String representationImgYn;
    private static ModelMapper modelMapper = new ModelMapper();
    public static ItemImgDto of(ItemImg itemImg){
        return modelMapper.map(itemImg,ItemImgDto.class);
    }
}
