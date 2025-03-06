package com.example.demo3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// エントリーポイントのアノテーション
// サーバの起動ポートなどは、resources/application.properties 設定ファイルに記載し、環境変数のimportも可能
@SpringBootApplication
public class Demo3Application {

	public static void main(String[] args) {
		SpringApplication.run(Demo3Application.class, args);
	}

}
