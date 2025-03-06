package net.metalmental.thymeleaf.controller;

import net.metalmental.thymeleaf.dto.CountryForm;
import net.metalmental.thymeleaf.model.Country;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class BindController {

    @GetMapping("/showPage")
    public String showPage(Model model){

        // 表示するComboBoxの値のリスト
        List<Country> countries = Arrays.asList(
                new Country("JP", "Japan"),
                new Country("US", "United States"),
                new Country("FR", "France")
        );

        // ComboBoxに上記リストを追加
        model.addAttribute("countries", countries);

        //
        model.addAttribute("countryForm", new CountryForm());

        return "showForm";
    }

    @PostMapping("/showPage")
    public String submitShowPage(@ModelAttribute CountryForm countryForm, Model model){
        model.addAttribute("selectedCountry", countryForm.getSelectedCountry());
        return "result";
    }
}
