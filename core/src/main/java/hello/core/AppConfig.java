package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//사용영역과 구성 영역이 분리 되었다 through AppconFig.. Appconfig는 구성을 담당한다.
//쉽게 말하면 배우를 교체해준다.
//AppConfig가 DI 컨테이너이다.
@Configuration
public class AppConfig {
    //역할과 구현 클래스가 한눈에 보인다. 애플리케이션 전체구성이 어떻게 이루어져있는지 한눈에 파악할수 있다.

    //@Bean memberService -> new MemoryMemberRepository()
    //@Bean orderService -> new MemoryMemberRepository()
    //@Bean 으로 설정된 것들은 스프링 컨테이너에 스프링 빈으로 등록되는데, 어쨌든 new 연산자를 통해서 인스턴스를 생성하는
    //자바 코드 이기 때문에 memberService, orderService 를 스프링 빈으로 등록 할 때 MemoryMemberRepository 인스턴스가
    //두번 생성된다는 의문을 가질수 있다.
    //이럴떄 테스트 코드를 돌려봐야한다. 의문이 생기면 ..

    //아래의 5번 처럼 호출될 것처럼 보인다..
    //call AppConfig.memberService
    //call AppConfig.memberRepository
    //call AppConfig.memberRepository
    //call AppConfig.orderService
    //call AppConfig.memberRepository

    //하지만 이렇게 3번만 호출된다
    //즉 어떠한 벙법을 써서라도 스프링 컨테이너는 싱글톤을 보장해준다.
    // call AppConfig.memberService
    // call AppConfig.memberRepository
    // call AppConfig.orderService
    @Bean
    public MemberService memberService(){
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    //나중에 JDBCRepository로 바꾸려면 여기만 갈아끼우면 된다.
    @Bean
    public MemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService(){
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
//        return null;
    }

    @Bean
    public DiscountPolicy discountPolicy(){
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }

}
