package com.rest.investment.investment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.rest.investment.InvestmentApplicationTests;
import com.rest.investment.product.EProduct;
import com.rest.investment.product.RProduct;
import com.rest.investment.user.EUser;
import com.rest.investment.user.RUser;
import com.rest.investment.util.DateTimeUtils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;

public class RInvestmentTests extends InvestmentApplicationTests {
    
    @Autowired
    private RInvestment rInvestment;

    @Autowired
    private RUser rUser;

    @Autowired
    private RProduct rProduct;

    @Test
    @Timeout(value = 1000L, unit = TimeUnit.MILLISECONDS)
    @DisplayName("RInvestmentTests: entity")
    public void entity() {
        /* given */
        EUser uData0 = EUser.builder()
                .build();
        rUser.save(uData0);

        EProduct pData0 = EProduct.builder()
                .build();
        rProduct.save(pData0);

        EInvestment origin = EInvestment.builder()
                .user(uData0)
                .product(pData0)
                .investingAmount(10L)
                .build();
                
        /* when */
        EInvestment saved = rInvestment.save(origin);
        
        Optional<EInvestment> found = rInvestment.findById(origin.getInvestmentId());
        
        rInvestment.deleteById(origin.getInvestmentId());
        Optional<EInvestment> deleted = rInvestment.findById(origin.getInvestmentId());

        /* then */
        assertNotNull(saved);

        assertTrue(found.isPresent());
        EInvestment found0 = found.get();
        assertEquals(origin.getInvestmentId(), found0.getInvestmentId());
        assertEquals(origin.getUser().getUserId(), found0.getUser().getUserId());
        assertEquals(origin.getProduct().getProductId(), found0.getProduct().getProductId());
        assertEquals(origin.getInvestingAmount(), found0.getInvestingAmount());
        assertEquals(DateTimeUtils.format(origin.getInvestedAt())
                , DateTimeUtils.format(found0.getInvestedAt()));
        
        assertNotNull(deleted);
        assertFalse(deleted.isPresent());
    }

    @Test
    @Timeout(value = 1000L, unit = TimeUnit.MILLISECONDS)
    @DisplayName("RInvestmentTests: findByUserId")
    public void findByUserId() {
        /* given */
        EProduct pData0 = EProduct.builder()
                .build();
        rProduct.save(pData0);

        EUser uData0 = EUser.builder()
                .build();
        EUser uData1 = EUser.builder()
                .build();
        EUser uData2 = EUser.builder()
                .build();
        rUser.saveAll(Arrays.asList(uData0, uData1, uData2));
        
        EInvestment iData0 = EInvestment.builder()
                .user(uData0)
                .product(pData0)
                .investingAmount(10L)
                .build();
        EInvestment iData1 = EInvestment.builder()
                .user(uData1)
                .product(pData0)
                .investingAmount(10L)
                .build();
        EInvestment iData2 = EInvestment.builder()
                .user(uData0)
                .product(pData0)
                .investingAmount(10L)
                .build();
        EInvestment iData3 = EInvestment.builder()
                .user(uData1)
                .product(pData0)
                .investingAmount(10L)
                .build();
        EInvestment iData4 = EInvestment.builder()
                .user(uData1)
                .product(pData0)
                .investingAmount(10L)
                .build();
        EInvestment iData5 = EInvestment.builder()
                .user(uData2)
                .product(pData0)
                .investingAmount(10L)
                .build();
        rInvestment.saveAll(Arrays.asList(iData0, iData1, iData2, iData3, iData4, iData5));
        
        {
            /* when */
            Long userId = uData0.getUserId();
            List<EInvestment> found = rInvestment.findByUserId(userId);

            /* then */
            assertNotNull(found);
            assertEquals(2, found.size());
            assertEquals(iData0.getInvestmentId(), found.get(0).getInvestmentId());
            assertEquals(userId, found.get(0).getUser().getUserId());
            assertEquals(iData2.getInvestmentId(), found.get(1).getInvestmentId());
            assertEquals(userId, found.get(1).getUser().getUserId());
        }
        {
            /* when */
            Long userId = uData1.getUserId();
            List<EInvestment> found = rInvestment.findByUserId(userId);
            
            /* then */
            assertNotNull(found);
            assertEquals(3, found.size());
            assertEquals(iData1.getInvestmentId(), found.get(0).getInvestmentId());
            assertEquals(userId, found.get(0).getUser().getUserId());
            assertEquals(iData3.getInvestmentId(), found.get(1).getInvestmentId());
            assertEquals(userId, found.get(1).getUser().getUserId());
            assertEquals(iData4.getInvestmentId(), found.get(2).getInvestmentId());
            assertEquals(userId, found.get(2).getUser().getUserId());
        }
    }
}
