package hello.core;

import hello.core.member.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
    //순수 자바로 개발 .. 하지만 매번 테스트 마다 이렇게 psvm 만들어서 테스트하는것은 좋지 않고 한계가있음 그래서 junit을 사용함
    public static void main(String[] args) {
//        AppConfig appConfig = new AppConfig();
        //AppConfig 를 통해서 주입을 하는데, memberService 인터페이스 안에는 memberServiceImpl 이 담기고 Impl 안에는
        //MemoryMemberRepository(구현체) 가 담긴다.
//        MemberService memberService = appConfig.memberService();

        //ApplicationContext 가 스프링 컨테이너라고 생각하면된다.
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        //스프링 컨테이너에 등록된 빈 이름(메스도명) 과 반환타입을 넣어준다.
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);

        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("new member = " + member.getName());
        System.out.println("findMember = " + findMember.getName());
    }
}
