package com.rest.investment.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.rest.investment.InvestmentApplicationTests;
import com.rest.investment.util.DateTimeUtils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;

public class RProductTests extends InvestmentApplicationTests {
    
    @Autowired
    private RProduct rProduct;

    @Test
    @Timeout(value = 1000L, unit = TimeUnit.MILLISECONDS)
    @DisplayName("RProductTests: entity")
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
        EProduct saved = rProduct.save(origin);
        
        Optional<EProduct> found = rProduct.findById(origin.getProductId());
        
        rProduct.deleteById(origin.getProductId());
        Optional<EProduct> deleted = rProduct.findById(origin.getProductId());
                
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
    @DisplayName("RProductTests: entityWithDefault")
    public void entityWithDefault() {
        /* given */
        EProduct origin = EProduct.builder()
                .build();
                
        /* when */
        EProduct saved = rProduct.save(origin);
        
        Optional<EProduct> found = rProduct.findById(origin.getProductId());
        
        rProduct.deleteById(origin.getProductId());
        Optional<EProduct> deleted = rProduct.findById(origin.getProductId());
                
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

    @Test
    @Timeout(value = 1000L, unit = TimeUnit.MILLISECONDS)
    @DisplayName("RProductTests: findByDate")
    public void findByDate() {
        /* given */
        List<EProduct> data = new ArrayList<>();
        EProduct data0 = EProduct.builder()
                .title("p1")
                .startedAt(LocalDateTime.of(2000, 1, 1, 1, 1, 1, 1))
                .finishedAt(LocalDateTime.of(2000, 2, 2, 2, 2, 2, 2))
                .build();
        EProduct data1 = EProduct.builder()
                .title("p2")
                .startedAt(LocalDateTime.of(2000, 3, 3, 3, 3, 3, 3))
                .finishedAt(LocalDateTime.of(2000, 4, 4, 4, 4, 4, 4))
                .build();
        EProduct data2 = EProduct.builder()
                .title("p3")
                .startedAt(LocalDateTime.of(2000, 1, 1, 1, 1, 1, 1))
                .finishedAt(LocalDateTime.of(2000, 4, 4, 4, 4, 4, 4))
                .build();
        data.add(data0);
        data.add(data1);
        data.add(data2);
        rProduct.saveAll(data);

        /* when */
        List<EProduct> found0 = rProduct.findByDate(LocalDateTime.of(2000, 1, 1, 1, 1, 1, 1));

        /* then */
        assertEquals(2, found0.size());
        assertEquals(data0.getTitle(), found0.get(0).getTitle());
        assertEquals(data2.getTitle(), found0.get(1).getTitle());
        
        /* when */
        List<EProduct> found1 = rProduct.findByDate(LocalDateTime.of(2000, 3, 3, 3, 3, 3, 3));

        /* then */
        assertEquals(2, found1.size());
        assertEquals(data1.getTitle(), found1.get(0).getTitle());
        assertEquals(data2.getTitle(), found1.get(1).getTitle());

        rProduct.deleteAll();
    }
}
