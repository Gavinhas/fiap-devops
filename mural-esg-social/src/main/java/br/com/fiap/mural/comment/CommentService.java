package br.com.fiap.mural.comment;

import br.com.fiap.mural.campaign.Campaign;
import br.com.fiap.mural.campaign.CampaignService;
import br.com.fiap.mural.comment.dto.CommentView;
import br.com.fiap.mural.user.AppUser;
import br.com.fiap.mural.user.AppUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CampaignService campaignService;
    private final AppUserRepository userRepository;

    public CommentService(
            CommentRepository commentRepository,
            CampaignService campaignService,
            AppUserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.campaignService = campaignService;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<CommentView> listForCampaign(Long campaignId) {
        return commentRepository.findDetailByCampaign(campaignId).stream()
                .map(CommentView::from)
                .toList();
    }

    @Transactional
    public void add(Long campaignId, String username, String body) {
        String text = body != null ? body.trim() : "";
        if (text.isEmpty()) {
            throw new IllegalArgumentException("Comentário não pode ficar vazio.");
        }
        if (text.length() > 2000) {
            throw new IllegalArgumentException("Comentário muito longo (máx. 2000 caracteres).");
        }
        Campaign campaign = campaignService.requireEntity(campaignId);
        AppUser author = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        commentRepository.save(new Comment(campaign, author, text));
    }
}
