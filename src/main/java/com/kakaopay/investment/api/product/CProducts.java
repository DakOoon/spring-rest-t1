package com.kakaopay.investment.api.product;

import java.util.List;

import com.kakaopay.investment.product.DIGetProducts;
import com.kakaopay.investment.product.DOGetProducts;
import com.kakaopay.investment.product.SGetProducts;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CProducts {
    
    private final SGetProducts sGetProducts;

    @GetMapping("api/investment/products")
    public List<DOGetProducts> getProducts(@RequestBody DIGetProducts input) {

        List<DOGetProducts> output = sGetProducts.service(input);

        return output;
    }
}
