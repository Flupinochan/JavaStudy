public class Text {
    public static void main(String[] args) {
        String longText2 = "one%ntwo%nthree";
        System.out.printf(longText2);
        System.out.println();
        longText2 = """
                Your birthday is
                %d
                です""";

        int age = 35;
        System.out.printf(longText2, age);

        String formatStr = String.format("Your age is %d", age);
        formatStr = "Your age is %d".formatted(age);

        System.out.println();

        // StringBuilderは、変数の再割り当てをしないため、パフォーマンスが良い
        String helloWorld = "Hello World";
        helloWorld = helloWorld.concat("!!!"); // 通常のStringは変数の再割り当てが必要

        StringBuilder helloWorldBuilder = new StringBuilder("Hello World");
        helloWorldBuilder.append("!!!");

        System.out.println(helloWorld);
        System.out.println(helloWorldBuilder);
    }
}
