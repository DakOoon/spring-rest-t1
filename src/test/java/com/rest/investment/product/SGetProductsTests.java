package com.rest.investment.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.investment.InvestmentApplicationTests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

public class SGetProductsTests extends InvestmentApplicationTests {

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RProduct RProduct;

    @Autowired
    private SGetProducts sGetProducts;

    @Test
    @Timeout(value = 1000L, unit = TimeUnit.MILLISECONDS)
    @DisplayName("ProductTests: SGetProducts")
    public void SGetProducts() throws JsonProcessingException {
        /* given */
        LocalDateTime date = LocalDateTime.of(2000, 3, 3, 3, 3, 3, 3);

        List<EProduct> data = new ArrayList<>();
        EProduct data0 = EProduct.builder()
                .title("p1")
                .startedAt(LocalDateTime.of(2000, 1, 1, 1, 1, 1, 1))
                .finishedAt(LocalDateTime.of(2000, 2, 2, 2, 2, 2, 2))
                .build();
        EProduct data1 = EProduct.builder()
                .title("p2")
                .startedAt(LocalDateTime.of(2000, 3, 3, 3, 3, 3, 3))
                .finishedAt(LocalDateTime.of(2000, 4, 4, 4, 4, 4, 4))
                .build();
        EProduct data2 = EProduct.builder()
                .title("p3")
                .startedAt(LocalDateTime.of(2000, 1, 1, 1, 1, 1, 1))
                .finishedAt(LocalDateTime.of(2000, 4, 4, 4, 4, 4, 4))
                .build();
        data.add(data0);
        data.add(data1);
        data.add(data2);
        
        Mockito.doReturn(data)
                .when(RProduct)
                .findByDate(date);


        DIGetProducts dIGetProducts = DIGetProducts.builder()
                .build();
        String dIGetProductsStr = objectMapper.writeValueAsString(dIGetProducts);

        /* when */
        List<DOGetProducts> dOGetProducts = sGetProducts.service(dIGetProducts);

        /* then */
        assertNotNull(dOGetProducts);
        assertEquals(1, dOGetProducts.size());
        EProduct found0 = found.get();
        assertEquals(origin.getProductId(), found0.getProductId());
        assertEquals(origin.getTitle(), found0.getTitle());
        assertEquals(origin.getTotalInvestingAmount(), found0.getTotalInvestingAmount());
        assertEquals(0L, found0.getCurrentInvestingAmout());
        assertEquals(0L, found0.getInvestorCount());
        assertEquals(origin.getProductState(), found0.getProductState());
        assertEquals(DateTimeUtils.format(origin.getStartedAt())
                , DateTimeUtils.format(found0.getStartedAt()));
        assertEquals(DateTimeUtils.format(origin.getFinishedAt())
                , DateTimeUtils.format(found0.getFinishedAt()));
    }
}