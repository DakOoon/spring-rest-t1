package com.kakaopay.investment.api.my;

import java.util.List;

import com.kakaopay.investment.investment.DIGetMyInvestments;
import com.kakaopay.investment.investment.DIInvest;
import com.kakaopay.investment.investment.DOGetMyInvestments;
import com.kakaopay.investment.investment.DOInvest;
import com.kakaopay.investment.investment.SGetMyInvestments;
import com.kakaopay.investment.investment.SInvest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CMyInvestment {
    
    private final SGetMyInvestments sGetMyInvestments;
    private final SInvest sInvest;

    @GetMapping("api/investment/my/investments")
    public List<DOGetMyInvestments> getMyInvestments(@RequestHeader(value = "X-USER-ID") Long userId) {
        DIGetMyInvestments input = DIGetMyInvestments.builder()
                .userId(userId)
                .build();

        List<DOGetMyInvestments> output = sGetMyInvestments.service(input);

        return output;
    }

    @PostMapping("api/investment/my/investments")
    public DOInvest invest(@RequestHeader(value = "X-USER-ID") Long userId, @RequestBody DIInvest input) {
        input.setUserId(userId);
        return sInvest.service(input);
    }
    
}