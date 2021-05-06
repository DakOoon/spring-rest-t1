package com.rest.investment.product;

import java.util.concurrent.TimeUnit;

import com.rest.investment.InvestmentApplicationTests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.boot.test.mock.mockito.MockBean;

public class SGetProductsTests extends InvestmentApplicationTests{

    @MockBean
    private RProduct RProduct;

    @Test
    @Timeout(value = 1000L, unit = TimeUnit.MILLISECONDS)
    @DisplayName("ProductTests: SGetProducts")
    public void SGetProducts() {
        /* given */
        /* when */
        /* then */
    }
}