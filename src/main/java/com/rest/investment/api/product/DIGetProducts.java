package com.rest.investment.api.product;

import java.time.LocalDateTime;

import com.rest.investment.util.DateTimeUtils;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DIGetProducts {
    
    @DateTimeFormat(pattern = DateTimeUtils.PATTERN)
    private LocalDateTime date;
}
