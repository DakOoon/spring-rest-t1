package com.rest.investment.api.my;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.investment.api.UnitTests;
import com.rest.investment.investment.SGetMyInvestments;
import com.rest.investment.investment.SPostMyInvestments;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class CMyInvestmentTests extends UnitTests {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SGetMyInvestments sGetMyInvestments;

    @MockBean
    private SPostMyInvestments sPostMyInvestments;

    @Test
    @Timeout(value = 1000L, unit = TimeUnit.MILLISECONDS)
    @DisplayName("get")
    public void get() throws Exception {
        /* given */
        String uri = "/api/investment/my/investments";
        Long userId = 10L;

        DIGetMyInvestments dIGetMyInvestments = DIGetMyInvestments.builder()
                .userId(userId)
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
                .header("X-USER-ID", userId));
        
        /* then */
        ra.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(dOGetMyInvestmentsStr))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Timeout(value = 1000L, unit = TimeUnit.MILLISECONDS)
    @DisplayName("post")
    public void post() throws Exception {
        /* given */
        String uri = "/api/investment/my/investments";
        Long userId = 10L;

        DIPostMyInvestments dIPostMyInvestments = DIPostMyInvestments.builder()
                .userId(userId)
                .productId(1L)
                .investingAmount(3000L)
                .build();
        String dIPostMyInvestmentsStr = objectMapper.writeValueAsString(dIPostMyInvestments);

        DOPostMyInvestments dOPostMyInvestments = DOPostMyInvestments.builder()
                .message("POST")
                .build();
        String dOPostMyInvestmentsStr = objectMapper.writeValueAsString(dOPostMyInvestments);
                                         
        Mockito.doReturn(dOPostMyInvestments)
                .when(sPostMyInvestments)
                .service(Mockito.argThat(input -> dIPostMyInvestments.equals(input)));
        
        /* when */
        ResultActions ra = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(dIPostMyInvestmentsStr));

        /* then */
        ra.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(dOPostMyInvestmentsStr))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Timeout(value = 1000L, unit = TimeUnit.MILLISECONDS)
    @DisplayName("postWithHeader")
    public void postWithHeader() throws Exception {
        /* given */
        String uri = "/api/investment/my/investments";
        Long userId = 10L;

        DIPostMyInvestments dIPostMyInvestments = DIPostMyInvestments.builder()
                .productId(1L)
                .investingAmount(3000L)
                .build();
        String dIPostMyInvestmentsStr = objectMapper.writeValueAsString(dIPostMyInvestments);

        DOPostMyInvestments dOPostMyInvestments = DOPostMyInvestments.builder()
                .message("POST")
                .build();
        String dOPostMyInvestmentsStr = objectMapper.writeValueAsString(dOPostMyInvestments);
                                         
        Mockito.doReturn(dOPostMyInvestments)
                .when(sPostMyInvestments)
                .service(Mockito.argThat(
                        input -> {
                            if(dIPostMyInvestments.getUserId() == null) {
                                dIPostMyInvestments.setUserId(userId);
                                return dIPostMyInvestments.equals(input);
                            }
                            return false;
                        }));
        
        /* when */
        ResultActions ra = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-USER-ID", userId)
                .content(dIPostMyInvestmentsStr));

        /* then */
        ra.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(dOPostMyInvestmentsStr))
                .andDo(MockMvcResultHandlers.print());
    }
}