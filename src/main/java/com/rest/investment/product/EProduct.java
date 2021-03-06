package com.rest.investment.product;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PRODUCT")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class EProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "title")
    private String title;

    @Column(name = "total_investing_amount", nullable = false, columnDefinition = "long default 0")
    @Builder.Default
    private Long totalInvestingAmount = 0L;

    @Formula("(SELECT NVL(SUM(inv.investing_amount), 0) FROM INVESTMENT inv WHERE inv.product_id = product_id)")
    @Builder.Default
    private Long currentInvestingAmout = 0L;

    @Formula("(SELECT NVL(COUNT(1), 0) FROM INVESTMENT inv WHERE inv.product_id = product_id)")
    @Builder.Default
    private Long investorCount = 0L;

    @Column(name = "product_state", nullable = false)
    @Builder.Default
    private String productState = ProductStateType.CLOSED.value();

    @Column(name = "started_at", nullable = false)
    @Builder.Default
    private LocalDateTime startedAt = LocalDateTime.now();

    @Column(name = "finished_at", nullable = false)
    @Builder.Default
    private LocalDateTime finishedAt = LocalDateTime.now();

    public void update(String productState) {
        this.productState = productState;
    }
}