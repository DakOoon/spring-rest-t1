package com.rest.investment.product;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rest.investment.util.DateTimeUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DOGetProducts {
    
    private Long productId;
    private String productTitle;
    private Long totalInvestingAmount;
    private Long currentInvestingAmout;
    private Long investorCount;
    private String productState;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeUtils.PATTERN)
    private LocalDateTime startedAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeUtils.PATTERN)
    private LocalDateTime finishedAt;
}
