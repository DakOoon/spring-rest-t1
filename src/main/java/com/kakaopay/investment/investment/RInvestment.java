package com.kakaopay.investment.investment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RInvestment extends JpaRepository<EInvestment, Long> {
    
    @Query(value = "SELECT * FROM INVESTMENT WHERE user_id = :user_id", nativeQuery = true)
    List<EInvestment> findByUserId(@Param("user_id") Long userId);

    @Query(value = "SELECT * FROM INVESTMENT WHERE user_id = :user_id", nativeQuery = true)
    List<EInvestment> invest(@Param("user_id") Long userId);
}
