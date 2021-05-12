package com.rest.investment.investment;

import java.util.concurrent.TimeUnit;

import com.rest.investment.InvestmentApplicationTests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

public class SGetMyInvestmentsTests extends InvestmentApplicationTests {

    @MockBean
    private RInvestment rInvestment;

    @Autowired
    private SGetMyInvestments sGetMyInvestments;

    @Test
    @Timeout(value = 1000L, unit = TimeUnit.MILLISECONDS)
    @DisplayName("SGetMyInvestmentsTests: SGetMyInvestments")
    public void SGetMyInvestments() {
        /* given */
        
        /* when */

        /* then */

    }
}