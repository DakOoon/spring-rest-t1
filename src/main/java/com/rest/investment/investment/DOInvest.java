package com.rest.investment.investment;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rest.investment.util.DateTimeUtils;

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
public class DOInvest {
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeUtils.PATTERN)
    private LocalDateTime timestamp;
    private String status;
    private String message;
}
