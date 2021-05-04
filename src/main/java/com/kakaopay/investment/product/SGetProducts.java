package com.kakaopay.investment.product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SGetProducts {
    
    private final RProduct rProduct;

    @Transactional
    public List<DOGetProducts> service(DIGetProducts input) {
        List<DOGetProducts> output = new ArrayList<>();
        
        // mapping input
        LocalDateTime date = input.getDate() == null ?LocalDateTime.now() :input.getDate();
        
        // process
        List<EProduct> found = rProduct.findByDate(date);

        // mapping output
        for(EProduct value : found) {
            DOGetProducts data = DOGetProducts.builder()
                    .productId(value.getProductId())
                    .productTitle(value.getTitle())
                    .totalInvestingAmount(value.getTotalInvestingAmount())
                    .currentInvestingAmout(value.getCurrentInvestingAmout())
                    .investorCount(value.getInvestorCount())
                    .productState(value.getProductState())
                    .startedAt(value.getStartedAt())
                    .finishedAt(value.getFinishedAt())
                    .build();
            output.add(data);
        }

        return output;
    }
    
}
