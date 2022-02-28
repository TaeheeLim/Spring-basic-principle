package hello.core.discount;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
//특별한 이름을 부여 할 수있다 by using @Qualifier in case there are two or more beans implemented by single interface
//@Qualifier("mainDiscountPolicy")
//@Primary 는 컴포넌트 스캔에 우선순위를 부여할수 있다. Qualifier 보다 자주 쓰임 why ? Qualifier 는 지저분 함
//@Primary
//이렇게 Qualifier 를 어노테이션을 만들어서 쓰면 컴파일 타임에 에러를 확인 할 수있다 ex)오타:MainnDiscountPolicy
//그러나 @Qualifier("mainDiscountPolicy") 이렇게 하면 에러를 찾기 힘들다
@MainDiscountPolicy
public class RateDiscountPolicy implements DiscountPolicy{

    private int discountPercent = 10;

    @Override
    public int discount(Member member, int price) {
        if(member.getGrade() == Grade.VIP){
            return price * discountPercent / 100;
        } else {
            return 0;
        }
    }
}
