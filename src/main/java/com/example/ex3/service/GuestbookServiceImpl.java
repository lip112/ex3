package com.example.ex3.service;

import com.example.ex3.dto.GuestBookDTO;
import com.example.ex3.dto.PageRequestDTO;
import com.example.ex3.dto.PageResultDTO;
import com.example.ex3.entitiy.Guestbook;
import com.example.ex3.repository.GuestbookRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;


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

    @Override
    public PageResultDTO<GuestBookDTO, Guestbook> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());
        Page<Guestbook> reuslt = repository.findAll(pageable);

        Function<Guestbook, GuestBookDTO> fn = (entity -> entityToDto(entity));
        return new PageResultDTO<>(reuslt, fn);
    }
}
