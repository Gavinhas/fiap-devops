package br.com.fiap.mural.campaign;

import br.com.fiap.mural.campaign.dto.CampaignRequest;
import br.com.fiap.mural.campaign.dto.CampaignResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    @Transactional(readOnly = true)
    public CampaignResponse getById(Long id) {
        return campaignRepository.findById(id)
                .map(CampaignResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("Campanha não encontrada"));
    }

    @Transactional(readOnly = true)
    public Campaign requireEntity(Long id) {
        return campaignRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Campanha não encontrada"));
    }

    @Transactional
    public CampaignResponse create(CampaignRequest request) {
        CampaignType type = request.campaignType() != null
                ? request.campaignType()
                : CampaignType.MONETARY_FICTITIOUS;

        Campaign campaign = new Campaign();
        campaign.setTitle(request.title().trim());
        campaign.setDescription(request.description().trim());
        campaign.setCampaignType(type);

        if (type == CampaignType.COMMUNITY_SUPPORT) {
            campaign.setGoalAmount(BigDecimal.ZERO);
            campaign.setCurrentAmount(BigDecimal.ZERO);
        } else {
            BigDecimal goal = request.goalAmount();
            if (goal == null || goal.compareTo(new BigDecimal("0.01")) < 0) {
                throw new IllegalArgumentException(
                        "Para valores fictícios em moeda, informe goalAmount a partir de 0,01 (apenas simbólico).");
            }
            BigDecimal current = request.currentAmount() != null ? request.currentAmount() : BigDecimal.ZERO;
            if (current.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("currentAmount não pode ser negativo");
            }
            campaign.setGoalAmount(goal);
            campaign.setCurrentAmount(current);
        }

        Campaign saved = campaignRepository.save(campaign);
        return CampaignResponse.from(saved);
    }

    /**
     * Registro simbólico de apoio à meta (sem integração de pagamento).
     * O valor é somado ao andamento até o teto da meta.
     */
    @Transactional
    public CampaignResponse addSymbolicContribution(Long campaignId, BigDecimal amount) {
        if (amount == null || amount.compareTo(new BigDecimal("0.01")) < 0) {
            throw new IllegalArgumentException("Informe um valor a partir de R$ 0,01.");
        }
        BigDecimal normalized = amount.setScale(2, RoundingMode.HALF_UP);
        Campaign c = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new IllegalArgumentException("Campanha não encontrada"));
        if (c.getCampaignType() != CampaignType.MONETARY_FICTITIOUS) {
            throw new IllegalArgumentException("Esta campanha não possui meta em R$.");
        }
        BigDecimal goal = c.getGoalAmount() != null ? c.getGoalAmount() : BigDecimal.ZERO;
        BigDecimal current = c.getCurrentAmount() != null ? c.getCurrentAmount() : BigDecimal.ZERO;
        BigDecimal remaining = goal.subtract(current);
        if (remaining.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("A meta desta campanha já foi alcançada.");
        }
        BigDecimal toAdd = normalized.min(remaining);
        if (toAdd.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Não há valor disponível para somar à meta.");
        }
        c.setCurrentAmount(current.add(toAdd));
        return CampaignResponse.from(campaignRepository.save(c));
    }
}
