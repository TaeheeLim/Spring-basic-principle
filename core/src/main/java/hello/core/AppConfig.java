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
    @Bean
    public MemberService memberService(){
        return new MemberServiceImpl(memberRepository());
    }

    //나중에 JDBCRepository로 바꾸려면 여기만 갈아끼우면 된다.
    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService(){
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy(){
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }

}
