package com.example.ex3.service;

import com.example.ex3.dto.GuestBookDTO;
import com.example.ex3.dto.PageRequestDTO;
import com.example.ex3.dto.PageResultDTO;
import com.example.ex3.entitiy.Guestbook;

public interface GuestbookService {
    Long register(GuestBookDTO dto);

    PageResultDTO<GuestBookDTO, Guestbook> getList(PageRequestDTO requestDTO);

    default Guestbook dtoToEntitiy(GuestBookDTO dto){
        Guestbook entitiy = Guestbook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return entitiy;
    }

    default GuestBookDTO entityToDto(Guestbook entity){
        GuestBookDTO dto = GuestBookDTO.builder()
                .gno(entity.getGno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();
        return dto;
    }
}
