package br.com.fiap.mural.web;

import br.com.fiap.mural.campaign.CampaignService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final CampaignService campaignService;

    public HomeController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("campaigns", campaignService.listAll());
        return "index";
    }
}
