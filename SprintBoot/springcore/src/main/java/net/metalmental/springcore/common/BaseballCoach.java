package net.metalmental.springcore.common;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import net.metalmental.util.Coach;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

// シングルトンは1つのインスタンスを使いまわす方法
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class BaseballCoach implements Coach {

    public BaseballCoach(){
        System.out.println("BaseballCoach");
    }

    @Override
    public String getDailyWorkout() {
        return "I am Baseball Coach";
    }

    @PostConstruct
    public void initProcess(){
        System.out.println(getClass().getSimpleName() + "インスタンスが作成されました");
    }

    @PreDestroy
    public void destroyProcess(){
        System.out.println(getClass().getSimpleName() + "インスタンスが破壊されます");
    }
}
