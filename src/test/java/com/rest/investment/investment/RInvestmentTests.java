package com.rest.investment.investment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.rest.investment.InvestmentApplicationTests;
import com.rest.investment.product.EProduct;
import com.rest.investment.product.RProduct;
import com.rest.investment.user.EUser;
import com.rest.investment.user.RUser;
import com.rest.investment.util.DateTimeUtils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;

public class RInvestmentTests extends InvestmentApplicationTests {
    
    @Autowired
    private RInvestment rInvestment;

    @Autowired
    private RUser rUser;    
    private EUser eUser = EUser.builder().build();

    @Autowired
    private RProduct rProduct;
    private EProduct eProduct = EProduct.builder().build();

    @BeforeEach
    public void createRefer() {
        rUser.save(eUser);
        rProduct.save(eProduct);
    }

    @AfterEach
    public void deleteRefer() {
        rProduct.delete(eProduct);
        rUser.delete(eUser);
    }

    @Test
    @Timeout(value = 1000L, unit = TimeUnit.MILLISECONDS)
    @DisplayName("RInvestmentTests: entity")
    public void entity() {
        /* given */
        EInvestment origin = EInvestment.builder()
                .user(eUser)
                .product(eProduct)
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
}
