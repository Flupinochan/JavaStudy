package net.metalmental.app.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Value("${my.name}")
    private String myName;

    @Value("${study}")
    private String study;

    @GetMapping("/")
    public String sayHello(){
        return "Hello World!";
    }

    @GetMapping("/hot-reload")
    public String getDailyWorkout(){
        return "Hot reload";
    }

    @GetMapping("/value-info")
    public String getValue(){
        return myName + " " + study;
    }
}

// @Componentなどをクラスにつけると、アプリ起動時に自動的にインスタンス化される
// シングルトンであり、インスタンスは1つだけ
// @Lazyで使用されるまで作成されない(遅延ロード)
// @Scope("prototype")で必要になるたびに都度再作成される

// @Componentで自動作成されたインスタンスを使いたい場合(DIしたい場合)は、使いたいクラスやコントローラに@Autowiredをつける