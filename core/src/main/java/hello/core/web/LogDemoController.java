package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
//myLogger 의 scope 이 request 이기 떄문에 그냥 실행 시키면 에러가 발생한다.
//http request 가 없는데 빈으로 등록하려해서 생명주기 범위가 맞지 않는다.
@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
    //ObjectProvider 를 사용해서 빈 생성 요청을 지연 시킨 후 http request 가오면 밑의 메서드를 실행하면서 빈 생성
//    private final ObjectProvider<MyLogger> myLoggerProvider;
    private final MyLogger myLogger;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) throws InterruptedException {
        //getObject 하는 시점에 MyLogger 가 만들어짐
//        MyLogger myLogger = myLoggerProvider.getObject();

        //가짜 프록시 객체는 여기서 진짜 myLogger 를 찾고 생성한다.
        String requestURL = request.getRequestURL().toString();

        System.out.println("myLogger = " + myLogger.getClass());
        myLogger.setRequestURL(requestURL);

        myLogger.log("controller test");
        Thread.sleep(1000);
        logDemoService.logic("testId");

        return "OK";
    }

}
