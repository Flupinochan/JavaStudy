package net.metalmental.util;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

// Dependancy Injection 自動インスタンス生成
// PrimaryはデフォルトでDIされるクラス
// デフォルトでは全てがインスタンス化されるが、Lazyなどの設定をするとWebアクセスされて、実際に使用されるタイミングでインスタンス化される
// 初期起動が早くなる
@Component
@Primary
@Lazy
public class CricketCoach implements Coach{

    public CricketCoach(){
        System.out.println("CricketCoath");
    }
    @Override
    public String getDailyWorkout() {
        return "Practice";
    }
}
