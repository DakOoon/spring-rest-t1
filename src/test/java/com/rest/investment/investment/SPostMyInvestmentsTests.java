package com.rest.investment.investment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.rest.investment.InvestmentApplicationTests;
import com.rest.investment.api.my.DIPostMyInvestments;
import com.rest.investment.api.my.DOPostMyInvestments;
import com.rest.investment.product.EProduct;
import com.rest.investment.product.ProductStateType;
import com.rest.investment.product.RProduct;
import com.rest.investment.user.EUser;
import com.rest.investment.user.RUser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

public class SPostMyInvestmentsTests extends InvestmentApplicationTests {

    @MockBean
    private RInvestment rInvestment;

    @MockBean
    private RProduct rProduct;

    @MockBean
    private RUser rUser;

    @Autowired
    private SPostMyInvestments sPostMyInvestments;

    @Test
    @Timeout(value = 1000L, unit = TimeUnit.MILLISECONDS)
    @DisplayName("SPostMyInvestmentsTests: success")
    public void success() {
        /* given */
        Long userId = 1L;
        Long productId = 1L;
        Long investingAmount = 5000L;

        EUser uData0 = EUser.builder()
                .userId(userId)
                .build();
                
        Mockito.doReturn(Optional.of(uData0))
                .when(rUser)
                .findById(userId);

        EProduct pData0 = EProduct.builder()
                .productId(productId)
                .totalInvestingAmount(10000L)
                .productState(ProductStateType.OPEN.value())
                .startedAt(LocalDateTime.of(2000, 3, 3, 3, 3, 3, 3))
                .finishedAt(LocalDateTime.of(2099, 3, 3, 3, 3, 3, 3))
                .build();

        Mockito.doReturn(Optional.of(pData0))
                .when(rProduct)
                .findById(productId);

        EInvestment iData0 = EInvestment.builder()
                .user(uData0)
                .product(pData0)
                .investingAmount(investingAmount)
                .investedAt(LocalDateTime.now())
                .build();

        Mockito.doReturn(iData0)
                .when(rInvestment)
                .save(Mockito.argThat(input -> {
                    return uData0.getUserId().equals(input.getUser().getUserId()) &&
                            pData0.getProductId().equals(input.getProduct().getProductId()) &&
                            iData0.getInvestingAmount().equals(input.getInvestingAmount());
                }));
                
        /* when */
        DIPostMyInvestments dIPostMyInvestments = DIPostMyInvestments.builder()
                .userId(userId)
                .productId(productId)
                .investingAmount(investingAmount)
                .build();
        DOPostMyInvestments dOPostMyInvestments = sPostMyInvestments.service(dIPostMyInvestments);

        /* then */
        assertNotNull(dOPostMyInvestments);
        assertEquals("200", dOPostMyInvestments.getStatus());
        assertEquals("성공", dOPostMyInvestments.getMessage());
        verify(rProduct, times(0)).save(any());
    }
    
    
    @Test
    @Timeout(value = 1000L, unit = TimeUnit.MILLISECONDS)
    @DisplayName("SPostMyInvestmentsTests: validateInput")
    public void validateInput() {
        /* given */
        Long normalUserId = 1L;
        Long normalProductId = 1L;
        Long normalInvestingAmount = 1000L;

        Long abnormalUserId = -1L;
        Long abnormalProductId = -1L;
        Long abnormalInvestingAmount = -1000L;
                
        {
            /* when */
            DIPostMyInvestments dIPostMyInvestments = DIPostMyInvestments.builder()
                    .build();
            DOPostMyInvestments dOPostMyInvestments = sPostMyInvestments.service(dIPostMyInvestments);

            /* then */
            assertNotNull(dOPostMyInvestments);
            assertEquals("400", dOPostMyInvestments.getStatus());
            assertEquals("잘못된 입력값입니다.", dOPostMyInvestments.getMessage());
        }
        {
            /* when */
            DIPostMyInvestments dIPostMyInvestments = DIPostMyInvestments.builder()
                    .userId(abnormalUserId)
                    .productId(normalProductId)
                    .investingAmount(normalInvestingAmount)
                    .build();
            DOPostMyInvestments dOPostMyInvestments = sPostMyInvestments.service(dIPostMyInvestments);

            /* then */
            assertNotNull(dOPostMyInvestments);
            assertEquals("400", dOPostMyInvestments.getStatus());
            assertEquals("잘못된 입력값입니다.", dOPostMyInvestments.getMessage());
        }
        {
            /* when */
            DIPostMyInvestments dIPostMyInvestments = DIPostMyInvestments.builder()
                    .userId(normalUserId)
                    .productId(abnormalProductId)
                    .investingAmount(normalInvestingAmount)
                    .build();
            DOPostMyInvestments dOPostMyInvestments = sPostMyInvestments.service(dIPostMyInvestments);

            /* then */
            assertNotNull(dOPostMyInvestments);
            assertEquals("400", dOPostMyInvestments.getStatus());
            assertEquals("잘못된 입력값입니다.", dOPostMyInvestments.getMessage());
        }
        {
            /* when */
            DIPostMyInvestments dIPostMyInvestments = DIPostMyInvestments.builder()
                    .userId(normalUserId)
                    .productId(normalProductId)
                    .investingAmount(abnormalInvestingAmount)
                    .build();
            DOPostMyInvestments dOPostMyInvestments = sPostMyInvestments.service(dIPostMyInvestments);

            /* then */
            assertNotNull(dOPostMyInvestments);
            assertEquals("400", dOPostMyInvestments.getStatus());
            assertEquals("잘못된 입력값입니다.", dOPostMyInvestments.getMessage());
        }
    }
    
