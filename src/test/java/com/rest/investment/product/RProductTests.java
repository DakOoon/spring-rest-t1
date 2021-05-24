package com.rest.investment.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.rest.investment.InvestmentApplicationTests;
import com.rest.investment.investment.EInvestment;
import com.rest.investment.investment.RInvestment;
import com.rest.investment.user.EUser;
import com.rest.investment.user.RUser;
import com.rest.investment.util.DateTimeUtils;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;

@TestInstance(Lifecycle.PER_CLASS)
public class RProductTests extends InvestmentApplicationTests {
    
    @Autowired
    private RInvestment rInvestment;

    @Autowired
    private RProduct rProduct;

    @Autowired
    private RUser rUser;

    @AfterAll
    public void afterAll() {
        rInvestment.deleteAll();
        rProduct.deleteAll();
        rUser.deleteAll();
    }

    @BeforeEach
    public void beforeEach() {
        rInvestment.deleteAll();
        rProduct.deleteAll();
        rUser.deleteAll();
    }

    @Test
    @Timeout(value = 1000L, unit = TimeUnit.MILLISECONDS)
    @DisplayName("entity")
    public void entity() {
        /* given */
        EProduct origin = EProduct.builder()
                .title("testTitle")
                .totalInvestingAmount(10L)
                .currentInvestingAmout(5L)
                .investorCount(3L)
                .productState(ProductStateType.OPEN.value())
                .startedAt(LocalDateTime.of(2000, 1, 1, 1, 1, 1))
                .finishedAt(LocalDateTime.of(2000, 1, 1, 1, 1, 1))
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
    @DisplayName("entityWithDefault")
    public void entityWithDefault() {
        /* given */
        EProduct origin = EProduct.builder()
                .build();
                
        /* when */
        EProduct saved = rProduct.save(origin);
        
        Optional<EProduct> found = rProduct.findById(origin.getProductId());
                
        /* then */
        assertNotNull(saved);
        
        assertTrue(found.isPresent());
        EProduct found0 = found.get();
        assertEquals(origin.getProductId(), found0.getProductId());
        assertEquals(null, found0.getTitle());
        assertEquals(0L, found0.getTotalInvestingAmount());
        assertEquals(0L, found0.getCurrentInvestingAmout());
        assertEquals(0L, found0.getInvestorCount());
        assertEquals(ProductStateType.CLOSED.value(), found0.getProductState());
        assertEquals(DateTimeUtils.format(LocalDateTime.now())
                , DateTimeUtils.format(found0.getStartedAt()));
        assertEquals(DateTimeUtils.format(LocalDateTime.now())
                , DateTimeUtils.format(found0.getFinishedAt()));
    }

    @Test
    @Timeout(value = 1000L, unit = TimeUnit.MILLISECONDS)
    @DisplayName("formula")
    public void formula() {
        /* given */
        Long totalInvestingAmount = 5000L;
        Long firstInvestingAmount = 2000L;
        Long secondInvestingAmount = 1000L;

        EUser uData0 = EUser.builder()
                .build();
        rUser.save(uData0);
        
        EProduct pData0 = EProduct.builder()
                .totalInvestingAmount(totalInvestingAmount)
                .build();
        rProduct.save(pData0);

        {
            /* when */
            Optional<EProduct> found = rProduct.findById(pData0.getProductId());
                        
            /* then */
            assertTrue(found.isPresent());
            EProduct found0 = found.get();
            assertEquals(pData0.getProductId(), found0.getProductId());
            assertEquals(totalInvestingAmount, found0.getTotalInvestingAmount());
            assertEquals(0L, found0.getCurrentInvestingAmout());
            assertEquals(0L, found0.getInvestorCount());
        }
        {
            /* when */
            EInvestment iData0 = EInvestment.builder()
                .user(uData0)
                .product(pData0)
                .investingAmount(firstInvestingAmount)
                .build();
            rInvestment.save(iData0);
            
            Optional<EProduct> found = rProduct.findById(pData0.getProductId());
                            
            /* then */
            assertTrue(found.isPresent());
            EProduct found0 = found.get();
            assertEquals(pData0.getProductId(), found0.getProductId());
            assertEquals(totalInvestingAmount, found0.getTotalInvestingAmount());
            assertEquals(firstInvestingAmount, found0.getCurrentInvestingAmout());
            assertEquals(1L, found0.getInvestorCount());
        }
        {
            /* when */
            EInvestment iData0 = EInvestment.builder()
                .user(uData0)
                .product(pData0)
                .investingAmount(secondInvestingAmount)
                .build();
            rInvestment.save(iData0);
            
            Optional<EProduct> found = rProduct.findById(pData0.getProductId());
                            
            /* then */
            assertTrue(found.isPresent());
            EProduct found0 = found.get();
            assertEquals(pData0.getProductId(), found0.getProductId());
            assertEquals(totalInvestingAmount, found0.getTotalInvestingAmount());
            assertEquals(firstInvestingAmount + secondInvestingAmount, found0.getCurrentInvestingAmout());
            assertEquals(2L, found0.getInvestorCount());
        }
    }

    @Test
    @Timeout(value = 1000L, unit = TimeUnit.MILLISECONDS)
    @DisplayName("findByDate")
    public void findByDate() {
        /* given */
        EProduct data0 = EProduct.builder()
                .title("p1")
                .startedAt(LocalDateTime.of(2000, 1, 1, 1, 1, 1))
                .finishedAt(LocalDateTime.of(2000, 2, 2, 2, 2, 2))
                .build();
        EProduct data1 = EProduct.builder()
                .title("p2")
                .startedAt(LocalDateTime.of(2000, 3, 3, 3, 3, 3))
                .finishedAt(LocalDateTime.of(2000, 4, 4, 4, 4, 4))
                .build();
        EProduct data2 = EProduct.builder()
                .title("p3")
                .startedAt(LocalDateTime.of(2000, 1, 1, 1, 1, 1))
                .finishedAt(LocalDateTime.of(2000, 4, 4, 4, 4, 4))
                .build();
        rProduct.saveAll(Arrays.asList(data0, data1, data2));

        {
            /* when */
            List<EProduct> found = rProduct.findByDate(LocalDateTime.of(2000, 1, 1, 1, 1, 1));

            /* then */
            assertEquals(2, found.size());
            assertEquals(data0.getTitle(), found.get(0).getTitle());
            assertEquals(data2.getTitle(), found.get(1).getTitle());
        }
        {
            /* when */
            List<EProduct> found = rProduct.findByDate(LocalDateTime.of(2000, 3, 3, 3, 3, 3));

            /* then */
            assertEquals(2, found.size());
            assertEquals(data1.getTitle(), found.get(0).getTitle());
            assertEquals(data2.getTitle(), found.get(1).getTitle());
        }
    }
}
