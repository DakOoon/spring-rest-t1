package com.rest.investment.product;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RProduct extends JpaRepository<EProduct, Long> {

    @Query(value = "SELECT ep FROM EProduct ep WHERE :date BETWEEN ep.startedAt AND ep.finishedAt")
    List<EProduct> findByDate(@Param("date") LocalDateTime date);
}