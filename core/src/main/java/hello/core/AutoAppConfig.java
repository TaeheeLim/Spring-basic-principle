package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        //배척할 클래스를 지정해주는 어노테이션인데, AppConfig, TestConfig 도 @Configuration 이 붙어있어서 ComponentScan 에 걸린다.
        //그래서 충돌을 방지하기 위해서 AppConfig 를 제외시킴
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
        //member 만 ComponentScan 의 대상이된다.
        //여러개 둘수도있다. {"hello.core.member", "hello.core.order"}
        //지정하지 않으면 @ComponentScan 이 붙은 설정 정보 클래스의 패키지가 시작 위치가 된다.
        //권장하는 방법은 패키지 위치를 지정하지 않고, 설정 정보 클래스의 위치를 프로젝트 최상단에 두는 것이다.
        //최근 스프링 부트도 이 방법을 기본으로 제공한다.
        //basePackages 는 생략한다...(관례)
//        basePackages = "hello.core.member"
)
public class AutoAppConfig {
    //스프링 빈을 등록 할떄 @Component 로 자동 등록, 그리고 @Bean 으로 수동 등록을 할 떄 같은 빈 이름을 가질 경우
    //자동 보다는 수동 등록이 우선권을 가지면서 수동 빈이 자동 빈을 오버라이딩 해버린다.
    //현실 세계에서는 이런 경우는 여러 설정들이 꼬여서 이런 결과가 만들어지는 경우가 대부분이다.. 즉 의도하지 않았다. 조심!!
    //굉장히 잡기 어려운 버그가 만들어진다.
//    @Bean(name = "memoryMemberRepository")
//    MemberRepository memberRepository(){
//        return new MemoryMemberRepository();
//    }
}
