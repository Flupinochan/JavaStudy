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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public void depositFunds(double depositAmount){
        this.balance += depositAmount;
        System.out.println(("Deposit: " + depositAmount + ", Remaining Balance: " + this.balance));
    }

    public void withdrawFunds(double withdrawalAmount){
        if(balance - withdrawalAmount < 0){
            System.out.println(("[ERROR] Balance is less than 0"));
        }else{
            balance -= withdrawalAmount;
            System.out.println("Withdraw: " + withdrawalAmount + ", Remaining Balance: " + this.balance);
        }
    }
}
