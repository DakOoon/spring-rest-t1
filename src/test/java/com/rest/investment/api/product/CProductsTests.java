package com.rest.investment.api.product;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.investment.InvestmentApplicationTests;
import com.rest.investment.product.DIGetProducts;
import com.rest.investment.product.DOGetProducts;
import com.rest.investment.product.SGetProducts;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CProductsTests extends InvestmentApplicationTests{
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SGetProducts sGetProducts;

    @Test
    @Timeout(value = 1000L, unit = TimeUnit.MILLISECONDS)
    @DisplayName("CProductsTests: GET api/investment/products")
    public void get() throws Exception {
        /* given */
        String uri = "/api/investment/products";

        DIGetProducts dIGetProducts = DIGetProducts.builder()
                .build();
        String dIGetProductsStr = objectMapper.writeValueAsString(dIGetProducts);

        List<DOGetProducts> dOGetProducts = new ArrayList<>();
        DOGetProducts data0 = DOGetProducts.builder()
                .build();
        dOGetProducts.add(data0);
        String dOGetProductsStr = objectMapper.writeValueAsString(dOGetProducts);

        Mockito.doReturn(dOGetProducts)
                .when(sGetProducts)
                .service(Mockito.any());

        /* when */
        ResultActions ra = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(dIGetProductsStr));

        /* then */
        ra.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(dOGetProductsStr))
                .andDo(MockMvcResultHandlers.print());
    }
}