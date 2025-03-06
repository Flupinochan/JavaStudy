## for文の条件は複数が可能

```java
int count = 5;
for (int i = 1; (i <= 10) && (i <= count); i++){
    System.out.println(i + count);
        }
```