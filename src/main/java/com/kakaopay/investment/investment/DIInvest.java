package com.kakaopay.investment.investment;

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
public class DIInvest {
    
    private Long userId;
    private Long productId;
    private Long investingAmount;
}
