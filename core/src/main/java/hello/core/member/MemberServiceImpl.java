package hello.core.member;

public class MemberServiceImpl implements MemberService{
    //인터페이스가 특정 구현체에 의존한다.
    //실제 할당하는 부분이 구현체를 의존
    //그래서 MemberRepository와 MemoryMemberRepository도 의존한다.
    //즉 DIP를 위반하고있다
    private final MemberRepository memberRepository = new MemoryMemberRepository();

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
