package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.*;

public class PrototypeTest {

    @Test
    void prototypeBeanFind(){
        //이렇게 new AnnotationConfigApplicationContext(클래스) 를 박으면 @Component 를 붙이지 않아도 ComponentScan 의 대상이 된다.
        //PrototypeBean 이 2번 생성 되면서 34번째 줄의 System.out.println("PrototypeBean.init"); 두번 호출 된다
        //또한 다른 주소값을 가지고 있는 것을 확인 할 수 있다.
        //또한 ac.close 를 해도 destroy 메소드가 호출 되지 않는데 @Scope("protoType")은 빈을 생성하고 연관관계를 맺고 초기화를 하고
        //클라이언트에게 반환을 하고나서 더이상 스프링 컨테이너에서 관리 하지 않기 때문이다.
        //그래서 클라이언트가 직접 종료해줘야한다.(모든 책임을 클라이언트에게 전가한다)
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        System.out.println("find prototypebean1");
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        System.out.println("find prototypeBean2");
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        System.out.println("prototypeBean1 = " + prototypeBean1);
        System.out.println("prototypeBean2 = " + prototypeBean2);
        assertThat(prototypeBean1).isNotSameAs(prototypeBean2);

        //이렇게 직접 destroy 해줘야 한다.
//        prototypeBean1.destroy();
//        prototypeBean2.destroy();
        ac.close();
    }

    @Scope("prototype")
    static class PrototypeBean {
        @PostConstruct
        public void init(){
            System.out.println("PrototypeBean.init");
        }

        @PreDestroy
        public void destroy(){
            System.out.println("PrototypeBean.destroy");
        }
    }
}
