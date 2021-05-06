package com.rest.investment.investment;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.rest.investment.product.EProduct;
import com.rest.investment.user.EUser;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "INVESTMENT")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class EInvestment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "investment_id")
    private Long investmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private EUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)
    private EProduct product;

    @Column(name = "investing_amount", nullable = false)
    private Long investingAmount;

    @Column(name = "invested_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime investedAt;
}
