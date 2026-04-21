package br.com.fiap.mural.campaign.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CampaignRequest(
        @NotBlank @Size(max = 200) String title,
        @NotBlank @Size(max = 4000) String description,
        @NotNull @DecimalMin(value = "0.01", inclusive = true) BigDecimal goalAmount,
        @DecimalMin(value = "0", inclusive = true) BigDecimal currentAmount
) {
}
