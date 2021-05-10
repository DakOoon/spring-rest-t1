package com.rest.investment.product;

import java.time.LocalDateTime;

import com.rest.investment.util.DateTimeUtils;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DIGetProducts {
    
    @DateTimeFormat(pattern = DateTimeUtils.PATTERN)
    private LocalDateTime date;
}
