package com.rest.investment.investment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.rest.investment.InvestmentApplicationTests;
import com.rest.investment.api.my.DIGetMyInvestments;
import com.rest.investment.api.my.DOGetMyInvestments;
import com.rest.investment.product.EProduct;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

public class SGetMyInvestmentsTests extends InvestmentApplicationTests {

    @MockBean
    private RInvestment rInvestment;

    @Autowired
    private SGetMyInvestments sGetMyInvestments;

    @Test
    @Timeout(value = 1000L, unit = TimeUnit.MILLISECONDS)
    @DisplayName("SGetMyInvestmentsTests: success")
    public void success() {
        /* given */
        Long userId = 33L;

        EProduct pData0 = EProduct.builder()
                .build();
        EInvestment iData0 = EInvestment.builder()
                .product(pData0)
                .build();
        EProduct pData1 = EProduct.builder()
                .build();
        EInvestment iData1 = EInvestment.builder()
                .product(pData1)
                .build();
        
        List<EInvestment> data = new ArrayList<>();
        data.add(iData0);
        data.add(iData1);

        Mockito.doReturn(data)
                .when(rInvestment)
                .findByUserId(userId);
        
        /* when */
        DIGetMyInvestments dIGetMyInvestments = DIGetMyInvestments.builder()
                .userId(userId)
                .build();
        List<DOGetMyInvestments> dOGetMyInvestments = sGetMyInvestments.service(dIGetMyInvestments);

        /* then */
        assertNotNull(dOGetMyInvestments);
        assertEquals(2, dOGetMyInvestments.size());
        assertEquals(iData0.getInvestmentId(), dOGetMyInvestments.get(0).getInvestmentId());
        assertEquals(pData0.getProductId(), dOGetMyInvestments.get(0).getProductId());
        assertEquals(iData1.getInvestmentId(), dOGetMyInvestments.get(1).getInvestmentId());
        assertEquals(pData1.getProductId(), dOGetMyInvestments.get(1).getProductId());
    }

    @Test
    @Timeout(value = 1000L, unit = TimeUnit.MILLISECONDS)
    @DisplayName("SGetMyInvestmentsTests: withoutUserId")
    public void withoutUserId() {
        /* given */
        Long userId = 33L;

        EInvestment data0 = EInvestment.builder()
                .build();
        EInvestment data1 = EInvestment.builder()
                .build();
        
        List<EInvestment> data = new ArrayList<>();
        data.add(data0);
        data.add(data1);

        Mockito.doReturn(data)
                .when(rInvestment)
                .findByUserId(userId);
        
        /* when */
        DIGetMyInvestments dIGetMyInvestments = DIGetMyInvestments.builder()
                .build();
        List<DOGetMyInvestments> dOGetMyInvestments = sGetMyInvestments.service(dIGetMyInvestments);

        /* then */
        assertNotNull(dOGetMyInvestments);
        assertEquals(0, dOGetMyInvestments.size());
    }
}