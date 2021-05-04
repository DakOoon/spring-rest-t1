package com.kakaopay.investment.product;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RProdcut extends JpaRepository<EProduct, Long> {

    @Query(value = "SELECT * FROM PRODUCT WHERE started_at <= :date AND finished_at >= :date", nativeQuery = true)
    List<EProduct> findByDate(@Param("date") LocalDateTime date);
}