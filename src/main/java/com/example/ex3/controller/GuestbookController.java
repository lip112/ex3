package com.example.ex3.controller;

import com.example.ex3.dto.PageRequestDTO;
import com.example.ex3.service.GuestbookService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@AllArgsConstructor
public class GuestbookController {
    private final GuestbookService service;
    @GetMapping({"/"})
    public String list(){
        return "redirect:/guestbook/list";
    }

    @GetMapping("/list")
    //PageRequestDTO를 안받아도 기본값으로 0폐이지부터 10사이즈로 보여주는데 받는 이유는 따로 원하고싶은 page,size를 프론트에서 받을수 있도록 매개변수에 적는다.
    public void list(PageRequestDTO pageRequestDTO, Model model){
        log.info("list..............");
        model.addAttribute("result", service.getList(pageRequestDTO));
    }
}
