package com.rest.investment.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.rest.investment.InvestmentApplicationTests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;

@TestInstance(Lifecycle.PER_CLASS)
public class RUserTests extends InvestmentApplicationTests {
    
    @Autowired
    private RUser rUser;

    @AfterAll
    public void afterAll() {
        rUser.deleteAll();
    }

    @BeforeEach
    public void beforeEach() {
        rUser.deleteAll();
    }

    @Test
    @Timeout(value = 1000L, unit = TimeUnit.MILLISECONDS)
    @DisplayName("entity")
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
    }
}
