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
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;
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
        //Function은 인자를 받아서 다른 타입으로 변환해주는 함수형 인터페이스입니다.
        Function<Guestbook, GuestBookDTO> fn = (entity -> entityToDto(entity));
        return new PageResultDTO<>(reuslt, fn);
    }

    @Override
    public GuestBookDTO read(Long gno) {
        Optional<Guestbook> result = repository.findById(gno);
        return result.isPresent()? entityToDto(result.get()): null;
    }

    @Override
    public void remove(Long gno) {
        repository.deleteById(gno);
    }

    @Override
    public void modify(GuestBookDTO dto) {
        Optional<Guestbook> result = repository.findById(dto.getGno());

        if (result.isPresent()) {
            Guestbook entity = result.get();

            entity.ChangeTitle(dto.getTitle());
            entity.ChangeContent(dto.getContent());

            repository.save(entity);
        }
    }
}
