package net.metalmental.springcore.common;

import net.metalmental.util.Coach;

public class SwimCoach implements Coach {

    public SwimCoach(){
        System.out.println("クラス名: " + getClass().getSimpleName());
    }

    @Override
    public String getDailyWorkout() {
        return "SwimCoachです";
    }
}