    @Test
    @Timeout(value = 1000L, unit = TimeUnit.MILLISECONDS)
    @DisplayName("SPostMyInvestmentsTests: checkInvestingAmountLessThanMinimumAmount")
    public void checkInvestingAmountLessThanMinimumAmount() {
        /* given */
        Long userId = 1L;
        Long productId = 1L;
        Long totalInvestingAmount = 10000L;
        Long currentInvestingAmout = 5000L;
        Long investingAmount = SPostMyInvestments.MIN_INVESTING_AMOUNT - 1;

        EUser uData0 = EUser.builder()
                .userId(userId)
                .build();
                
        Mockito.doReturn(Optional.of(uData0))
                .when(rUser)
                .findById(userId);

        EProduct pData0 = EProduct.builder()
                .productId(productId)
                .totalInvestingAmount(totalInvestingAmount)
                .currentInvestingAmout(currentInvestingAmout)
                .productState(ProductStateType.OPEN.value())
                .startedAt(LocalDateTime.of(2000, 3, 3, 3, 3, 3, 3))
                .finishedAt(LocalDateTime.of(2099, 3, 3, 3, 3, 3, 3))
                .build();

        Mockito.doReturn(Optional.of(pData0))
                .when(rProduct)
                .findById(productId);

        EInvestment iData0 = EInvestment.builder()
                .user(uData0)
                .product(pData0)
                .investingAmount(investingAmount)
                .investedAt(LocalDateTime.now())
                .build();

        Mockito.doReturn(iData0)
                .when(rInvestment)
                .save(Mockito.argThat(input -> {
                    return uData0.getUserId().equals(input.getUser().getUserId()) &&
                            pData0.getProductId().equals(input.getProduct().getProductId()) &&
                            iData0.getInvestingAmount().equals(input.getInvestingAmount());
                }));

        /* when */
        DIPostMyInvestments dIPostMyInvestments = DIPostMyInvestments.builder()
                .userId(userId)
                .productId(productId)
                .investingAmount(investingAmount)
                .build();
        DOPostMyInvestments dOPostMyInvestments = sPostMyInvestments.service(dIPostMyInvestments);

        /* then */
        assertNotNull(dOPostMyInvestments);
        assertEquals("400", dOPostMyInvestments.getStatus());
        assertEquals("최소 투자금액은 "+SPostMyInvestments.MIN_INVESTING_AMOUNT+"원 입니다.", dOPostMyInvestments.getMessage());
        verify(rProduct, times(0)).save(any());
    }
    
