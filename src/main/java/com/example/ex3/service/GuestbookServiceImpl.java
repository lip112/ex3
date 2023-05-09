package com.example.ex3.service;

import com.example.ex3.dto.GuestBookDTO;
import com.example.ex3.entitiy.Guestbook;
import com.example.ex3.repository.GuestbookRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;


@Service
@Log4j2
@AllArgsConstructor
public class GuestbookServiceImpl implements GuestbookService{
    private final GuestbookRepository repository;
    @Override
    public Long register(GuestBookDTO dto) {
        log.info("DTO------------");
        log.info(dto);

        Guestbook entity = dtoToEntitiy(dto);
        repository.save(entity);
        log.info(entity);
        return entity.getGno();
    }
}
