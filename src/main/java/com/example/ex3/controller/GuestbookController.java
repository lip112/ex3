package com.example.ex3.controller;

import com.example.ex3.dto.GuestBookDTO;
import com.example.ex3.dto.PageRequestDTO;
import com.example.ex3.service.GuestbookService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@AllArgsConstructor
public class GuestbookController {
    private final GuestbookService service;

    @GetMapping({"/"})
    public String list() {
        return "redirect:/guestbook/list";
    }

    @GetMapping("/list")
    //PageRequestDTO를 안받아도 기본값으로 0폐이지부터 10사이즈로 보여주는데 받는 이유는 따로 원하고싶은 page,size를 프론트에서 받을수 있도록 매개변수에 적는다.
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("list..............");
        model.addAttribute("result", service.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register() {
        log.info("register .....get");
    }

    @PostMapping("/register")
    public String registerPost(GuestBookDTO dto, RedirectAttributes redirectAttributes) {
        log.info("dto...." + dto);

        Long gno = service.register(dto);
        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";
    }

    @GetMapping({"/read", "/modify"})
    //@ModelAttribute는 원래 아래처럼 한줄 써야하지만 파라메터로 받는 동시에 모델을 생성하는 것이다.
    //model.addAttribute("requestDTO", requestDTO); 이거를 파라메터에 적은것이다.
    public void read(long gno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("gno: " +gno);

        GuestBookDTO dto = service.read(gno);

        model.addAttribute("dto", dto);
    }

    @PostMapping("/remove")
    //RedirectAttributes는 Post로 결과를 처리하고 그 결과를 그대로 유지하고 싶을때 사용한다.
    //일반적으로 POST 요청으로 데이터를 전달받은 후, 해당 데이터를 처리하고 결과를 표시하기 위해 리다이렉트를 수행하는 경우가 많습니다.
    // 이 경우, 리다이렉트 이전에 처리한 결과를 리다이렉트 이후에도 유지하고 싶은 경우가 있습니다. 이때 RedirectAttributes를 사용하여
    // 데이터를 전달할 수 있습니다.
    public String remove(Long gno, RedirectAttributes redirectAttributes) {
        log.info("gno...." + gno);
        service.remove(gno);

        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";
    }
    @PostMapping("/modify")
    public String modify(GuestBookDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes) {
        log.info("post modify...........................");
        log.info("dto " + dto);

        service.modify(dto);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("gno", dto.getGno());

        return "redirect:/guestbook/read";
    }
}
