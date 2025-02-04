import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class IteratorTest {
    public static void main(String[] args) {
        List<String> textList = new ArrayList<>();
        textList.add("apple");
        textList.add("banana");
        textList.add("melon");

        var iterator = textList.iterator();
        // iteratorはpaginatorやdatabaseのcursorと同じ
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }

        // イテレータは前後移動やループ開始地点などを柔軟に操作できる

        System.out.println("");

        LinkedList<String> textList2 = new LinkedList<>(); // LinkedListはPreviousで逆順にループ可能
        textList2.add("apple");
        textList2.add("banana");
        textList2.add("melon");
        var listIterator = textList2.listIterator(textList2.size()); // Iteratorの開始地点は最後のElementを指定
        while (listIterator.hasPrevious()){
            System.out.println(listIterator.previous());
        }
    }
}
