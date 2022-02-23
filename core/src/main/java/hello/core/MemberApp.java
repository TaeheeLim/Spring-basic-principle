package hello.core;

import hello.core.member.*;

public class MemberApp {
    //순수 자바로 개발 .. 하지만 매번 테스트 마다 이렇게 psvm 만들어서 테스트하는것은 좋지 않고 한계가있음 그래서 junit을 사용함
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        //AppConfig를 통해서 주입을 하는데, memberService 인터페이스 안에는 memberServiceImpl 이 담기고 Impl 안에는
        //MemoryMemberRepository(구현체) 가 담긴다.
        MemberService memberService = appConfig.memberService();
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("new member = " + member.getName());
        System.out.println("findMember = " + findMember.getName());
    }
}
