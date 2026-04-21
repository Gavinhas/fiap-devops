package br.com.fiap.mural.campaign.dto;

import br.com.fiap.mural.campaign.Campaign;

import java.math.BigDecimal;

public record CampaignResponse(
        Long id,
        String title,
        String description,
        BigDecimal goalAmount,
        BigDecimal currentAmount
) {
    public static CampaignResponse from(Campaign c) {
        return new CampaignResponse(
                c.getId(),
                c.getTitle(),
                c.getDescription(),
                c.getGoalAmount(),
                c.getCurrentAmount()
        );
    }
}
