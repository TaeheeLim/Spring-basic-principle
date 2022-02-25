package hello.core.singleton;

public class StatefulService {
// 같은 참조값을 가지지기 떄문에 ...
//    private int price; //상태를 유지하는 필드 10000 -> 20000 원으로 바꿔치기 됨

    public int order(String name, int price){
        System.out.println("name = " + name + " price = " + price);
//        this.price = price; //여기가 문제!
        //그래서 이렇게 파라미터로 or 지역변수로 관리
        return price;
    }

//    public int getPrice(){
//        return this.price;
//    }
}
