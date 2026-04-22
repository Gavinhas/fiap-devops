package br.com.fiap.mural.web;

import br.com.fiap.mural.campaign.Campaign;
import br.com.fiap.mural.campaign.CampaignRepository;
import br.com.fiap.mural.campaign.CampaignType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CampaignDetailPageIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CampaignRepository campaignRepository;

    @Test
    void detailPageReturns200AndTitle() throws Exception {
        Campaign c = new Campaign();
        c.setTitle("Campanha página");
        c.setDescription("Descrição completa da campanha.");
        c.setGoalAmount(new BigDecimal("100.00"));
        c.setCurrentAmount(new BigDecimal("25.00"));
        c.setCampaignType(CampaignType.MONETARY_FICTITIOUS);
        c = campaignRepository.save(c);

        mockMvc.perform(get("/campaigns/" + c.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Campanha página")));
    }

    @Test
    @WithMockUser(username = "usuario", roles = "USER")
    void detailPageWorksWhenLoggedIn() throws Exception {
        Campaign c = new Campaign();
        c.setTitle("Logado ok");
        c.setDescription("Teste com sessão.");
        c.setGoalAmount(new BigDecimal("50.00"));
        c.setCurrentAmount(BigDecimal.ZERO);
        c.setCampaignType(CampaignType.MONETARY_FICTITIOUS);
        c = campaignRepository.save(c);

        mockMvc.perform(get("/campaigns/" + c.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Logado ok")))
                .andExpect(content().string(containsString("Publicar")));
    }
}
