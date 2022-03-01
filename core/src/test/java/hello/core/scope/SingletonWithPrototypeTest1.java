package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.Assertions.*;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFid(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);

    }

    @Test
    void singletonClientUsePrototype(){
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);
        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);
        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);
    }

    //scope 가 prototype 인 클래스를 갖고 있는 싱글톤 클래스를 생성하면 싱글톤을 빈으로 등록하는 시점에 프로토타입이 생성된다.
    //그렇게 되면 싱글톤은 항상 같은 프로토타입을 가지고 있는 이는 우리가 원하는 것이 아니다.
    //프로토타입은 클라이언트가 요청 할 떄마다 새로운 인스턴스를 생성해서 반환해야 하기 떄문이다.
    //이것을 해결하기 위한 방법으로 생성자 주입을 하는 것이 아니라, @Autowired private Application ac 필드를 만들고,
    //logic 메서드 안에 PrototypeBean prototypeBean = ac.getBean(PrototypeBean.class);
    //이렇게 하면 해결이 되지만 좋은 방법이 아니다.
    @Scope("singleton")
    static class ClientBean {
//        private final PrototypeBean prototypeBean; //생성시점에 주입

        //좋은 해결 방법 : ObjectProvider 사용, provider.getObject();
//        @Autowired
//        private ObjectProvider<PrototypeBean> provider;
//        private Provider
        //라이브러리를 사용한 경우 provider.get();
        @Autowired
        private Provider<PrototypeBean> provider;

//        @Autowired
//        public ClientBean(PrototypeBean prototypeBean){
//            this.prototypeBean = prototypeBean;
//        }

        public int logic(){
            //항상 새로운 프로토타입이 생성된다.
            //DL(Dependency Lookup)의 기능으로 직접 조회하는것이 아니라 대신 조회해서 해당 빈을 찾아 반환해준다.
//            PrototypeBean prototypeBean = provider.getObject();
            PrototypeBean prototypeBean = provider.get();
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        public void addCount(){
            count++;
        }

        public int getCount(){
            return this.count;
        }

        @PostConstruct
        public void init(){
            System.out.println("PrototypeBean.init " + this);
        }

        @PreDestroy
        public void destroy(){
            System.out.println("PrototypeBean.destroy");
        }
    }

}
