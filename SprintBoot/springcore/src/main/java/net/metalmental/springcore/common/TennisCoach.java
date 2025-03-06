package net.metalmental.springcore.common;

import net.metalmental.util.Coach;
import org.springframework.stereotype.Component;

@Component
public class TennisCoach implements Coach {

    public TennisCoach(){
        System.out.println("TennisCoach");
    }

    @Override
    public String getDailyWorkout() {
        return "Tennis";
    }
}
