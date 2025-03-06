# データ型

## シングルクォートは、1文字のみに使用可能

```java
char oneChar = 'A';
String myString = "Hello";
```

## ,カンマの代わりに、_アンダースコアで数値を区切ることが可能

```java
int numTest = 1_000;
```

## 数値はデフォルトでint, doubleで、long, shortにしたい場合はL, Fをつける

※memoryは多いが、処理速度が早く、小数点では精度が高い方が良い場合が多いため、doubleが推奨される

```java
long longValue = 100L;
```

## 正確に計算したい場合は、floatで計算すると良い、intだと切り捨てられる

```java
int intCalc = 5 / 2; // 2
float floatCalc = 5F / 2F; // 2.5
```

## booleanは、小文字のtrueとfalseのみ使用可能

```java
boolean myBoolean = true;
```

## String(immutable) vs StringBuilder(mutable)

```java
StringBuilder sb = new StringBuilder("Hello");
sb.insert(5, " Beautiful");
System.out.println(sb.toString()); // 既存の文字列を変更し、Hello Beautiful を出力
```

## 文字列比較は、== ではなく、.equals を使用する

```java
String myName = "metalmental";
if (myName.equals("metalmental")) {
    System.out.print("My name is " + myName);
}
```