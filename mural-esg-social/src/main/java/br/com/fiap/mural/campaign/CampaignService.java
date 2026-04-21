package br.com.fiap.mural.campaign;

import br.com.fiap.mural.campaign.dto.CampaignRequest;
import br.com.fiap.mural.campaign.dto.CampaignResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CampaignService {

    private final CampaignRepository campaignRepository;

    public CampaignService(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }

    @Transactional(readOnly = true)
    public List<CampaignResponse> listAll() {
        return campaignRepository.findAll().stream()
                .map(CampaignResponse::from)
                .toList();
    }

    @Transactional
    public CampaignResponse create(CampaignRequest request) {
        BigDecimal goal = request.goalAmount();
        BigDecimal current = request.currentAmount() != null ? request.currentAmount() : BigDecimal.ZERO;
        if (goal.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("goalAmount deve ser maior que zero");
        }
        if (current.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("currentAmount não pode ser negativo");
        }

        Campaign campaign = new Campaign();
        campaign.setTitle(request.title().trim());
        campaign.setDescription(request.description().trim());
        campaign.setGoalAmount(goal);
        campaign.setCurrentAmount(current);

        Campaign saved = campaignRepository.save(campaign);
        return CampaignResponse.from(saved);
    }
}
