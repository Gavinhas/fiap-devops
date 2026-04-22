package br.com.fiap.mural.web;

import br.com.fiap.mural.campaign.CampaignType;
import br.com.fiap.mural.campaign.dto.CampaignRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class CampaignForm {

    @NotBlank(message = "Informe o título.")
    @Size(max = 200)
    private String title = "";

    @NotBlank(message = "Informe a descrição.")
    @Size(max = 4000)
    private String description = "";
    private CampaignType campaignType = CampaignType.MONETARY_FICTITIOUS;
    private BigDecimal goalAmount;
    private BigDecimal currentAmount;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CampaignType getCampaignType() {
        return campaignType;
    }

    public void setCampaignType(CampaignType campaignType) {
        this.campaignType = campaignType;
    }

    public BigDecimal getGoalAmount() {
        return goalAmount;
    }

    public void setGoalAmount(BigDecimal goalAmount) {
        this.goalAmount = goalAmount;
    }

    public BigDecimal getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(BigDecimal currentAmount) {
        this.currentAmount = currentAmount;
    }

    public CampaignRequest toRequest() {
        return new CampaignRequest(title, description, campaignType, goalAmount, currentAmount);
    }
}
