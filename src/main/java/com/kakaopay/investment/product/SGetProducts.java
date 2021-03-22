package com.kakaopay.investment.product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.kakaopay.investment.util.DateTimeUtils;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SGetProducts {
    
    private final RProdcut rProduct;

    @Transactional
    public List<DOGetProducts> service(DIGetProducts input) {
        List<DOGetProducts> output = new ArrayList<>();
        
        // mapping input
        String date = input.getDate() == null ?DateTimeUtils.format(LocalDateTime.now()) :input.getDate();
        
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
                    .startedAt(DateTimeUtils.format(value.getStartedAt()))
                    .finishedAt(DateTimeUtils.format(value.getFinishedAt()))
                    .build();
            output.add(data);
        }

        return output;
    }
    
}
