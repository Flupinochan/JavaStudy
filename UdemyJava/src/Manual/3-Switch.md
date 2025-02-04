## 新しいSwitch文の形式(breakの省略)

```java
switch (switchValue) {
    case 1 -> System.out.println("Value was 1");
    case 2, 3 -> {
        System.out.println("Value was 2");
        System.out.println("Value was 3");
    }
    default -> System.out.println("Was not 1 or 2");
}
```