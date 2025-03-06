import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.example.BankAccount;

class BankAccountTestParameterized {

    private BankAccount account;

    @BeforeEach
    void setup() {
        this.account = new BankAccount(1000, "Tetsuro", "Kawagoe", BankAccount.CHECKING);
        System.out.println("Running a test...");
    }

    @ParameterizedTest
    @CsvSource({
        "200.00, true, 1200.00",
        "325.14, true, 1325.14",
        "489.33, true, 1489.33",
        "1000.00, true, 2000.00"
    })
    void deposit(double amount, boolean branch, double expected) {
        account.deposit(amount, branch);
        assertEquals(expected, account.getBalance(), 0.01);
    }
}