package com.kakaopay.investment.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.kakaopay.investment.InvestmentApplicationTests;
import com.kakaopay.investment.util.DateTimeUtils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductTests extends InvestmentApplicationTests {
    
    @Autowired
    private RProdcut rProdcut;

    @Test
    @Timeout(value = 1000L, unit = TimeUnit.MILLISECONDS)
    @DisplayName("ProductTests: entity")
    public void entity() {
        /* given */
        EProduct origin = EProduct.builder()
                .title("testTitle")
                .totalInvestingAmount(10L)
                .currentInvestingAmout(5L)
                .investorCount(3L)
                .productState(ProductStateType.OPEN.value())
                .startedAt(LocalDateTime.of(2000, 1, 1, 1, 1, 1, 1))
                .finishedAt(LocalDateTime.of(2000, 1, 1, 1, 1, 1, 1))
                .build();
                
        /* when */
        EProduct saved = rProdcut.save(origin);
        
        Optional<EProduct> found = rProdcut.findById(origin.getProductId());
        
        rProdcut.deleteById(origin.getProductId());
        Optional<EProduct> deleted = rProdcut.findById(origin.getProductId());
                
        /* then */
        assertNotNull(saved);
        
        assertTrue(found.isPresent());
        EProduct found0 = found.get();
        assertEquals(origin.getProductId(), found0.getProductId());
        assertEquals(origin.getTitle(), found0.getTitle());
        assertEquals(origin.getTotalInvestingAmount(), found0.getTotalInvestingAmount());
        assertEquals(0L, found0.getCurrentInvestingAmout());
        assertEquals(0L, found0.getInvestorCount());
        assertEquals(origin.getProductState(), found0.getProductState());
        assertEquals(DateTimeUtils.format(origin.getStartedAt())
                , DateTimeUtils.format(found0.getStartedAt()));
        assertEquals(DateTimeUtils.format(origin.getFinishedAt())
                , DateTimeUtils.format(found0.getFinishedAt()));
        
        assertNotNull(deleted);
        assertFalse(deleted.isPresent());
    }

    @Test
    @Timeout(value = 1000L, unit = TimeUnit.MILLISECONDS)
    @DisplayName("ProductTests: entityWithDefault")
    public void entityWithDefault() {
        /* given */
        EProduct origin = EProduct.builder()
                .build();
                
        /* when */
        EProduct saved = rProdcut.save(origin);
        
        Optional<EProduct> found = rProdcut.findById(origin.getProductId());
        
        rProdcut.deleteById(origin.getProductId());
        Optional<EProduct> deleted = rProdcut.findById(origin.getProductId());
                
        /* then */
        assertNotNull(saved);
        
        assertTrue(found.isPresent());
        EProduct found0 = found.get();
        assertEquals(origin.getProductId(), found0.getProductId());
        assertEquals(origin.getTitle(), found0.getTitle());
        assertEquals(0L, found0.getTotalInvestingAmount());
        assertEquals(0L, found0.getCurrentInvestingAmout());
        assertEquals(0L, found0.getInvestorCount());
        assertEquals(ProductStateType.CLOSED.value(), found0.getProductState());
        assertEquals(DateTimeUtils.format(origin.getStartedAt())
                , DateTimeUtils.format(found0.getStartedAt()));
        assertEquals(DateTimeUtils.format(origin.getFinishedAt())
                , DateTimeUtils.format(found0.getFinishedAt()));
                
        assertNotNull(deleted);
        assertFalse(deleted.isPresent());
    }

    public void findByDate() {
        // given
        // when
        // then
    }
}
