import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

record GroceryItem(String name, String type, int count){
    public GroceryItem(String name){
        this(name, "DAIRY", 1);
    }
}

// ArrayListはプリミティブ型(intやlongなど)はサポートしていない
public class ArrayListTest{
    public static void main(String[] args) {
        ArrayList<Integer> arrayList2 = new ArrayList<Integer>();
        ArrayList<Integer> arrayList3 = new ArrayList<>(); // 右側は省略可能
//        ArrayList<> arrayList4 = new ArrayList<>(); // 型指定しないことも可能

        ArrayList<GroceryItem> arrayList = new ArrayList<>();
        arrayList.add(new GroceryItem("Butter"));
        arrayList.add(new GroceryItem("milk"));
        arrayList.add(1, new GroceryItem("oranges"));
        arrayList.add(new GroceryItem("banana"));
        arrayList.remove(3);
        System.out.println(arrayList);

        List<String> list = new ArrayList<>();// ArrayListよりListの方が便利 ※右側はArrayListになる
        list.add("apples");
        list.add("bananas");
        list.add("oranges");
        System.out.println(list.get(1));
        if (list.contains("apples")){
            System.out.println("applesを含んでいます");
        }else{
            list.add("apples");
            list.remove("apples");
        }
        list.addAll(List.of("aaa", "bbb", "ccc")); // addAllで一括追加
        list.sort(Comparator.naturalOrder()); // sort
        System.out.println(list);
        list.removeAll(List.of("aaa", "bbb", "ccc"));
        System.out.println(list);
        System.out.println(list.size()); // length = size()

        String[] days = new String[] {"Sunday", "Monday", "Tuesday"};
        List<String> daysList1 = Arrays.asList(days); // 可変で追加や削除が可能
        List<String> daysList2 = List.of(days); // 不変で追加や削除が不可能
    }
}
