package com.kakaopay.investment.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import com.kakaopay.investment.InvestmentApplicationTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserTests extends InvestmentApplicationTests {
    
    @Autowired
    private RUser rUser;

    @Test
    public void entityCRUD() {
        System.out.println("UserTests: entityCRUD");
        // given
        EUser origin = EUser.builder()
                .build();
        EUser saved = rUser.save(origin);
        assertNotNull(saved);

        // when
        Optional<EUser> found= rUser.findById(origin.getUserId());

        // then
        assertTrue(found.isPresent());
        EUser user = found.get();
        assertEquals(origin.getUserId(), user.getUserId());

        rUser.deleteById(origin.getUserId());
    }
}
