package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        //배척할 클래스를 지정해주는 어노테이션인데, AppConfig, TestConfig 도 @Configuration 이 붙어있어서 ComponentScan 에 걸린다.
        //그래서 충돌을 방지하기 위해서 AppConfig 를 제외시킴
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class),
        //member 만 ComponentScan 의 대상이된다.
        //여러개 둘수도있다. {"hello.core.member", "hello.core.order"}
        //지정하지 않으면 @ComponentScan 이 붙은 설정 정보 클래스의 패키지가 시작 위치가 된다.
        //권장하는 방법은 패키지 위치를 지정하지 않고, 설정 정보 클래스의 위치를 프로젝트 최상단에 두는 것이다.
        //최근 스프링 부트도 이 방법을 기본으로 제공한다.
        //basePackages 는 생략한다...(관례)
        basePackages = "hello.core.member"
)
public class AutoAppConfig {

}
