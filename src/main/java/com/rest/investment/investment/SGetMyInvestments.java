package com.rest.investment.investment;

import java.util.ArrayList;
import java.util.List;

import com.rest.investment.api.my.DIGetMyInvestments;
import com.rest.investment.api.my.DOGetMyInvestments;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SGetMyInvestments {

    private final RInvestment rInvestment;
    
    @Transactional(readOnly = true)
    public List<DOGetMyInvestments> service(final DIGetMyInvestments input) {
        // mapping input
        Long userId = input.getUserId()==null ?null :input.getUserId();

        // process
        List<EInvestment> founds = rInvestment.findByUserId(userId);
        
        // mapping output
        List<DOGetMyInvestments> output = new ArrayList<>();
        for(EInvestment value : founds) {
            DOGetMyInvestments data = DOGetMyInvestments.builder()
                    .investmentId(value.getInvestmentId())
                    .productId(value.getProduct().getProductId())
                    .productTitle(value.getProduct().getTitle())
                    .totalInvestingAmount(value.getProduct().getTotalInvestingAmount())
                    .InvestingAmount(value.getInvestingAmount())
                    .investedAt(value.getInvestedAt())
                    .build();
            output.add(data);
        }

        return output;
    }
}
