package br.com.fiap.mural.campaign.dto;

import br.com.fiap.mural.campaign.Campaign;
import br.com.fiap.mural.campaign.CampaignType;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record CampaignResponse(
        Long id,
        String title,
        String description,
        BigDecimal goalAmount,
        BigDecimal currentAmount,
        CampaignType campaignType
) {

    /** Garante tipo e valores numéricos mesmo se o registro vier incompleto (ex.: migrações antigas). */
    public CampaignResponse {
        campaignType = campaignType != null ? campaignType : CampaignType.MONETARY_FICTITIOUS;
        goalAmount = goalAmount != null ? goalAmount : BigDecimal.ZERO;
        currentAmount = currentAmount != null ? currentAmount : BigDecimal.ZERO;
    }

    /** Convenção JavaBean para uso em templates ({@code campaign.monetary}). */
    public boolean isMonetary() {
        return campaignType == CampaignType.MONETARY_FICTITIOUS;
    }

    /** Convenção JavaBean para uso em templates ({@code campaign.communitySupport}). */
    public boolean isCommunitySupport() {
        return campaignType == CampaignType.COMMUNITY_SUPPORT;
    }

    public static CampaignResponse from(Campaign c) {
        return new CampaignResponse(
                c.getId(),
                c.getTitle(),
                c.getDescription(),
                c.getGoalAmount(),
                c.getCurrentAmount(),
                c.getCampaignType()
        );
    }

    /** Percentual para barra de progresso. */
    public double progressPercent() {
        BigDecimal goal = goalAmount != null ? goalAmount : BigDecimal.ZERO;
        BigDecimal cur = currentAmount != null ? currentAmount : BigDecimal.ZERO;
        if (goal.compareTo(BigDecimal.ZERO) <= 0) {
            return 0;
        }
        BigDecimal pct = cur
                .divide(goal, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
        double v = pct.doubleValue();
        return Math.min(100, Math.max(0, v));
    }
}
