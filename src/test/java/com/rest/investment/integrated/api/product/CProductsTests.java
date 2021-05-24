package com.rest.investment.integrated.api.product;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.investment.integrated.IntegratedTests;
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
    public void delete() {
        rProduct.deleteAll();
    }

    @Test
    @Timeout(value = 3000L, unit = TimeUnit.MILLISECONDS)
    @DisplayName("CProductsTests: getNone")
    public void getNone() throws JsonProcessingException {
        /* given */
        String uri = "/api/investment/products";
        LocalDateTime target = LocalDateTime.of(2033, 1, 1, 1, 1, 1);

        EProduct pData0 = EProduct.builder()
                .title("p0")
                .totalInvestingAmount(10L)
                .productState(ProductStateType.OPEN.value())
                .startedAt(target.minusDays(2))
                .finishedAt(target.minusDays(1))
                .build();
        EProduct pData1 = EProduct.builder()
                .title("p1")
                .totalInvestingAmount(10L)
                .productState(ProductStateType.OPEN.value())
                .startedAt(target.plusDays(1))
                .finishedAt(target.plusDays(2))
                .build();
        rProduct.saveAll(Arrays.asList(pData0, pData1));

        DIGetProducts dIGetProducts = DIGetProducts.builder()
                .date(target)
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
    @DisplayName("CProductsTests: getOne")
    public void getOne() throws JsonProcessingException {
        /* given */
        String uri = "/api/investment/products";
        LocalDateTime target = LocalDateTime.of(2033, 1, 1, 1, 1, 1);
        
        EProduct pData0 = EProduct.builder()
                .title("p0")
                .totalInvestingAmount(10L)
                .productState(ProductStateType.OPEN.value())
                .startedAt(target.minusDays(2))
                .finishedAt(target.minusDays(1))
                .build();
        EProduct pData1 = EProduct.builder()
                .title("p1")
                .totalInvestingAmount(10L)
                .productState(ProductStateType.OPEN.value())
                .startedAt(target.minusDays(1))
                .finishedAt(target.plusDays(1))
                .build();
        rProduct.saveAll(Arrays.asList(pData0, pData1));

        DIGetProducts dIGetProducts = DIGetProducts.builder()
                .date(target)
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
    @DisplayName("CProductsTests: getMany")
    public void getMany() throws JsonProcessingException {
        /* given */
        String uri = "/api/investment/products";
        LocalDateTime target = LocalDateTime.of(2033, 1, 1, 1, 1, 1);
        
        EProduct pData0 = EProduct.builder()
                .title("p0")
                .totalInvestingAmount(10L)
                .productState(ProductStateType.OPEN.value())
                .startedAt(target.minusDays(1))
                .finishedAt(target.plusDays(1))
                .build();
        EProduct pData1 = EProduct.builder()
                .title("p1")
                .totalInvestingAmount(10L)
                .productState(ProductStateType.OPEN.value())
                .startedAt(target.minusDays(20))
                .finishedAt(target.plusDays(5))
                .build();
        EProduct pData2 = EProduct.builder()
                .title("p2")
                .totalInvestingAmount(10L)
                .productState(ProductStateType.OPEN.value())
                .startedAt(target.plusDays(1))
                .finishedAt(target.plusDays(2))
                .build();
        rProduct.saveAll(Arrays.asList(pData0, pData1, pData2));

        DIGetProducts dIGetProducts = DIGetProducts.builder()
                .date(target)
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

    @Test
    @Timeout(value = 3000L, unit = TimeUnit.MILLISECONDS)
    @DisplayName("CProductsTests: getWithoutDate")
    public void getWithoutDate() throws JsonProcessingException {
        /* given */
        String uri = "/api/investment/products";
        LocalDateTime target = LocalDateTime.now();
        
        EProduct pData0 = EProduct.builder()
                .title("p0")
                .totalInvestingAmount(10L)
                .productState(ProductStateType.OPEN.value())
                .startedAt(target.minusDays(1))
                .finishedAt(target.plusDays(1))
                .build();
        EProduct pData1 = EProduct.builder()
                .title("p1")
                .totalInvestingAmount(10L)
                .productState(ProductStateType.OPEN.value())
                .startedAt(target.minusDays(5))
                .finishedAt(target.minusDays(1))
                .build();
        EProduct pData2 = EProduct.builder()
                .title("p2")
                .totalInvestingAmount(10L)
                .productState(ProductStateType.OPEN.value())
                .startedAt(target.minusDays(5))
                .finishedAt(target.plusWeeks(1))
                .build();
        rProduct.saveAll(Arrays.asList(pData0, pData1, pData2));

        DIGetProducts dIGetProducts = DIGetProducts.builder()
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
                .productId(pData2.getProductId())
                .productTitle(pData2.getTitle())
                .totalInvestingAmount(pData2.getTotalInvestingAmount())
                .currentInvestingAmout(pData2.getCurrentInvestingAmout())
                .investorCount(pData2.getInvestorCount())
                .productState(pData2.getProductState())
                .startedAt(pData2.getStartedAt())
                .finishedAt(pData2.getFinishedAt())
                .build();
        String dOGetProductsStr = objectMapper.writeValueAsString(Arrays.asList(data0, data1));

        rs.expectStatus().isOk()
                .expectBody().json(dOGetProductsStr);
    }
}