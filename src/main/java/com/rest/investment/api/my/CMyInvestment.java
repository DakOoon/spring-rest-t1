package com.rest.investment.api.my;

import java.util.List;

import com.rest.investment.investment.SGetMyInvestments;
import com.rest.investment.investment.SPostMyInvestments;

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
    private final SPostMyInvestments sPostMyInvestments;

    @GetMapping("api/investment/my/investments")
    public List<DOGetMyInvestments> getMyInvestments(@RequestHeader(value = "X-USER-ID") Long userId) {
        DIGetMyInvestments input = DIGetMyInvestments.builder()
                .userId(userId)
                .build();

        List<DOGetMyInvestments> output = sGetMyInvestments.service(input);

        return output;
    }

    @PostMapping("api/investment/my/investments")
    public DOPostMyInvestments postMyInvestments(@RequestHeader(value = "X-USER-ID") Long userId, @RequestBody DIPostMyInvestments input) {
        input.setUserId(userId);
        
        DOPostMyInvestments output = sPostMyInvestments.service(input);
        
        return output;
    }
    
}