package com.rest.investment.investment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DIInvest {
    
    private Long userId;
    private Long productId;
    private Long investingAmount;
}
