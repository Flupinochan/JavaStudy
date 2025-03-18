package net.metalmental.thymeleaf.controller;

import jakarta.validation.Valid;
import net.metalmental.thymeleaf.model.Customer;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CustomerController {
    // 全てのコントローラの引数(リクエスト)のデータを前処理する
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        // 半角空白を除去、空文字はnullにする ※全角空白は非対応
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        // 全てのStringクラスに適用
        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/customer")
    public String showForm(Model model){
        model.addAttribute("customer", new Customer());

        return "customer-form";
    }

    // @Validでバリデーションを適用、BindingResultにバリデーションの結果が(エラー等)が保存される
    @PostMapping("/processForm")
    public String processForm(@Valid @ModelAttribute("customer") Customer customer,
                              BindingResult bindingResult){

        System.out.println("Last Name:" + customer.getLastName());
        System.out.println("Binding Result: " + bindingResult.toString());
        if (bindingResult.hasErrors()){
            return "customer-form";
        }else{
            return "customer-confirmation";
        }
    }
}
