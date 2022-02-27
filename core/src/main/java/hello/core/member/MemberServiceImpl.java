package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//구현체에 @Component 를 붙이면 스프링 컨테이너에 스프링 빈으로 등록이 된다.
//그런데 밑의 생성자 주입도 해줘야하는데 이것은 @Autowired 어노테이션을 통해서 자동으로 생성사주입을 시켜준다.
//@Component("memberService2")이런 식으로 빈 이름을 지정할 수도 있다
//빈 이름 default 는 앞에 글자만 소문자로 바뀐다.
@Component
public class MemberServiceImpl implements MemberService{
    //인터페이스가 특정 구현체에 의존한다.
    //실제 할당하는 부분이 구현체를 의존
    //그래서 MemberRepository와 MemoryMemberRepository도 의존한다.
    //즉 DIP를 위반하고있다
    private final MemberRepository memberRepository;
    //이렇게 바꾸면 이제는 추상에만 의존하고 생성자 주입을 통해서 역할을 구현한다.

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    //테스트 용도
    public MemberRepository getMemberRepository(){
        return memberRepository;
    }
}
