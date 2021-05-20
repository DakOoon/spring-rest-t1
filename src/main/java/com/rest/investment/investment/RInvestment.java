package com.rest.investment.investment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RInvestment extends JpaRepository<EInvestment, Long> {
    
    @Query(value = "SELECT ei FROM EInvestment ei WHERE ei.user.userId = :userId")
    List<EInvestment> findByUserId(@Param("userId") Long userId);
}
