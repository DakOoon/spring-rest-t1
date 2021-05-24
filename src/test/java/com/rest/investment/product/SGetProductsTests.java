package com.rest.investment.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.rest.investment.InvestmentApplicationTests;
import com.rest.investment.api.product.DIGetProducts;
import com.rest.investment.api.product.DOGetProducts;
import com.rest.investment.util.DateTimeUtils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

public class SGetProductsTests extends InvestmentApplicationTests {

    @MockBean
    private RProduct rProduct;

    @Autowired
    private SGetProducts sGetProducts;

    @Test
    @Timeout(value = 1000L, unit = TimeUnit.MILLISECONDS)
    @DisplayName("SGetProductsTests: success")
    public void success() {
        /* given */
        LocalDateTime date = LocalDateTime.of(2000, 3, 3, 3, 3, 3);

        EProduct data0 = EProduct.builder()
                .title("p0")
                .build();
        EProduct data1 = EProduct.builder()
                .title("p1")
                .build();

        List<EProduct> data = new ArrayList<>();
        data.add(data0);
        data.add(data1);

        Mockito.doReturn(data)
                .when(rProduct)
                .findByDate(date);
                
        /* when */
        DIGetProducts dIGetProducts = DIGetProducts.builder()
                .date(date)
                .build();
        List<DOGetProducts> dOGetProducts = sGetProducts.service(dIGetProducts);

        /* then */
        assertNotNull(dOGetProducts);
        assertEquals(2, dOGetProducts.size());
        assertEquals(data0.getProductId(), dOGetProducts.get(0).getProductId());
        assertEquals(data0.getTitle(), dOGetProducts.get(0).getProductTitle());
        assertEquals(data1.getProductId(), dOGetProducts.get(1).getProductId());
        assertEquals(data1.getTitle(), dOGetProducts.get(1).getProductTitle());
    }

    @Test
    @Timeout(value = 1000L, unit = TimeUnit.MILLISECONDS)
    @DisplayName("SGetProductsTests: withoutDate")
    public void withoutDate() {
        /* given */
        LocalDateTime now = LocalDateTime.now();

        EProduct data0 = EProduct.builder()
                .title("p0")
                .build();

        List<EProduct> data = new ArrayList<>();
        data.add(data0);
        
        Mockito.doReturn(data)
                .when(rProduct)
                .findByDate(Mockito.argThat(input -> 
                        DateTimeUtils.format(now).equals(DateTimeUtils.format(input))));
                
        /* when */
        DIGetProducts dIGetProducts = DIGetProducts.builder()
                .build();
        List<DOGetProducts> dOGetProducts = sGetProducts.service(dIGetProducts);

        /* then */
        assertNotNull(dOGetProducts);
        assertEquals(1, dOGetProducts.size());
        assertEquals(data0.getProductId(), dOGetProducts.get(0).getProductId());
        assertEquals(data0.getTitle(), dOGetProducts.get(0).getProductTitle());
    }
}