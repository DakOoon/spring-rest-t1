package com.kakaopay.investment.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Optional;

import com.kakaopay.investment.InvestmentApplicationTests;
import com.kakaopay.investment.util.DateTimeUtils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductTests extends InvestmentApplicationTests{
    
    @Autowired
    private RProdcut rProdcut;

    @Test
    public void entity() {
        // given
        System.out.println("ProductTests: entity");
        EProduct eProduct = EProduct.builder()
                .title("testTitle")
                .totalInvestingAmount(10L)
                .currentInvestingAmout(5L)
                .investorCount(3L)
                .productState(ProductStateType.OPEN.value())
                .startedAt(LocalDateTime.of(2000, 1, 1, 1, 1, 1, 1))
                .finishedAt(LocalDateTime.of(2000, 1, 1, 1, 1, 1, 1))
                .build();
        EProduct saved = rProdcut.save(eProduct);
        assertNotNull(saved);

        // when
        Optional<EProduct> found = rProdcut.findById(eProduct.getProductId());

        // then
        assertTrue(found.isPresent());
        EProduct product = found.get();
        assertEquals(eProduct.getProductId(), product.getProductId());
        assertEquals(eProduct.getTitle(), product.getTitle());
        assertEquals(eProduct.getTotalInvestingAmount(), product.getTotalInvestingAmount());
        assertEquals(eProduct.getCurrentInvestingAmout(), product.getCurrentInvestingAmout());
        assertEquals(eProduct.getInvestorCount(), product.getInvestorCount());
        assertEquals(eProduct.getProductState(), product.getProductState());
        assertEquals(DateTimeUtils.format(eProduct.getStartedAt())
                , DateTimeUtils.format(product.getStartedAt()));
        assertEquals(DateTimeUtils.format(eProduct.getFinishedAt())
                , DateTimeUtils.format(product.getFinishedAt()));

        rProdcut.deleteById(eProduct.getProductId());
    }

    @Test
    public void entityWithDefault() {
        // given
        System.out.println("ProductTests: entityWithDefault");
        EProduct eProduct = EProduct.builder()
                .build();
        EProduct saved = rProdcut.save(eProduct);
        assertNotNull(saved);
        
        // when
        Optional<EProduct> found = rProdcut.findById(eProduct.getProductId());

        // then
        assertTrue(found.isPresent());
        EProduct product = found.get();
        assertEquals(eProduct.getProductId(), product.getProductId());
        assertEquals(eProduct.getTitle(), product.getTitle());
        assertEquals(0L, product.getTotalInvestingAmount());
        assertEquals(0L, product.getCurrentInvestingAmout());
        assertEquals(0L, product.getInvestorCount());
        assertEquals(ProductStateType.CLOSED.value(), product.getProductState());
        assertEquals(DateTimeUtils.format(eProduct.getStartedAt())
                , DateTimeUtils.format(product.getStartedAt()));
        assertEquals(DateTimeUtils.format(eProduct.getFinishedAt())
                , DateTimeUtils.format(product.getFinishedAt()));
                
        rProdcut.deleteById(eProduct.getProductId());
    }

    public void findByDate() {
        // given
        // when
        // then
    }
}