    @Test
    @Timeout(value = 1000L, unit = TimeUnit.MILLISECONDS)
    @DisplayName("SPostMyInvestmentsTests: checkInvestingAmountEqualToMinimumAmount")
    public void checkInvestingAmountEqualToMinimumAmount() {
        /* given */
        Long userId = 1L;
        Long productId = 1L;
        Long totalInvestingAmount = 10000L;
        Long currentInvestingAmout = 5000L;
        Long investingAmount = SPostMyInvestments.MIN_INVESTING_AMOUNT;

        EUser uData0 = EUser.builder()
                .userId(userId)
                .build();
                
        Mockito.doReturn(Optional.of(uData0))
                .when(rUser)
                .findById(userId);

        EProduct pData0 = EProduct.builder()
                .productId(productId)
                .totalInvestingAmount(totalInvestingAmount)
                .currentInvestingAmout(currentInvestingAmout)
                .productState(ProductStateType.OPEN.value())
                .startedAt(LocalDateTime.of(2000, 3, 3, 3, 3, 3, 3))
                .finishedAt(LocalDateTime.of(2099, 3, 3, 3, 3, 3, 3))
                .build();

        Mockito.doReturn(Optional.of(pData0))
                .when(rProduct)
                .findById(productId);

        EInvestment iData0 = EInvestment.builder()
                .user(uData0)
                .product(pData0)
                .investingAmount(investingAmount)
                .investedAt(LocalDateTime.now())
                .build();

        Mockito.doReturn(iData0)
                .when(rInvestment)
                .save(Mockito.argThat(input -> {
                    return uData0.getUserId().equals(input.getUser().getUserId()) &&
                            pData0.getProductId().equals(input.getProduct().getProductId()) &&
                            iData0.getInvestingAmount().equals(input.getInvestingAmount());
                }));

        /* when */
        DIPostMyInvestments dIPostMyInvestments = DIPostMyInvestments.builder()
                .userId(userId)
                .productId(productId)
                .investingAmount(investingAmount)
                .build();
        DOPostMyInvestments dOPostMyInvestments = sPostMyInvestments.service(dIPostMyInvestments);

        /* then */
        assertNotNull(dOPostMyInvestments);
        assertEquals("200", dOPostMyInvestments.getStatus());
        assertEquals("성공", dOPostMyInvestments.getMessage());
        verify(rProduct, times(0)).save(any());
    }
    
    @Test
    @Timeout(value = 1000L, unit = TimeUnit.MILLISECONDS)
    @DisplayName("SPostMyInvestmentsTests: checkInvestingAmountEqualToMinimumLessThanTotal")
    public void checkInvestingAmountEqualToMinimumLessThanTotal() {
        /* given */
        Long userId = 1L;
        Long productId = 1L;
        Long totalInvestingAmount = 10000L;
        Long currentInvestingAmout = 5000L;
        Long investingAmount = totalInvestingAmount - currentInvestingAmout - SPostMyInvestments.MIN_INVESTING_AMOUNT;

        EUser uData0 = EUser.builder()
                .userId(userId)
                .build();
                
        Mockito.doReturn(Optional.of(uData0))
                .when(rUser)
                .findById(userId);

        EProduct pData0 = EProduct.builder()
                .productId(productId)
                .totalInvestingAmount(totalInvestingAmount)
                .currentInvestingAmout(currentInvestingAmout)
                .productState(ProductStateType.OPEN.value())
                .startedAt(LocalDateTime.of(2000, 3, 3, 3, 3, 3, 3))
                .finishedAt(LocalDateTime.of(2099, 3, 3, 3, 3, 3, 3))
                .build();

        Mockito.doReturn(Optional.of(pData0))
                .when(rProduct)
                .findById(productId);

        EInvestment iData0 = EInvestment.builder()
                .user(uData0)
                .product(pData0)
                .investingAmount(investingAmount)
                .investedAt(LocalDateTime.now())
                .build();

        Mockito.doReturn(iData0)
                .when(rInvestment)
                .save(Mockito.argThat(input -> {
                    return uData0.getUserId().equals(input.getUser().getUserId()) &&
                            pData0.getProductId().equals(input.getProduct().getProductId()) &&
                            iData0.getInvestingAmount().equals(input.getInvestingAmount());
                }));

        /* when */
        DIPostMyInvestments dIPostMyInvestments = DIPostMyInvestments.builder()
                .userId(userId)
                .productId(productId)
                .investingAmount(investingAmount)
                .build();
        DOPostMyInvestments dOPostMyInvestments = sPostMyInvestments.service(dIPostMyInvestments);

        /* then */
        assertNotNull(dOPostMyInvestments);
        assertEquals("200", dOPostMyInvestments.getStatus());
        assertEquals("성공", dOPostMyInvestments.getMessage());
        verify(rProduct, times(0)).save(any());
    }
    
