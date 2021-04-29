package com.kakaopay.investment.investment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.kakaopay.investment.InvestmentApplicationTests;
import com.kakaopay.investment.product.EProduct;
import com.kakaopay.investment.product.RProdcut;
import com.kakaopay.investment.user.EUser;
import com.kakaopay.investment.user.RUser;
import com.kakaopay.investment.util.DateTimeUtils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;

public class InvestmentTests extends InvestmentApplicationTests {
    
    @Autowired
    private RInvestment rInvestment;

    @Autowired
    private RUser rUser;    
    private EUser eUser = EUser.builder().build();

    @Autowired
    private RProdcut rProdcut;
    private EProduct eProduct = EProduct.builder().build();

    @BeforeEach
    public void createRefer() {
        rUser.save(eUser);
        rProdcut.save(eProduct);
    }

    @AfterEach
    public void deleteRefer() {
        rProdcut.delete(eProduct);
        rUser.delete(eUser);
    }

    @Test
    @Timeout(value = 1000L, unit = TimeUnit.MILLISECONDS)
    @DisplayName("InvestmentTests: entity")
    public void entity() {
        /* given */
        EUser origin = EUser.builder()
                .build();

        /* when */
        EUser saved = rUser.save(origin);
        
        Optional<EUser> found = rUser.findById(origin.getUserId());
        
        rUser.deleteById(origin.getUserId());
        Optional<EUser> deleted = rUser.findById(origin.getUserId());

        /* then */
        assertNotNull(saved);
        
        assertTrue(found.isPresent());
        EUser found0 = found.get();
        assertEquals(origin.getUserId(), found0.getUserId());
        
        assertNotNull(deleted);
        assertFalse(deleted.isPresent());



        /* given */
        EInvestment eInvestment = EInvestment.builder()
                .user(eUser)
                .product(eProduct)
                .investingAmount(10L)
                .build();
        EInvestment saved = rInvestment.save(eInvestment);
        assertNotNull(saved);

        /* when */
        Optional<EInvestment> found = rInvestment.findById(eInvestment.getInvestmentId());

        /* then */
        assertTrue(found.isPresent());
        EInvestment investment = found.get();
        assertEquals(eInvestment.getInvestmentId(), investment.getInvestmentId());
        assertEquals(eInvestment.getUser().getUserId(), investment.getUser().getUserId());
        assertEquals(eInvestment.getProduct().getProductId(), investment.getProduct().getProductId());
        assertEquals(eInvestment.getInvestingAmount(), investment.getInvestingAmount());
        assertEquals(DateTimeUtils.format(eInvestment.getInvestedAt())
                , DateTimeUtils.format(investment.getInvestedAt()));
        
        rInvestment.deleteById(eInvestment.getInvestmentId());
    }
}
