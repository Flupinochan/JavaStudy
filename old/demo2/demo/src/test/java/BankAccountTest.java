// import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.BankAccount;

public class BankAccountTest {
    private BankAccount account;
    
    @BeforeAll
    public static void beforeOnce(){
        System.out.println("BeforeAll Test Setting");
    }
    
    @BeforeEach // BeforeEach: メソッドごとに初期化する共通設定、BeforeAll: クラスごとに1度だけ初期化する ※Afterもある
    public void setup(){
        this.account = new BankAccount(1000, "Tetsuro", "Kawagoe", BankAccount.CHECKING);
        System.out.println("Initialize BankAccount...");
    }

    @Test
    public void deposit() throws Exception{
        double balance = this.account.deposit(200.00, true);
        assertEquals(1200.00, balance, 0);
    }

    @Test
    public void withdraw_notBranch() throws Exception{
        assertThrows(IllegalArgumentException.class, () -> {
            // 例外がスローされるコードを実行(例外のテスト)
            account.withdraw(600.00, false);
        });
    }

    @Test
    public void getBalance_deposit() throws Exception{
        account.deposit(200.00, true);
        double balance = this.account.getBalance();
        assertEquals(1200.00, balance, 0);
    }

    @Test
    public void getBalance_withdraw() throws Exception{
        account.withdraw(200.00, true);
        double balance = this.account.getBalance();
        assertEquals(800.00, balance, 0);
    }

    @Test
    public void isChecking_true(){
        assertTrue(this.account.isChecking(), "[FAIL] The account is NOT a CHECKING account!!"); // 第2引数にエラー時のメッセージを記載可能
    }

}