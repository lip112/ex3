package com.example.ex3.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO<DTO, EN> {
    //DTO
    private List<DTO> dtoList;
    private int totalPage;
    //현재페이지  번호
    private int page;
    //목록 사이즈
    private int size;
    private int start, end;

    //이전 다음
    private boolean prev, next;
    //페이지 번호 목록
    private List<Integer> pageList;

    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {
        dtoList = result.stream().map(fn).collect(Collectors.toList());

        totalPage = result.getTotalPages();

        makePageList(result.getPageable());
    }
    private void makePageList(Pageable pageable){
        this.page = pageable.getPageNumber() + 1; // 0부터 시작하니 1 추가
        this.size = pageable.getPageSize();

        int tempEnd = (int)(Math.ceil(page / 10.0)) * 10;

        start = tempEnd -9;

        prev = start > 1;

        end = totalPage > tempEnd ? tempEnd : totalPage;

        next = totalPage > tempEnd;

        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }
}
//    Filter: 주어진 조건에 맞는 요소만 걸러내는 기능입니다.
//    Map: 요소를 다른 요소로 변환하는 기능입니다.
//    Reduce: 요소들을 결합하여 하나의 값을 도출하는 기능입니다.
//    Collect: 요소들을 수집하여 컬렉션으로 반환하거나 결과값으로 반환하는 기능입니다.

//    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
//                numbers.stream()
//                .filter(n -> n % 2 == 0)
//                .map(n -> n * n)
//                .forEach(System.out::println);


