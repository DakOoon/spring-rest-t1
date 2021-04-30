package com.kakaopay.investment.api.my;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.kakaopay.investment.InvestmentApplicationTests;
import com.kakaopay.investment.investment.DIGetMyInvestments;
import com.kakaopay.investment.investment.DOGetMyInvestments;
import com.kakaopay.investment.investment.SGetMyInvestments;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
// @WebMvcTest(CMyInvestment.class)
public class CMyInvestmentTests extends InvestmentApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SGetMyInvestments sGetMyInvestments;

    @Test
    @Timeout(value = 1000L, unit = TimeUnit.MILLISECONDS)
    @DisplayName("CMyInvestmentTests: GET api/investment/my/investments")
    public void get() throws Exception {
        /* given */
        String uri = "/api/investment/my/investments";

        DIGetMyInvestments serviceInput = DIGetMyInvestments.builder()
                                        .userId(1L)
                                        .build();
        DOGetMyInvestments data = DOGetMyInvestments.builder()
                                        .productId(1L)
                                        .productTitle("product")
                                        .totalInvestingAmount(1L)
                                        .InvestingAmount(1L)
                                        .investedAt(LocalDateTime.of(2000, 1, 1, 1, 1, 1, 1))
                                        .build();
        List<DOGetMyInvestments> serviceOutput = new ArrayList<>();
        serviceOutput.add(data);
        
        Mockito.when(sGetMyInvestments.service(serviceInput)).thenReturn(serviceOutput);

        /* when */
        MultiValueMap<String, String> input = new LinkedMultiValueMap<>();
        input.add("X-USER-ID", "1");
        
        mockMvc.perform(MockMvcRequestBuilders.get(uri).params(input))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("hello gracelove"))
                .andDo(MockMvcResultHandlers.print());

        /* then */

    }
}