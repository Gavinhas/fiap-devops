package br.com.fiap.mural.web;

import br.com.fiap.mural.campaign.CampaignService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
public class CampaignSupportWebController {

    private final CampaignService campaignService;

    public CampaignSupportWebController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @PostMapping("/campaigns/{id}/contribute")
    public String contribute(
            @PathVariable Long id,
            @RequestParam BigDecimal amount,
            RedirectAttributes redirectAttributes) {
        try {
            campaignService.addSymbolicContribution(id, amount);
            redirectAttributes.addFlashAttribute(
                    "supportOk",
                    "Obrigado! Seu apoio simbólico foi registrado na meta desta campanha.");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("supportError", ex.getMessage());
        }
        return "redirect:/campaigns/" + id;
    }
}
