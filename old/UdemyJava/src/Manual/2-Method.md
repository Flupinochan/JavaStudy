## 後続の処理をスキップするためにreturnする

```java
public static void methodTest(int age) {
    if (age > 20) {
        return; // 後続の処理をスキップするためにreturnする
    }
}
```

## String.format で変数埋め込み文字列の定義

```java
public static void displayHighScorePosition(String playerName, String playerPosition){
    String message = String.format("%sさんのポジションは、%sです", playerName, playerPosition);
    System.out.print(message);
}
```

## 変数名を左に置く(数学的ではないほうが良い)

```java
if (score >= 1000)
```

## オーバーロードの役割

・メソッドの引数だけが異なる。返り値は考慮されない
・2つのメソッドで機能を共有する場合にオーバーロードする
・時間と分の2つの引数から秒を求める場合、分のみから秒を求める場合など用途が同じだけど引数が異なる場合にオーバーロードする
・オーバーロードされるコードで、元のメソッドを利用し、機能を共有することで、元のメソッド修正時に、オーバーロードされたメソッドも修正される

## 三項演算子の使い方

夏の場合は、45、夏でない場合は、35にしたい場合

```java
boolean summer = true;
int tempareture = summer ? 45 : 35;
```