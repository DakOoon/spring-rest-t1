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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
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
        DOGetMyInvestments data0 = DOGetMyInvestments.builder()
                                                        .productId(1L)
                                                        .productTitle("product")
                                                        .totalInvestingAmount(1L)
                                                        .InvestingAmount(1L)
                                                        .investedAt(LocalDateTime.of(2000, 1, 1, 1, 1, 1, 1))
                                                        .build();
        List<DOGetMyInvestments> serviceOutput = new ArrayList<>();
        serviceOutput.add(data0);
        
        Mockito.doReturn(serviceOutput)
                .when(sGetMyInvestments)
                .service(Mockito.any());

        /* when */
        ResultActions ra = mockMvc.perform(MockMvcRequestBuilders.get(uri).header("X-USER-ID", "1"));
        
        /* then */
        ra.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[{\"productId\":1,\"productTitle\":\"product\",\"totalInvestingAmount\":1,\"investedAt\":\"2000-01-01T01:01:01.000000001\",\"investingAmount\":1}]"))
                .andDo(MockMvcResultHandlers.print());
    }
}