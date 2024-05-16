package me.yoon.myshop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.yoon.myshop.dto.OrderDto;
import me.yoon.myshop.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j(topic = "order controller")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public @ResponseBody ResponseEntity order(
            @RequestBody @Valid OrderDto orderDto,
            BindingResult bindingResult,
            Principal principal
    ){
        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for(FieldError fieldError : fieldErrors){
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String email = principal.getName();
        Long orderId;
        log.info("order controller email : "+email);
        try{
            orderId = orderService.order(orderDto,email);
        }catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST );
        }
        return new ResponseEntity<Long>(orderId, HttpStatus.OK );
    }
}
