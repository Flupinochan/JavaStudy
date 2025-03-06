package net.metalmental.springcore.config;

import net.metalmental.springcore.common.SwimCoach;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Componentをまとめて定義するクラス
@Configuration
public class SpringConfig {

    // @Componentと同じで、メソッドで@Componentを定義するようなもの
    // 外部ライブラリをDIしたい(クラスとして定義できない)場合で使用 (S3 Clientとか)
    // beanidは@Qualifierで指定できるカスタム名
    @Bean("beanid")
    public SwimCoach swimCoach(){
        return new SwimCoach();
    }
}
