package com.rest.investment.investment;

import java.time.LocalDateTime;
import java.util.Optional;

import com.rest.investment.api.my.DIPostMyInvestments;
import com.rest.investment.api.my.DOPostMyInvestments;
import com.rest.investment.product.EProduct;
import com.rest.investment.product.ProductStateType;
import com.rest.investment.product.RProduct;
import com.rest.investment.user.EUser;
import com.rest.investment.user.RUser;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SPostMyInvestments {

    @Getter
    public class InvestmentException extends Exception {
        private static final long serialVersionUID = -8846685628988675724L;
        
        private String status = "400";
        private String description;

        public InvestmentException(String description) {
            this.description = description;
        }
    }

    public static final long MIN_INVESTING_AMOUNT = 1000;

    private final RInvestment rInvestment;
    private final RProduct rProduct;
    private final RUser rUser;
    
    @Transactional
    public DOPostMyInvestments service(DIPostMyInvestments input) {
        DOPostMyInvestments output = DOPostMyInvestments.builder().build();

        try {
            // mapping input
            Long userId = input.getUserId()==null ?-1 :input.getUserId();
            Long productId = input.getProductId()==null ?-1 :input.getProductId();
            Long invAmt = input.getInvestingAmount()==null ?-1 :input.getInvestingAmount();

            if(userId < 0 || productId < 0 || invAmt < 0) {
                throw new InvestmentException("잘못된 입력값입니다.");
            }
            if(invAmt < MIN_INVESTING_AMOUNT) {
                throw new InvestmentException("최소 투자금액은 "+MIN_INVESTING_AMOUNT+"원 입니다.");
            }
    
            // process
            // read USER
            Optional<EUser> user = rUser.findById(userId);
            if(!user.isPresent()) {
                throw new InvestmentException("사용자를 찾을 수 없습니다.");
            }
    
            // read PRODUCT
            Optional<EProduct> product = rProduct.findById(input.getProductId());
            if(!product.isPresent()) {
                throw new InvestmentException("투자상품을 찾을 수 없습니다.");
            }
            
            EProduct prdValue = product.get();
            if(!ProductStateType.OPEN.equals(ProductStateType.valueOf(prdValue.getProductState()))) {
                throw new InvestmentException("마감된 투자상품입니다.");
            }

            // update PRODUCT
            Long curAmt = prdValue.getCurrentInvestingAmout();
            Long totAmt = prdValue.getTotalInvestingAmount();
            if(totAmt >= curAmt + invAmt + MIN_INVESTING_AMOUNT) {
                // do nothing
                
            } else if(totAmt < curAmt + invAmt) {
                throw new InvestmentException("투자금액이 너무 큽니다.");
            
            } else if(totAmt < curAmt + invAmt + MIN_INVESTING_AMOUNT) {
                prdValue.update(ProductStateType.CLOSED.value());
                rProduct.save(prdValue);
            }
            
            // insert INVESTMENT
            EInvestment investment = EInvestment.builder()
                    .user(user.get())
                    .product(product.get())
                    .investingAmount(invAmt)
                    .build();
            EInvestment result = rInvestment.save(investment);
    
            // mapping output
            output.setTimestamp(result.getInvestedAt());
            output.setStatus("200");
            output.setMessage("성공");

        } catch(InvestmentException e) {
            output.setTimestamp(LocalDateTime.now());
            output.setStatus(e.getStatus());
            output.setMessage(e.getDescription());
        }

        return output;
    }
}
