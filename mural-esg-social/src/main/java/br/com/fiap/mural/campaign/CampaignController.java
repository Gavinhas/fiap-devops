package br.com.fiap.mural.campaign;

import br.com.fiap.mural.campaign.dto.CampaignRequest;
import br.com.fiap.mural.campaign.dto.CampaignResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CampaignController {

    private final CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @GetMapping("/campaigns")
    public List<CampaignResponse> listCampaigns() {
        return campaignService.listAll();
    }

    @PostMapping("/admin/campaigns")
    public ResponseEntity<CampaignResponse> createCampaign(@Valid @RequestBody CampaignRequest request) {
        CampaignResponse created = campaignService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
