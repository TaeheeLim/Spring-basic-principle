package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

//ctrl + F12로 클래스의 코드를 확인해보면 그대로 생성자가 있는것을 확인할 수 있다
@Component
//@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    //@Autowired private MemberRepository memberRepository 이렇게 필드 주입이 가능하다 without 생성자 주입
    // 그러나 안티 패턴이라 권장되지 않는다
    //final 은 생성자 주입을 하거나 직접 값을 초기화 해줘야한다
    private final MemberRepository memberRepository;

    //할인 정책이 정액에서 정률로 변경 된다면 client 인 OrderServiceImpl 의 코드가 아래와 같이 바뀌어야 한다.
    //OCP, DIP 같은 객체 지향 설계 원칙을 준수한것 처럼 보이지만 사실은 아니다.
    //OrderServiceImpl 은 DiscountPolicy 인터페이스 뿐만 아니라 구현 클래스(FixDiscountPolicy or RateDiscountPolicy)에도 의존하고 있다.
    //항상 추상화에 의존해야 하는데 구현체도 의존하기 때문에 DIP 위반이다.
    //정액할인제도 에서 정률할인제도로 변경하는 순간 OrderServiceImpl 의 소스코드도 함께 변경되어야 하기 때문에 OCP 위반
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    //그래서 아래와 같이 해야한다.. final 은 반드시 값을 할당해야함
    private final DiscountPolicy discountPolicy;

    //밑의 수정자 주입(setter)로도 필드에 주입이 가능한데, @Autowired를 붙여야한다.
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
//    @Autowired
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        this.discountPolicy = discountPolicy;
//    }

    //생성자 주입을 통해서 구현을 하면 DIP 를 지키게 된다. 단지 interface 에만 의존한다. not 구현체
    //생성자가 딱 1개만 있다면 @Autowired 를 생략해도 된다.
    //new OrderServiceImpl(); 스프링도 결국 컨테이너에 빈을 등록하려면 이런식으로 new 해줘야 하는데, 밑의 생성자를 만들어서
    //빈 등록을 한다. 등록하면서 자동주입이 일어난다.
    //그러나 수정자(setter)는 빈을 등록하고 나서 후에 !!!! 빈 연관관계를 만들어준다.

    //생성자 주입은 lombok 의 @RequiredArgsConstructor 로 대체 가능하다.
    //@ComponentScan 이 Component 를 scan 할때 하나의 인터페이스 안에 여러개의 구현체에 @Component 가 있고
    //생성자 주입을 할때 인터페이스로 생성자 주입흘 하면 오류가난다.
    //왜냐하면 조회 대상 빈이 여러개 이기 때문이다.
    //이럴떄는 @Autowired 가 여러개의 조회 대상빈이 있으면 필드 명을 매칭 하기 때문에 DiscountPolicy rateDiscountPolicy(필드명 or 파라미터명)
    //이런식으로 이름을 바꿔주면 해당 component 를 스캔 할 수 있다.
    //@Qualifier("mainDiscountPolicy") DiscountPolicy discountPolicy 이렇게 scan 할 수도 있다.
    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    //단일 책임 원칙을 잘 지킨 경우이다
    //OrderService 는 단지 다른 interface 를 호출 해서 실행 을 위임하고 오직 결과만 다룬다.
    //할인에 대한건 DiscountPolicy 가 다 함
    //회원조회는 MemberRepository 가 다 함
    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    //테스트 용도(MemberServiceImpl 의 필드인 memberRepository 와 같은지 학인하기 위함)
    public MemberRepository getMemberRepository(){
        return memberRepository;
    }
}