    @Test
    @Timeout(value = 1000L, unit = TimeUnit.MILLISECONDS)
    @DisplayName("SPostMyInvestmentsTests: checkInvestingAmountGreaterThanMinimumLessThanTotal")
    public void checkInvestingAmountGreaterThanMinimumLessThanTotal() {
        /* given */
        Long userId = 1L;
        Long productId = 1L;
        Long totalInvestingAmount = 10000L;
        Long currentInvestingAmout = 5000L;
        Long investingAmount = totalInvestingAmount - currentInvestingAmout - SPostMyInvestments.MIN_INVESTING_AMOUNT + 1;

        EUser uData0 = EUser.builder()
                .userId(userId)
                .build();
                
        Mockito.doReturn(Optional.of(uData0))
                .when(rUser)
                .findById(userId);

        EProduct pData0 = EProduct.builder()
                .productId(productId)
                .totalInvestingAmount(totalInvestingAmount)
                .currentInvestingAmout(currentInvestingAmout)
                .productState(ProductStateType.OPEN.value())
                .startedAt(LocalDateTime.of(2000, 3, 3, 3, 3, 3, 3))
                .finishedAt(LocalDateTime.of(2099, 3, 3, 3, 3, 3, 3))
                .build();

        Mockito.doReturn(Optional.of(pData0))
                .when(rProduct)
                .findById(productId);

        EInvestment iData0 = EInvestment.builder()
                .user(uData0)
                .product(pData0)
                .investingAmount(investingAmount)
                .investedAt(LocalDateTime.now())
                .build();

        Mockito.doReturn(iData0)
                .when(rInvestment)
                .save(Mockito.argThat(input -> {
                    return uData0.getUserId().equals(input.getUser().getUserId()) &&
                            pData0.getProductId().equals(input.getProduct().getProductId()) &&
                            iData0.getInvestingAmount().equals(input.getInvestingAmount());
                }));

        /* when */
        DIPostMyInvestments dIPostMyInvestments = DIPostMyInvestments.builder()
                .userId(userId)
                .productId(productId)
                .investingAmount(investingAmount)
                .build();
        DOPostMyInvestments dOPostMyInvestments = sPostMyInvestments.service(dIPostMyInvestments);

        /* then */
        assertNotNull(dOPostMyInvestments);
        assertEquals("200", dOPostMyInvestments.getStatus());
        assertEquals("성공", dOPostMyInvestments.getMessage());
        verify(rProduct, times(1)).save(any());
        verify(rProduct, times(1)).save(Mockito.argThat(input -> 
            ProductStateType.CLOSED.value().equals(input.getProductState())
        ));
    }
    
    @Test
    @Timeout(value = 1000L, unit = TimeUnit.MILLISECONDS)
    @DisplayName("SPostMyInvestmentsTests: checkInvestingAmountEqualToTotal")
    public void checkInvestingAmountEqualToTotal() {
        /* given */
        Long userId = 1L;
        Long productId = 1L;
        Long totalInvestingAmount = 10000L;
        Long currentInvestingAmout = 5000L;
        Long investingAmount = totalInvestingAmount - currentInvestingAmout;

        EUser uData0 = EUser.builder()
                .userId(userId)
                .build();
                
        Mockito.doReturn(Optional.of(uData0))
                .when(rUser)
                .findById(userId);

        EProduct pData0 = EProduct.builder()
                .productId(productId)
                .totalInvestingAmount(totalInvestingAmount)
                .currentInvestingAmout(currentInvestingAmout)
                .productState(ProductStateType.OPEN.value())
                .startedAt(LocalDateTime.of(2000, 3, 3, 3, 3, 3, 3))
                .finishedAt(LocalDateTime.of(2099, 3, 3, 3, 3, 3, 3))
                .build();

        Mockito.doReturn(Optional.of(pData0))
                .when(rProduct)
                .findById(productId);

        EInvestment iData0 = EInvestment.builder()
                .user(uData0)
                .product(pData0)
                .investingAmount(investingAmount)
                .investedAt(LocalDateTime.now())
                .build();

        Mockito.doReturn(iData0)
                .when(rInvestment)
                .save(Mockito.argThat(input -> {
                    return uData0.getUserId().equals(input.getUser().getUserId()) &&
                            pData0.getProductId().equals(input.getProduct().getProductId()) &&
                            iData0.getInvestingAmount().equals(input.getInvestingAmount());
                }));

        /* when */
        DIPostMyInvestments dIPostMyInvestments = DIPostMyInvestments.builder()
                .userId(userId)
                .productId(productId)
                .investingAmount(investingAmount)
                .build();
        DOPostMyInvestments dOPostMyInvestments = sPostMyInvestments.service(dIPostMyInvestments);

        /* then */
        assertNotNull(dOPostMyInvestments);
        assertEquals("200", dOPostMyInvestments.getStatus());
        assertEquals("성공", dOPostMyInvestments.getMessage());
        verify(rProduct, times(1)).save(any());
        verify(rProduct, times(1)).save(Mockito.argThat(input -> 
            ProductStateType.CLOSED.value().equals(input.getProductState())
        ));
    }
    
