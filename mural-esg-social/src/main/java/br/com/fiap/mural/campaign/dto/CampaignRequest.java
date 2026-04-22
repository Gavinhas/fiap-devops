package br.com.fiap.mural.campaign.dto;

import br.com.fiap.mural.campaign.CampaignType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CampaignRequest(
        @NotBlank @Size(max = 200) String title,
        @NotBlank @Size(max = 4000) String description,
        CampaignType campaignType,
        BigDecimal goalAmount,
        @DecimalMin(value = "0", inclusive = true) BigDecimal currentAmount
) {
}
