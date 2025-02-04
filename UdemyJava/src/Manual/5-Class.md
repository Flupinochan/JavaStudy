public class Account {

    private String number;
    private double balance;
    private String customerName;
    private String customerEmail;
    private String customerPhone;

    public Account(){
        System.out.println("Constructor called");
    }

    public Account(String number, double balance, String customerName, String customerEmail, String customerPhone){
        this(); // Constructorの呼び出しが可能 ※最初に呼び出す必要がある
        this.number = number; // ConstructorではSetterや他のメソッドは呼び出さない
        this.balance = balance;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
    }


getterだけをもち、データを格納するだけのクラス = recordで定義すると良い
データはConstructorでインスタンス化する時に引数で指定する
setterはなく、不変