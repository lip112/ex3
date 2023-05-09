package com.example.ex3.service;

import com.example.ex3.dto.GuestBookDTO;
import com.example.ex3.entitiy.Guestbook;

public interface GuestbookService {
    Long register(GuestBookDTO dto);

    default Guestbook dtoToEntitiy(GuestBookDTO dto){
        Guestbook entitiy = Guestbook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return entitiy;
    }

}
