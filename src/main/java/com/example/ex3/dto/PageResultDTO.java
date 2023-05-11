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
        //    Filter: 주어진 조건에 맞는 요소만 걸러내는 기능입니다.
        //    Map: 요소를 다른 요소로 변환하는 기능입니다.
        //    Reduce: 요소들을 결합하여 하나의 값을 도출하는 기능입니다.
        //    Collect: 요소들을 수집하여 컬렉션으로 반환하거나 결과값으로 반환하는 기능입니다.

        //    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        //                numbers.stream()
        //                .filter(n -> n % 2 == 0)
        //                .map(n -> n * n)
        //                .forEach(System.out::println);

        totalPage = result.getTotalPages();

        makePageList(result.getPageable());
    }
    private void makePageList(Pageable pageable){
        this.page = pageable.getPageNumber() + 1; // 0부터 시작하니 1 추가
        this.size = pageable.getPageSize();
        //마지막 페이지 번호 가져오기 ceil은 반올림 하기때문에 9 -> 10이고 11폐이지는 20이 나온다.
        int tempEnd = (int)(Math.ceil(page / 10.0)) * 10;
        //페이지는 10단위 이기때문에 맨앞은 1, 11, 21 단위로 만들어 줄 수 있다.
        start = tempEnd -9;
        //1~9까지는 이전으로 가는 링크가 안나오도록 한다. 1~9만 false
        prev = start > 1;
        //end는 실제 마지막 페이지와 다시 비교할 필요가 있다. 33 > 40
        end = totalPage > tempEnd ? tempEnd : totalPage;

        next = totalPage > tempEnd;

        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    //        boxed() 메소드는 IntStream 같이 원시 타입에 대한 스트림 지원을 클래스 타입(예: IntStream -> Stream<Integer>)으로 전환하여 전용으로
    //        실행 가능한 (예를 들어 본문과 같이 int 자체로는 Collection에 못 담기 때문에 Integer 클래스로 변환하여 List<Integer> 로 담기 위해 등)
    //        기능을 수행하기 위해 존재합니다.
    }
}



