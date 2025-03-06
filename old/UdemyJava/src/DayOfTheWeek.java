public enum DayOfTheWeek {
    SUN, MON, TUES, WED, THURS, FRI, SAT;

    public String getDate(){
        return switch (this){
            case SUN -> "Sunday";
            case MON -> "Monday";
            default -> "AnyDay";
        };
    }
}
