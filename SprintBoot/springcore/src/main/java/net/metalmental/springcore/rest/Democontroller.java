package net.metalmental.springcore.rest;

import net.metalmental.util.Coach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import net.metalmental.springcore.common.TennisCoach;

@RestController
public class Democontroller {

    // 直接fieldにDIすることも可能だが、推奨されない
    // コンストラクタを利用しないことで、単体テストができない、ハードコーディングしているのと同じ
//    @Autowired
    private Coach myCoach;
    private Coach anotherCoach;

    // インスタンスを自動生成 ※Interface型を注入するため、クラスは必ずInterfaceで実装する
    // Interface型を利用することで別のMock用のクラスやV2クラスなど他のクラスもそのまま利用可能で柔軟性が高い
    // どのクラスかは、@Qualifierで指定 ※最初は小文字にする
    @Autowired
    public Democontroller(@Qualifier("beanid") Coach theCoach,
                          @Qualifier("beanid") Coach theAnotherCoach){
        myCoach = theCoach;
        anotherCoach = theAnotherCoach;
    }

    // コンストラクタでなくても普通のメソッドでもDIは可能だが、推奨されない
//    @Autowired
//    public void doSomeMethod(Coach theCoach){
//        myCoach = theCoach;
//    }
    @GetMapping("/check-instance")
    public String scopeTest(){
        return "同じインスタンス(bean)? " + (myCoach == anotherCoach);
    }

    @GetMapping("/daily-workout")
    public String getDailyWorkout(){
        return myCoach.getDailyWorkout();

    }
}
