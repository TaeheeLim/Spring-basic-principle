package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

//public class NetworkClient implements InitializingBean, DisposableBean
public class NetworkClient {

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + this.url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    //서비스 시작시 호출
    public void connect() {
        System.out.println("connect : " + url);
    }

    public void call(String message) {
        System.out.println("call: " + url + " message = " + message);
    }

    //서비스 종료시 호출
    public void disconnect() {
        System.out.println("close : " + url);
    }

    //InitializingBean 을 구현 하면 아래의 메소드를 implement 해야 한다.
    //메소드명 그대로 의존관계주입이 끝나면 호출해주겠다라는 의미이다.
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        System.out.println("NetworkClient.afterPropertiesSet");
//        connect();
//        call("초기화 연결 메시지");
//    }

    //이름 그대로 빈 소멸 직전에 호출
//    @Override
//    public void destroy() throws Exception {
//        System.out.println("NetworkClient.destroy");
//        disconnect();
//    }

//    public void init(){
//        System.out.println("NetworkClient.init");
//        connect();
//        call("초기화 연결 메시지");
//    }
//
//    public void close(){
//        System.out.println("NetworkClient.close");
//        disconnect();
//    }

    //최신 스프링에서 가장 권장하는 방법이다.
    //애노테이션 하나만 붙이면 되기 떄문에 편리하다
    //패키지를 잘 보면 `java.annotation.PostConstruct`이다. 스프링에 종속적인 기술이 아니라 JSR-250라는 자바 표준이다.
    //따라서 스프링이 아닌 다른 컨테이너에서도 동작한다.
    //컴포넌트 스캔과 잘 어울린다.
    //유일한 단점은 외부 라이브러리에는 적용하지 못한다. 외부 라이브러리를 초기화, 종료하려면 메서드 이름을 지정해주는 방식으로 해야한다.
    @PostConstruct
    public void init(){
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메시지");
    }

    @PreDestroy
    public void close(){
        System.out.println("NetworkClient.close");
        disconnect();
    }
}
