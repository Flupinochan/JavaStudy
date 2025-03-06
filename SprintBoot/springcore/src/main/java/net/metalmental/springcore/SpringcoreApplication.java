package net.metalmental.springcore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 外部パッケージの場合は、明示的にimportする必要がある ※デフォルトでは自身とサブパッケージのみ
@SpringBootApplication(
		scanBasePackages = {"net.metalmental.springcore",
							"net.metalmental.util"}
)
public class SpringcoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcoreApplication.class, args);
	}

}
