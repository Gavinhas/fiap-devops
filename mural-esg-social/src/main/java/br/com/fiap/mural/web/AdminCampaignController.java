package br.com.fiap.mural.web;

import br.com.fiap.mural.campaign.CampaignService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminCampaignController {

    private final CampaignService campaignService;

    public AdminCampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @GetMapping("/campaigns/new")
    public String newCampaign(Model model) {
        if (!model.containsAttribute("campaignForm")) {
            model.addAttribute("campaignForm", new CampaignForm());
        }
        return "admin/campaign-form";
    }

    @PostMapping("/campaigns")
    public String create(
            @Valid @ModelAttribute("campaignForm") CampaignForm campaignForm,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/campaign-form";
        }
        try {
            campaignService.create(campaignForm.toRequest());
        } catch (IllegalArgumentException ex) {
            model.addAttribute("formError", ex.getMessage());
            return "admin/campaign-form";
        }
        return "redirect:/";
    }
}