    @Test
    @Timeout(value = 1000L, unit = TimeUnit.MILLISECONDS)
    @DisplayName("SPostMyInvestmentsTests: checkInvestingAmountGreaterThanTotal")
    public void checkInvestingAmountGreaterThanTotal() {
        /* given */
        Long userId = 1L;
        Long productId = 1L;
        Long totalInvestingAmount = 10000L;
        Long currentInvestingAmout = 5000L;
        Long investingAmount = totalInvestingAmount - currentInvestingAmout + 1;

        EUser uData0 = EUser.builder()
                .userId(userId)
                .build();
                
        Mockito.doReturn(Optional.of(uData0))
                .when(rUser)
                .findById(userId);

        EProduct pData0 = EProduct.builder()
                .productId(productId)
                .totalInvestingAmount(totalInvestingAmount)
                .currentInvestingAmout(currentInvestingAmout)
                .productState(ProductStateType.OPEN.value())
                .startedAt(LocalDateTime.of(2000, 3, 3, 3, 3, 3, 3))
                .finishedAt(LocalDateTime.of(2099, 3, 3, 3, 3, 3, 3))
                .build();

        Mockito.doReturn(Optional.of(pData0))
                .when(rProduct)
                .findById(productId);

        EInvestment iData0 = EInvestment.builder()
                .user(uData0)
                .product(pData0)
                .investingAmount(investingAmount)
                .investedAt(LocalDateTime.now())
                .build();

        Mockito.doReturn(iData0)
                .when(rInvestment)
                .save(Mockito.argThat(input -> {
                    return uData0.getUserId().equals(input.getUser().getUserId()) &&
                            pData0.getProductId().equals(input.getProduct().getProductId()) &&
                            iData0.getInvestingAmount().equals(input.getInvestingAmount());
                }));

        /* when */
        DIPostMyInvestments dIPostMyInvestments = DIPostMyInvestments.builder()
                .userId(userId)
                .productId(productId)
                .investingAmount(investingAmount)
                .build();
        DOPostMyInvestments dOPostMyInvestments = sPostMyInvestments.service(dIPostMyInvestments);

        /* then */
        assertNotNull(dOPostMyInvestments);
        assertEquals("400", dOPostMyInvestments.getStatus());
        assertEquals("투자금액이 너무 큽니다.", dOPostMyInvestments.getMessage());
        verify(rProduct, times(0)).save(any());
    }
    
    @Test
    @Timeout(value = 1000L, unit = TimeUnit.MILLISECONDS)
    @DisplayName("SPostMyInvestmentsTests: checkUser")
    public void checkUser() {
        /* given */
        Long userId = 1L;
        Long productId = 1L;
        Long investingAmount = 5000L;
                
        EUser uData0 = EUser.builder()
                .userId(userId)
                .build();

        // The user doesn't exist
        Mockito.doReturn(Optional.empty())
                .when(rUser)
                .findById(userId);

        EProduct pData0 = EProduct.builder()
                .productId(productId)
                .totalInvestingAmount(10000L)
                .productState(ProductStateType.OPEN.value())
                .startedAt(LocalDateTime.of(2000, 3, 3, 3, 3, 3, 3))
                .finishedAt(LocalDateTime.of(2099, 3, 3, 3, 3, 3, 3))
                .build();

        Mockito.doReturn(Optional.of(pData0))
                .when(rProduct)
                .findById(productId);

        EInvestment iData0 = EInvestment.builder()
                .user(null)
                .product(pData0)
                .investingAmount(investingAmount)
                .investedAt(LocalDateTime.now())
                .build();

        Mockito.doReturn(iData0)
                .when(rInvestment)
                .save(Mockito.argThat(input -> {
                    return uData0.getUserId().equals(input.getUser().getUserId()) &&
                            pData0.getProductId().equals(input.getProduct().getProductId()) &&
                            iData0.getInvestingAmount().equals(input.getInvestingAmount());
                }));
                
        /* when */
        DIPostMyInvestments dIPostMyInvestments = DIPostMyInvestments.builder()
                .userId(userId)
                .productId(productId)
                .investingAmount(investingAmount)
                .build();
        DOPostMyInvestments dOPostMyInvestments = sPostMyInvestments.service(dIPostMyInvestments);

        /* then */
        assertNotNull(dOPostMyInvestments);
        assertEquals("400", dOPostMyInvestments.getStatus());
        assertEquals("사용자를 찾을 수 없습니다.", dOPostMyInvestments.getMessage());
    }
    
