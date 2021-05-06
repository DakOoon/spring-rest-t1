package com.rest.investment.product;

import java.time.LocalDateTime;

import com.rest.investment.util.DateTimeUtils;

import org.springframework.format.annotation.DateTimeFormat;

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
public class DIGetProducts {
    
    @DateTimeFormat(pattern = DateTimeUtils.PATTERN)
    private LocalDateTime date;
}
