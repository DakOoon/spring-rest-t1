package com.kakaopay.investment.product;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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

    @Column(name = "current_investing_amount", nullable = false, columnDefinition = "long default 0")
    @Builder.Default
    private Long currentInvestingAmout = 0L;

    @Column(name = "investor_count", nullable = false, columnDefinition = "long default 0")
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

    public void update(Long currencurrentInvestingAmount, Long investorCount) {
        this.currentInvestingAmout = currencurrentInvestingAmount;
        this.investorCount = investorCount;
    }

    public void update(String productState) {
        this.productState = productState;
    }
}