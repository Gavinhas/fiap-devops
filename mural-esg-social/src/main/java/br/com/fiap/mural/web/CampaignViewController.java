package br.com.fiap.mural.web;

import br.com.fiap.mural.campaign.CampaignService;
import br.com.fiap.mural.campaign.dto.CampaignResponse;
import br.com.fiap.mural.comment.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class CampaignViewController {

    private final CampaignService campaignService;
    private final CommentService commentService;

    public CampaignViewController(CampaignService campaignService, CommentService commentService) {
        this.campaignService = campaignService;
        this.commentService = commentService;
    }

    @GetMapping("/campaigns/{id}")
    public String detail(@PathVariable Long id, Model model) {
        CampaignResponse campaign;
        try {
            campaign = campaignService.getById(id);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("campaign", campaign);
        model.addAttribute("progressPct", campaign.progressPercent());
        model.addAttribute("comments", commentService.listForCampaign(id));
        return "campaign-detail";
    }
}
