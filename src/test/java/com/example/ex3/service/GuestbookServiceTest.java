package com.example.ex3.service;

import com.example.ex3.dto.GuestBookDTO;
import com.example.ex3.dto.PageRequestDTO;
import com.example.ex3.dto.PageResultDTO;
import com.example.ex3.entitiy.Guestbook;
import com.example.ex3.repository.GuestbookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.function.Function;

@SpringBootTest
public class GuestbookServiceTest {
    @Autowired
    private GuestbookService service;

    @Autowired
    private GuestbookRepository repository;

    @Test
    public void testRegister() {
        GuestBookDTO guestBookDTO = GuestBookDTO.builder()
                .title("Sample Title...")
                .content("Sample Content...")
                .writer("user0")
                .build();
        System.out.println(service.register(guestBookDTO));
    }

    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();

        PageResultDTO<GuestBookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);

        System.out.println("resultDTO.isPrev() = " + resultDTO.isPrev());
        System.out.println("resultDTO.isNext() = " + resultDTO.isNext());
        System.out.println("resultDTO.getTotalPage() = " + resultDTO.getTotalPage());
        System.out.println("----------------");
        for (GuestBookDTO guestBookDTO : resultDTO.getDtoList()){
            System.out.println("guestBookDTO = " + guestBookDTO);
        }

        System.out.println("==============");
        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }

    @Test
    public void testSerach() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .type("tc") // 검색조건 t, c, w, tc, tcw
                .keyword("한글")
                .build();
        PageResultDTO<GuestBookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);

        System.out.println("resultDTO.isPrev = " + resultDTO.isPrev());
        System.out.println("resultDTO.isNext() = " + resultDTO.isNext());
        System.out.println("resultDTO.getDtoList() = " + resultDTO.getDtoList());

        System.out.println("====================");
        for (GuestBookDTO guestBookDTO : resultDTO.getDtoList()) {
            System.out.println("guestBookDTO = " + guestBookDTO);
        }
        System.out.println("====================");
        resultDTO.getPageList().forEach(i -> System.out.println(i));

    }
}