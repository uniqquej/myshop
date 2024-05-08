package me.yoon.myshop.service;

import lombok.RequiredArgsConstructor;
import me.yoon.myshop.dto.ItemFormDto;
import me.yoon.myshop.dto.ItemResponseDto;
import me.yoon.myshop.entity.Item;
import me.yoon.myshop.entity.ItemImg;
import me.yoon.myshop.repository.ItemImgRepository;
import me.yoon.myshop.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;

    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception{
        Item item = itemFormDto.createItem();
        itemRepository.save(item);

        for(int i=0; i<itemImgFileList.size();i++){
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);
            if(i==0){
                itemImg.setRepresentationImgYn("Y");
            }else{
                itemImg.setRepresentationImgYn("N");
            }
            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }
        return item.getId();
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Item findById(Long id){
        return itemRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 아이템이 없습니다."));
    }

}
