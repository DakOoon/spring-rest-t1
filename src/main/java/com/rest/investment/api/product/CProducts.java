package com.rest.investment.api.product;

import java.util.List;

import com.rest.investment.product.DIGetProducts;
import com.rest.investment.product.DOGetProducts;
import com.rest.investment.product.SGetProducts;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CProducts {
    
    private final SGetProducts sGetProducts;

    @GetMapping("api/investment/products")
    public List<DOGetProducts> getProducts(@RequestBody @Validated final DIGetProducts input) {

        List<DOGetProducts> output = sGetProducts.service(input);

        return output;
    }
}
