package com.kakaopay.investment.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DOGetProducts {
    
    private Long productId;
    private String productTitle;
    private Long totalInvestingAmount;
    private Long currentInvestingAmout;
    private Long investorCount;
    private String productState;
    private String startedAt;
    private String finishedAt;
}
