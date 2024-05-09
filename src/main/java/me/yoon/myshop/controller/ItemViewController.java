package me.yoon.myshop.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.yoon.myshop.dto.ItemFormDto;
import me.yoon.myshop.dto.ItemSearchDto;
import me.yoon.myshop.entity.Item;
import me.yoon.myshop.service.ItemService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ItemViewController {
    private final ItemService itemService;
    @GetMapping("/admin/item/new")
    public String itemForm(Model model){
        model.addAttribute("itemFormDto", new ItemFormDto());
        return "item/itemForm";
    }
    @PostMapping("/admin/item/new")
    public String itemNew(
            @Valid ItemFormDto itemFormDto,
            BindingResult bindingResult,
            Model model,
            @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList
    ){
        if(bindingResult.hasErrors()) return "item/itemForm";

        if(itemImgFileList.get(0).isEmpty()&& itemFormDto.getId()==null){
            model.addAttribute("errorMessage","첫번째 상품 이미지는 필수 입력값입니다.");
            return "item/itemForm";
        }
        try {
            itemService.saveItem(itemFormDto, itemImgFileList);
        }catch (Exception e){
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생했습니다.");
            return "item/itemForm";
        }
        return "redirect:/";
    }

    @GetMapping("/admin/item/{itemId}")
    public String itemDtl(@PathVariable("itemId")Long itemId,  Model model){
        try{
            ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
            model.addAttribute("itemFormDto", itemFormDto);
        }catch(EntityNotFoundException e){
            model.addAttribute("errorMessages","존재하지 않는 상품입니다.");
            model.addAttribute("itemFormDto",new ItemFormDto());
            return "item/itemForm";
        }
        return "item/itemForm";

    }

    @PostMapping("/admin/item/{itemId}")
    public String itemUpdate(
            @Valid ItemFormDto itemFormDto,
            BindingResult bindingResult,
            @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList,
            Model model
    ){
        if(bindingResult.hasErrors()) return "item/itemForm";
        if(itemImgFileList.get(0).isEmpty()&& itemFormDto.getId()==null){
            model.addAttribute("errorMessage","첫번재 상품 이미지는 필수 입력값입니다.");
            return "item/itemForm";
        }
        try{
            itemService.updateItem(itemFormDto,itemImgFileList);
        }catch (Exception e){
            model.addAttribute("errorMessage","상품 수정 중 오류가 발생했습니다.");
            return "item/itemForm";
        }
        return "redirect:/";
    }

    @GetMapping(value = {"/admin/items","admin/items/{page}"})
    public String itemManage(
            ItemSearchDto itemSearchDto,
            @PathVariable("page") Optional<Integer> page,
            Model model
    ){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get():0,3);
        Page<Item> items =
                itemService.getAdminItemPage(itemSearchDto,pageable);
        model.addAttribute("items",items);
        model.addAttribute("itemSearchDto",itemSearchDto);
        model.addAttribute("maxPage",5);
        return "item/itemMng";
    }

}
