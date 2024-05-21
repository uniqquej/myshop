package me.yoon.myshop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.yoon.myshop.dto.ItemSearchDto;
import me.yoon.myshop.dto.MainItemDto;
import me.yoon.myshop.service.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j(topic = "home controller")
public class HomeController {
    private final ItemService itemService;

    @GetMapping("/")
    public String home(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0,6);
        Page<MainItemDto> items =
                itemService.getMainItemPage(itemSearchDto, pageable);
        model.addAttribute("items",items);
        model.addAttribute("itemSearchDto",itemSearchDto);
        model.addAttribute("maxPage",5);
        return "index";
    }

    @GetMapping("/forbidden")
    public String forbidden() {
        log.warn("비정상적인 접근: 403 forbidden");
        /*따로 횟수를 기록한다던지 로직*/
        return "redirect:/";
    }
}
