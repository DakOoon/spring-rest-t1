package com.rest.investment.api.integrated.product;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.investment.api.integrated.IntegratedTests;
import com.rest.investment.api.product.DIGetProducts;
import com.rest.investment.api.product.DOGetProducts;
import com.rest.investment.product.EProduct;
import com.rest.investment.product.ProductStateType;
import com.rest.investment.product.RProduct;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

public class CProductsTests extends IntegratedTests {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RProduct rProduct;

    @BeforeEach
    public void product() {
        rProduct.deleteAll();
    }

    @Test
    @Timeout(value = 3000L, unit = TimeUnit.MILLISECONDS)
    @DisplayName("CProductsTests: getNone api/investment/products")
    public void getNone() throws JsonProcessingException {
        /* given */
        String uri = "/api/investment/products";
        
        DIGetProducts dIGetProducts = DIGetProducts.builder()
                .date(LocalDateTime.of(2033, 1, 1, 1, 1, 1))
                .build();
        String dIGetProductsStr = objectMapper.writeValueAsString(dIGetProducts);
        
        /* when */
        ResponseSpec rs = webTestClient.method(HttpMethod.GET)
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dIGetProductsStr)
                .exchange();
        
        /* then */
        String dOGetProductsStr = "[]";

        rs.expectStatus().isOk()
                .expectBody().json(dOGetProductsStr);
    }

    @Test
    @Timeout(value = 3000L, unit = TimeUnit.MILLISECONDS)
    @DisplayName("CProductsTests: getOne api/investment/products")
    public void getOne() throws JsonProcessingException {
        /* given */
        String uri = "/api/investment/products";
        
        EProduct pData0 = EProduct.builder()
                .title("p0")
                .totalInvestingAmount(10L)
                .productState(ProductStateType.OPEN.value())
                .startedAt(LocalDateTime.of(2000, 1, 1, 1, 1, 1))
                .finishedAt(LocalDateTime.of(2010, 1, 1, 1, 1, 1))
                .build();
        EProduct pData1 = EProduct.builder()
                .title("p1")
                .totalInvestingAmount(10L)
                .productState(ProductStateType.OPEN.value())
                .startedAt(LocalDateTime.of(2000, 1, 1, 1, 1, 1))
                .finishedAt(LocalDateTime.of(2020, 1, 1, 1, 1, 1))
                .build();
        rProduct.saveAll(Arrays.asList(pData0, pData1));

        DIGetProducts dIGetProducts = DIGetProducts.builder()
                .date(LocalDateTime.of(2015, 1, 1, 1, 1, 1))
                .build();
        String dIGetProductsStr = objectMapper.writeValueAsString(dIGetProducts);
        
        /* when */
        ResponseSpec rs = webTestClient.method(HttpMethod.GET)
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dIGetProductsStr)
                .exchange();
                
        /* then */
        DOGetProducts data0 = DOGetProducts.builder()
                .productId(pData1.getProductId())
                .productTitle(pData1.getTitle())
                .totalInvestingAmount(pData1.getTotalInvestingAmount())
                .currentInvestingAmout(pData1.getCurrentInvestingAmout())
                .investorCount(pData1.getInvestorCount())
                .productState(pData1.getProductState())
                .startedAt(pData1.getStartedAt())
                .finishedAt(pData1.getFinishedAt())
                .build();
        String dOGetProductsStr = objectMapper.writeValueAsString(Arrays.asList(data0));

        rs.expectStatus().isOk()
                .expectBody().json(dOGetProductsStr);
    }

    @Test
    @Timeout(value = 3000L, unit = TimeUnit.MILLISECONDS)
    @DisplayName("CProductsTests: getMany api/investment/products")
    public void getMany() throws JsonProcessingException {
        /* given */
        String uri = "/api/investment/products";
        
        EProduct pData0 = EProduct.builder()
                .title("p0")
                .totalInvestingAmount(10L)
                .productState(ProductStateType.OPEN.value())
                .startedAt(LocalDateTime.of(2000, 1, 1, 1, 1, 1))
                .finishedAt(LocalDateTime.of(2010, 1, 1, 1, 1, 1))
                .build();
        EProduct pData1 = EProduct.builder()
                .title("p1")
                .totalInvestingAmount(10L)
                .productState(ProductStateType.OPEN.value())
                .startedAt(LocalDateTime.of(2010, 1, 1, 1, 1, 1))
                .finishedAt(LocalDateTime.of(2020, 1, 1, 1, 1, 1))
                .build();
        EProduct pData2 = EProduct.builder()
                .title("p2")
                .totalInvestingAmount(10L)
                .productState(ProductStateType.OPEN.value())
                .startedAt(LocalDateTime.of(2025, 1, 1, 1, 1, 1))
                .finishedAt(LocalDateTime.of(2030, 1, 1, 1, 1, 1))
                .build();
        rProduct.saveAll(Arrays.asList(pData0, pData1, pData2));

        DIGetProducts dIGetProducts = DIGetProducts.builder()
                .date(LocalDateTime.of(2010, 1, 1, 1, 1, 1))
                .build();
        String dIGetProductsStr = objectMapper.writeValueAsString(dIGetProducts);
        
        /* when */
        ResponseSpec rs = webTestClient.method(HttpMethod.GET)
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dIGetProductsStr)
                .exchange();
                
        /* then */
        DOGetProducts data0 = DOGetProducts.builder()
                .productId(pData0.getProductId())
                .productTitle(pData0.getTitle())
                .totalInvestingAmount(pData0.getTotalInvestingAmount())
                .currentInvestingAmout(pData0.getCurrentInvestingAmout())
                .investorCount(pData0.getInvestorCount())
                .productState(pData0.getProductState())
                .startedAt(pData0.getStartedAt())
                .finishedAt(pData0.getFinishedAt())
                .build();
        DOGetProducts data1 = DOGetProducts.builder()
                .productId(pData1.getProductId())
                .productTitle(pData1.getTitle())
                .totalInvestingAmount(pData1.getTotalInvestingAmount())
                .currentInvestingAmout(pData1.getCurrentInvestingAmout())
                .investorCount(pData1.getInvestorCount())
                .productState(pData1.getProductState())
                .startedAt(pData1.getStartedAt())
                .finishedAt(pData1.getFinishedAt())
                .build();
        String dOGetProductsStr = objectMapper.writeValueAsString(Arrays.asList(data0, data1));

        rs.expectStatus().isOk()
                .expectBody().json(dOGetProductsStr);
    }
}