    @Test
    @Timeout(value = 1000L, unit = TimeUnit.MILLISECONDS)
    @DisplayName("SPostMyInvestmentsTests: checkProduct")
    public void checkProduct() {
        /* given */
        Long userId = 1L;
        Long productId = 1L;
        Long investingAmount = 5000L;

        EUser uData0 = EUser.builder()
                .userId(userId)
                .build();
                
        Mockito.doReturn(Optional.of(uData0))
                .when(rUser)
                .findById(userId);
        {
            /* given */
            EProduct pData0 = EProduct.builder()
                    .productId(productId)
                    .totalInvestingAmount(10000L)
                    .productState(ProductStateType.OPEN.value())
                    .startedAt(LocalDateTime.of(2000, 3, 3, 3, 3, 3, 3))
                    .finishedAt(LocalDateTime.of(2099, 3, 3, 3, 3, 3, 3))
                    .build();

            // The product doesn't exist
            Mockito.doReturn(Optional.empty())
                    .when(rProduct)
                    .findById(productId);

            EInvestment iData0 = EInvestment.builder()
                    .user(uData0)
                    .product(pData0)
                    .investingAmount(investingAmount)
                    .investedAt(LocalDateTime.now())
                    .build();

            Mockito.doReturn(iData0)
                    .when(rInvestment)
                    .save(Mockito.argThat(input -> {
                        return uData0.getUserId().equals(input.getUser().getUserId()) &&
                                pData0.getProductId().equals(input.getProduct().getProductId()) &&
                                iData0.getInvestingAmount().equals(input.getInvestingAmount());
                    }));
                    
            /* when */
            DIPostMyInvestments dIPostMyInvestments = DIPostMyInvestments.builder()
                    .userId(userId)
                    .productId(productId)
                    .investingAmount(investingAmount)
                    .build();
            DOPostMyInvestments dOPostMyInvestments = sPostMyInvestments.service(dIPostMyInvestments);

            /* then */
            assertNotNull(dOPostMyInvestments);
            assertEquals("400", dOPostMyInvestments.getStatus());
            assertEquals("투자상품을 찾을 수 없습니다.", dOPostMyInvestments.getMessage());
        }
        {
            /* given */
            // The product with CLOSED state
            EProduct pData0 = EProduct.builder()
                    .productId(productId)
                    .totalInvestingAmount(10000L)
                    .startedAt(LocalDateTime.of(2000, 3, 3, 3, 3, 3, 3))
                    .finishedAt(LocalDateTime.of(2099, 3, 3, 3, 3, 3, 3))
                    .build();

            Mockito.doReturn(Optional.of(pData0))
                    .when(rProduct)
                    .findById(productId);

            EInvestment iData0 = EInvestment.builder()
                    .user(uData0)
                    .product(pData0)
                    .investingAmount(investingAmount)
                    .investedAt(LocalDateTime.now())
                    .build();

            Mockito.doReturn(iData0)
                    .when(rInvestment)
                    .save(Mockito.argThat(input -> {
                        return uData0.getUserId().equals(input.getUser().getUserId()) &&
                                pData0.getProductId().equals(input.getProduct().getProductId()) &&
                                iData0.getInvestingAmount().equals(input.getInvestingAmount());
                    }));
                    
            /* when */
            DIPostMyInvestments dIPostMyInvestments = DIPostMyInvestments.builder()
                    .userId(userId)
                    .productId(productId)
                    .investingAmount(investingAmount)
                    .build();
            DOPostMyInvestments dOPostMyInvestments = sPostMyInvestments.service(dIPostMyInvestments);

            /* then */
            assertNotNull(dOPostMyInvestments);
            assertEquals("400", dOPostMyInvestments.getStatus());
            assertEquals("마감된 투자상품입니다.", dOPostMyInvestments.getMessage());
        }
    }
}