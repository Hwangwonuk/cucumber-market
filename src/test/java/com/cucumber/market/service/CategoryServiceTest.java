package com.cucumber.market.service;

/*
 JUnit : 자바와 JVM 계열의 언어(예 : 코틀린)에서 사용하는 단위 테스트 프레임워크를 말한다.

 단위 테스트(Unit Test) : 소스코드의 특정 모듈(프로그램 내 하나의 기능을 부르는 말)이 의도된 대로 정확히 작동하는지 검증하는 절차이며,
 함수, 메서드, 개별 코드 같은 작은 단위에 대해 테스트 케이스(Test Case)로 분리하고 테스트 코드를 작성하여 테스트하는 것을 말한다.
 외부 API와의 연동이 필수라든가 DB 데이터 접근 등 외부 리소스를 직접 사용해야 하는 테스트라면 단위 테스트가 아니다.
 단위 테스트에서 외부와의 연동이 필요하다면 테스트 대역(Test Double)을 사용하면 된다.

 Mockito : 단위 테스트를 위한 자바 Mocking 프레임워크 중 하나이다.

 테스트 대역(Test Double)의 종류 중 모의(Mock) 객체를 필요로 할 때 사용한다.

 테스트 대역(Test Double) : 테스트하려는 객체와 연관된 객체를 사용하기가 어렵고 모호할 때 대신해 줄 수 있는 객체를 말한다.

 모의(Mock) 객체 : 호출했을 때 사전에 정의된 명세대로의 결과를 돌려주도록 미리 프로그램 돼있는 것이다.
 예상치 못한 호출이 있을 경우 예외를 던질 수 있으며 모든 호출이 예상된 것이었는지 확인할 수 있다.
 */

import com.cucumber.market.dto.category.*;
import com.cucumber.market.exception.NotExistCategoryNameException;
import com.cucumber.market.mapper.CategoryMapper;
import com.cucumber.market.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

// @ExtendWith : JUnit에서 해당 테스트 클래스에 공통으로 적용할 기능이 있을 때 사용하는 것
@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryServiceImpl;

    private BigCategoryNameRequest bigCategoryNameRequest;

    @BeforeEach
    void init() {
        // given
        bigCategoryNameRequest = BigCategoryNameRequest.builder()
                .bigCategoryName("대분류등록")
                .build();
    }

    @Test
    @DisplayName("Request에 대분류가 입력된 경우 대분류 등록 성공")
    void successRegisterBigCategory() {
        // when + then
        Assertions.assertDoesNotThrow(() -> categoryServiceImpl.registerBigCategory(bigCategoryNameRequest));

        // verify문 설명
        // categoryServiceImpl.registerBigCategory() 메서드 내부적으로
        // categoryMapper.registerBigCategory() 메서드를 호출했는지 검증
        verify(categoryMapper).registerBigCategory(bigCategoryNameRequest.getBigCategoryName());
    }

    @Test
    @DisplayName("Request에 입력된 대분류가 이미 중복된 이름일 경우 대분류 등록 실패")
    void throwExceptionCheckDuplicateBigCategoryName() {
        // given
        when(categoryMapper.checkDuplicateBigCategoryName(bigCategoryNameRequest.getBigCategoryName())).thenReturn(1);

        // when + then
        Assertions.assertThrows(DataIntegrityViolationException.class,
                () -> {
                    categoryServiceImpl.checkDuplicateBigCategoryName(bigCategoryNameRequest.getBigCategoryName());
                }
        );

        // verify문 설명
        // categoryServiceImpl.checkDuplicateBigCategoryName() 메서드 내부적으로
        // categoryMapper.checkDuplicateBigCategoryName() 메서드를 호출했는지 검증
        verify(categoryMapper).checkDuplicateBigCategoryName(bigCategoryNameRequest.getBigCategoryName());
    }

    @Test
    @DisplayName("Request에 입력된 대분류가 존재할 경우 조회 가능")
    void successCheckExistBigCategoryName() {
        // given
        when(categoryMapper.checkDuplicateBigCategoryName(bigCategoryNameRequest.getBigCategoryName())).thenReturn(1);

        // when + then
        categoryServiceImpl.checkExistBigCategoryName(bigCategoryNameRequest.getBigCategoryName());

        // verify문 설명
        // categoryServiceImpl.checkExistBigCategoryName() 메서드 내부적으로
        // categoryMapper.checkDuplicateBigCategoryName() 메서드를 호출했는지 검증
        verify(categoryMapper).checkDuplicateBigCategoryName(bigCategoryNameRequest.getBigCategoryName());
    }

    @Test
    @DisplayName("Request에 입력된 대분류가 존재하지 않을 경우 대분류 등록 실패")
    void throwExceptionCheckExistBigCategoryName() {
        // given
        when(categoryMapper.checkDuplicateBigCategoryName(bigCategoryNameRequest.getBigCategoryName())).thenReturn(0);

        // when + then
        Assertions.assertThrows(NotExistCategoryNameException.class,
                () -> {
                    categoryServiceImpl.checkExistBigCategoryName(bigCategoryNameRequest.getBigCategoryName());
                }
        );

        // verify문 설명
        // categoryServiceImpl.checkExistBigCategoryName() 메서드 내부적으로
        // categoryMapper.checkDuplicateBigCategoryName() 메서드를 호출했는지 검증
        verify(categoryMapper).checkDuplicateBigCategoryName(bigCategoryNameRequest.getBigCategoryName());
    }

    @Test
    @DisplayName("저장된 모든 대분류를 조회하는데에 성공")
    void successGetBigCategoryNames() {
        // given
        List<BigCategoryNamesResponse> bigCategoryNames = new ArrayList<>();
        bigCategoryNames.add(BigCategoryNamesResponse.builder()
                .bigCategoryName("대분류등록")
                .build());
        when(categoryMapper.getBigCategoryNames()).thenReturn(bigCategoryNames);

        // when
        List<BigCategoryNamesResponse> actualBigCategoryNames = categoryServiceImpl.getBigCategoryNames();

        // then
        Assertions.assertEquals(bigCategoryNames, actualBigCategoryNames);

        // verify문 설명
        // categoryServiceImpl.getBigCategoryNames() 메서드 내부적으로
        // categoryMapper.getBigCategoryNames() 메서드를 호출했는지 검증
        verify(categoryMapper).getBigCategoryNames();
    }


}
