package hello.core.singleton;

//싱글톤은 하나의 객체만 생성한다는것을 보장해주지만 많은 단점을 갖고있다.
//우선 7 ~ 15라인의 코드는 무조건 작성해야한다.
//클라이언트가 구체 클래스에 의존한다 -> DIP 위반 구체클래스.getInstance();
//클라이언트가 구체 클래스에 의존해서 OCP 원칙을 위반할 가능성이 높다.
//테스트하기 어렵다
//내부 속성을 변경하거나 초기화 하기 어렵다.
//private 생성자로 자식 클래스를 만들기 어렵다.
//결론적으로 유연성이 떨어진다.
//안티패턴으로 불리기도 한다.
//그래서 싱글톤 컨테이너(스프링 컨테이너)를 사용한다.
public class SingletonService {

    private static final SingletonService instance = new SingletonService();

    public static SingletonService getInstance(){
        return instance;
    }

    private SingletonService(){

    }

    public void logic(){
        System.out.println("싱글톤 객체 로직 호출");
    }
}