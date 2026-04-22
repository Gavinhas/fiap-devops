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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CampaignContributeIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CampaignRepository campaignRepository;

    @Test
    @WithMockUser(username = "usuario", roles = "USER")
    void contributeIncreasesCurrentAmount() throws Exception {
        Campaign c = new Campaign();
        c.setTitle("Meta teste");
        c.setDescription("D");
        c.setGoalAmount(new BigDecimal("1000.00"));
        c.setCurrentAmount(new BigDecimal("100.00"));
        c.setCampaignType(CampaignType.MONETARY_FICTITIOUS);
        c = campaignRepository.save(c);

        mockMvc.perform(post("/campaigns/" + c.getId() + "/contribute")
                        .param("amount", "50")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("supportOk"));

        mockMvc.perform(get("/campaigns/" + c.getId()))
                .andExpect(status().isOk());
    }
}
