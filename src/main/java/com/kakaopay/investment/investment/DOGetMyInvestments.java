package com.kakaopay.investment.investment;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kakaopay.investment.util.DateTimeUtils;

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
public class DOGetMyInvestments {
    
    private Long productId;
    private String productTitle;
    private Long totalInvestingAmount;
    private Long InvestingAmount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeUtils.PATTERN)
    private LocalDateTime investedAt;
}
