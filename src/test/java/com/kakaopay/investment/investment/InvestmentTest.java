package com.kakaopay.investment.investment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Optional;

import com.kakaopay.investment.InvestmentApplicationTests;
import com.kakaopay.investment.product.EProduct;
import com.kakaopay.investment.product.RProdcut;
import com.kakaopay.investment.user.EUser;
import com.kakaopay.investment.user.RUser;
import com.kakaopay.investment.util.DateTimeUtils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class InvestmentTest extends InvestmentApplicationTests{
    
    @Autowired
    private RInvestment rInvestment;

    @Autowired
    private RUser rUser;    
    private EUser eUser;

    @Autowired
    private RProdcut rProdcut;
    private EProduct eProduct;

    @BeforeEach
    public void createRefer() {
        eUser = EUser.builder().build();
        rUser.save(eUser);

        eProduct = EProduct.builder().build();
        rProdcut.save(eProduct);
    }

    @AfterEach
    public void deleteRefer() {
        rProdcut.delete(eProduct);
        rUser.delete(eUser);
    }

    @Test
    public void entity() {
        // given
        System.out.println("InvestmentTest: entity");
        EInvestment eInvestment = EInvestment.builder()
                .user(eUser)
                .product(eProduct)
                .investingAmount(10L)
                .build();
        rInvestment.save(eInvestment);

        // when
        Optional<EInvestment> found = rInvestment.findById(eInvestment.getInvestmentId());

        // then
        assertFalse(found.isEmpty());
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
