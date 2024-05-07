package com.example.ibteam7.dto;

import com.example.ibteam7.entity.PromoCodesEntity;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PromoValidationResponseDto {

    private PromoCodesEntity promoCodesEntity;
    private String message;
}
