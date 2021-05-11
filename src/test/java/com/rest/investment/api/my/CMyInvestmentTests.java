package com.rest.investment.api.my;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.investment.InvestmentApplicationTests;
import com.rest.investment.investment.DIGetMyInvestments;
import com.rest.investment.investment.DIInvest;
import com.rest.investment.investment.DOGetMyInvestments;
import com.rest.investment.investment.DOInvest;
import com.rest.investment.investment.SGetMyInvestments;
import com.rest.investment.investment.SInvest;

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
public class CMyInvestmentTests extends InvestmentApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SGetMyInvestments sGetMyInvestments;

    @MockBean
    private SInvest sInvest;

    @Test
    @Timeout(value = 1000L, unit = TimeUnit.MILLISECONDS)
    @DisplayName("CMyInvestmentTests: GET api/investment/my/investments")
    public void get() throws Exception {
        /* given */
        String uri = "/api/investment/my/investments";

        DIGetMyInvestments dIGetMyInvestments = DIGetMyInvestments.builder()
                .userId(1L)
                .build();
        
        List<DOGetMyInvestments> dOGetMyInvestments = new ArrayList<>();
        DOGetMyInvestments data0 = DOGetMyInvestments.builder()
                .productId(1L)
                .productTitle("product")
                .totalInvestingAmount(1L)
                .InvestingAmount(1L)
                .investedAt(LocalDateTime.now())
                .build();
        dOGetMyInvestments.add(data0);
        String dOGetMyInvestmentsStr = objectMapper.writeValueAsString(dOGetMyInvestments);
        
        Mockito.doReturn(dOGetMyInvestments)
                .when(sGetMyInvestments)
                .service(Mockito.argThat(input -> dIGetMyInvestments.equals(input)));

        /* when */
        ResultActions ra = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-USER-ID", "1"));
        
        /* then */
        ra.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(dOGetMyInvestmentsStr))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Timeout(value = 1000L, unit = TimeUnit.MILLISECONDS)
    @DisplayName("CMyInvestmentTests: POST api/investment/my/investments")
    public void post() throws Exception {
        /* given */
        String uri = "/api/investment/my/investments";

        DIInvest dIInvest = DIInvest.builder()
                .userId(1L)
                .productId(1L)
                .build();
        String dIInvestStr = objectMapper.writeValueAsString(dIInvest);

        DOInvest dOInvest = DOInvest.builder()
                .message("POST")
                .build();
        String dOInvestStr = objectMapper.writeValueAsString(dOInvest);
                                         
        Mockito.doReturn(dOInvest)
                .when(sInvest)
                .service(Mockito.argThat(input -> dIInvest.equals(input)));
        
        /* when */
        ResultActions ra = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-USER-ID", "1")
                .content(dIInvestStr));

        /* then */
        ra.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(dOInvestStr))
                .andDo(MockMvcResultHandlers.print());
    }
}