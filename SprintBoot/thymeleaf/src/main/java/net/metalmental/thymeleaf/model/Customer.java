package net.metalmental.thymeleaf.model;

import jakarta.validation.constraints.*;
import lombok.Data;

// @Dataは、Getter/Setter/toString等を自動生成するLombokライブラリ
// IntelliJではLombokプラグインを入れておくこと
@Data
public class Customer {
    private String firstName;

    // NotNullは参照型のStringのみ有効
    @NotNull()
    @Size(min=1, message="is two required") // messageはmessages.properties出も設定可能
    private String lastName;

    @NotNull(message="is required")
    @Min(value=0, message="must be greater then or equal to zero")
    @Max(value=10, message="must be less than or equal to zero")
    private Integer freePasses; // intではなくIntegerにすることで@NotNullが利用可能

    // 正規表現のバリデーション
    @Pattern(regexp = "^[a-zA-z0-9]{5}", message = "only 5 chars or digits")
    private String postalCode;
}
