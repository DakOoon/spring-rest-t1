package com.rest.investment.api.integrated.my;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.investment.api.integrated.IntegratedTests;
import com.rest.investment.api.my.DIPostMyInvestments;
import com.rest.investment.api.my.DOGetMyInvestments;
import com.rest.investment.api.my.DOPostMyInvestments;
import com.rest.investment.investment.EInvestment;
import com.rest.investment.investment.RInvestment;
import com.rest.investment.product.EProduct;
import com.rest.investment.product.ProductStateType;
import com.rest.investment.product.RProduct;
import com.rest.investment.user.EUser;
import com.rest.investment.user.RUser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

public class CMyInvestmentTests extends IntegratedTests {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RProduct rProduct;

    @Autowired
    private RUser rUser;

    @Autowired
    private RInvestment rInvestment;

    @BeforeEach
    public void product() {
        rInvestment.deleteAll();
        rProduct.deleteAll();
        rUser.deleteAll();
    }
    
    @Test
    @Timeout(value = 3000L, unit = TimeUnit.MILLISECONDS)
    @DisplayName("CMyInvestmentTests: get api/investment/my/investments")
    public void get() throws JsonProcessingException {
        /* given */
        String uri = "api/investment/my/investments";
        
        EUser uData0 = EUser.builder()
                .build();
        EUser uData1 = EUser.builder()
                .build();
        rUser.saveAll(Arrays.asList(uData0, uData1));

        EProduct pData0 = EProduct.builder()
                .title("p0")
                .totalInvestingAmount(15000L)
                .productState(ProductStateType.OPEN.value())
                .startedAt(LocalDateTime.of(2000, 1, 1, 1, 1, 1))
                .finishedAt(LocalDateTime.of(2010, 1, 1, 1, 1, 1))
                .build();
        EProduct pData1 = EProduct.builder()
                .title("p1")
                .totalInvestingAmount(10000L)
                .productState(ProductStateType.OPEN.value())
                .startedAt(LocalDateTime.of(2000, 1, 1, 1, 1, 1))
                .finishedAt(LocalDateTime.of(2020, 1, 1, 1, 1, 1))
                .build();
        rProduct.saveAll(Arrays.asList(pData0, pData1));

        EInvestment iData0 = EInvestment.builder()
                .user(uData0)
                .product(pData1)
                .investingAmount(2000L)
                .build();
        EInvestment iData1 = EInvestment.builder()
                .user(uData0)
                .product(pData0)
                .investingAmount(3000L)
                .build();
        rInvestment.saveAll(Arrays.asList(iData0, iData1));

        /* when */
        ResponseSpec rs = webTestClient.method(HttpMethod.GET)
                .uri(uri)
                .header("X-USER-ID", String.valueOf(uData0.getUserId()))
                .exchange();
        
        /* then */
        DOGetMyInvestments data0 = DOGetMyInvestments.builder()
                .investmentId(iData0.getInvestmentId())
                .productId(iData0.getProduct().getProductId())
                .productTitle(iData0.getProduct().getTitle())
                .totalInvestingAmount(iData0.getProduct().getTotalInvestingAmount())
                .InvestingAmount(iData0.getInvestingAmount())
                .investedAt(iData0.getInvestedAt())
                .build();
        DOGetMyInvestments data1 = DOGetMyInvestments.builder()
                .investmentId(iData1.getInvestmentId())
                .productId(iData1.getProduct().getProductId())
                .productTitle(iData1.getProduct().getTitle())
                .totalInvestingAmount(iData1.getProduct().getTotalInvestingAmount())
                .InvestingAmount(iData1.getInvestingAmount())
                .investedAt(iData1.getInvestedAt())
                .build();
        String dOGetMyInvestmentsStr = objectMapper.writeValueAsString(Arrays.asList(data0, data1));
        
        rs.expectStatus().isOk()
                .expectBody().json(dOGetMyInvestmentsStr);
    }

    @Test
    @Timeout(value = 3000L, unit = TimeUnit.MILLISECONDS)
    @DisplayName("CMyInvestmentTests: post api/investment/my/investments")
    public void post() throws JsonProcessingException {
        /* given */
        String uri = "api/investment/my/investments";
        
        EUser uData0 = EUser.builder()
                .build();
        EUser uData1 = EUser.builder()
                .build();
        rUser.saveAll(Arrays.asList(uData0, uData1));

        EProduct pData0 = EProduct.builder()
                .title("p0")
                .totalInvestingAmount(15000L)
                .productState(ProductStateType.OPEN.value())
                .startedAt(LocalDateTime.of(2000, 1, 1, 1, 1, 1))
                .finishedAt(LocalDateTime.of(2010, 1, 1, 1, 1, 1))
                .build();
        EProduct pData1 = EProduct.builder()
                .title("p1")
                .totalInvestingAmount(10000L)
                .productState(ProductStateType.OPEN.value())
                .startedAt(LocalDateTime.of(2000, 1, 1, 1, 1, 1))
                .finishedAt(LocalDateTime.of(2020, 1, 1, 1, 1, 1))
                .build();
        rProduct.saveAll(Arrays.asList(pData0, pData1));
        
        /* when */
        DIPostMyInvestments dIPostMyInvestments = DIPostMyInvestments.builder()
                .userId(uData0.getUserId())
                .productId(pData0.getProductId())
                .investingAmount(5000L)
                .build();
        String dIPostMyInvestmentsStr = objectMapper.writeValueAsString(dIPostMyInvestments);

        ResponseSpec rs = webTestClient.method(HttpMethod.POST)
                .uri(uri)
                // .header("X-USER-ID", String.valueOf(uData0.getUserId()))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dIPostMyInvestmentsStr)
                .exchange();
                
        /* then */
        FluxExchangeResult<DOGetMyInvestments> result = webTestClient.method(HttpMethod.GET)
                .uri(uri)
                .header("X-USER-ID", String.valueOf(dIPostMyInvestments.getUserId()))
                .exchange()
                .expectStatus().isOk()
                .returnResult(DOGetMyInvestments.class);
        DOGetMyInvestments dOGetMyInvestments = result.getResponseBody().blockFirst();

        DOPostMyInvestments dOPostMyInvestments = DOPostMyInvestments.builder()
                .status("200")
                .message("성공")
                .timestamp(dOGetMyInvestments.getInvestedAt())
                .build();
        String dOPostMyInvestmentsStr = objectMapper.writeValueAsString(dOPostMyInvestments);

        rs.expectStatus().isOk()
                .expectBody().json(dOPostMyInvestmentsStr);
    }
}
