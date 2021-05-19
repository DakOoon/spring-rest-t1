package com.rest.investment.api.my;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DIPostMyInvestments {
    
    private Long userId;
    private Long productId;
    private Long investingAmount;
}
