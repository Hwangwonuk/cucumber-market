package com.cucumber.market.dto.member;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

/*
* 1. 문제
     redis에 객체를 저장하려고 할때 "DefaultSerializer requires a Serializable payload but received an object of type" 에러가 발생한다.
  2. 원인
     redis는 data를 hash해 저장하기 때문에, redis에 저장할 객체는 serializable를 implements 해야한다.
  3. 해결
     redis에 저장할 object가 Serializable을 implements 하도록 변경한다.
  4. 직렬화를 주로 어디에 사용하는가?
     JVM의 메모리에만 상주되어 있는 객체 데이터를 그대로 영속화(persist)가 필요할 때 사용된다.
     시스템이 종료되더라도 없어지지 않는 장점을 가지며 영속화된 데이터이기 때문에 네트워크로 전송도 가능하다.
     그리고 필요할 때 직렬화 된 객체 데이터를 가져와서 역직렬화하여 객체를 바로 사용할 수 있게 된다.

     서블릿 세션들은 대부분 세션의 Java 직렬화를 지원하고 있다.
     단순히 세션을 서블릿 메모리 위에서 운용한다면 직렬화가 필요없지만, 파일로 저장하거나 세션 클러스터링, DB를 저장하는 옵션등을 선택하게 되면 세션 자체가 직렬화되어 저장되어 전달된다.

     그리고 캐시에서도 사용된다. 주로 DB를 조회한 후 가져온 데이터 객체 같은 경우 실시간 형태로 요구하는 데이터가 아니라면
     메모리, 외부 저장소, 파일 등을 저장소를 이용해서 데이터 객체를 저장한 후 동일한 요청이 오면 DB를 다시 요청하는 것이 아니라
     저장된 객체를 찾아서 응답하게 하는 형태를 캐시를 사용한다라고 말한다.
     캐시를 이용하면 DB 리소스를 절약할 수 있기 때문에 많은 시스템에서 자주 활용된다.
     이렇게 캐시할 부분을 자바 직렬화된 데이터를 저장해서 사용한한다.
*/
@Getter
@Builder
public class CurrentMemberInfo implements Serializable {
    private final String member_id;
    private final String isAdmin;
}
