package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository = new MemoryMemberRepository();

    //할인 정책이 정액에서 정률로 변경 된다면 client인 OrderServiceImpl의 코드가 아래와 같이 바뀌어야 한다.
    //OCP, DIP 같은 객체 지향 설계 원칙을 준수한것 처럼 보이지만 사실은 아니다.
    //OrderServiceImpl은 DiscountPolicy인터페이스 뿐만 아니라 구현 클래스(FixDiscountPolicy or RateDiscountPolicy)에도 의존하고 있다.
    //항상 추상화에 의존해야 하는데 구현체도 의존하기 때문에 DIP 위반이다.
    //정액할인제도 에서 정률할인제도로 변경하는 순간 OrderServiceImpl의 소스코드도 함께 변경되어야 하기 때문에 OCP 위반
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    //그래서 아래와 같이 해야한다.. final 은 반드시 값을 할당해야함
    private DiscountPolicy discountPolicy;


    //단일 책임 원칙을 잘 지킨 경우이다
    //OrderService는 단지 다른 interface를 호출 해서 실행 을 위임하고 오직 결과만 다룬다.
    //할인에 대한건 DiscountPolicy가 다 함
    //회원조회는 MemberRepository가 다 함
    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
