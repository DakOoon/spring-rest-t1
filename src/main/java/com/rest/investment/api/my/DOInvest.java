package com.rest.investment.api.my;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rest.investment.util.DateTimeUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DOInvest {
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeUtils.PATTERN)
    private LocalDateTime timestamp;
    private String status;
    private String message;
}
