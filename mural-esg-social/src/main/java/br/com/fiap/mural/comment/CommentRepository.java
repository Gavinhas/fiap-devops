package br.com.fiap.mural.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c JOIN FETCH c.author WHERE c.campaign.id = :campaignId ORDER BY c.createdAt DESC")
    List<Comment> findDetailByCampaign(@Param("campaignId") Long campaignId);
}
