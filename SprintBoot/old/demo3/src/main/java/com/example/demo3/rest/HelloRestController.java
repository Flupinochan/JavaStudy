package com.example.demo3.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// @SpringBootApplicationアノテーションと同一or配下のパッケージを検索する
// @RestControllerがコントローラーとして機能するクラスである
@RestController
public class HelloRestController {

    // application.properties ファイルの環境変数読み込み
    @Value("${hello.name}")
    private String helloWorld;

    // Getリクエストのマッピング
    @GetMapping("/")
    public String sayHello(){
        return this.helloWorld;
    }
}
