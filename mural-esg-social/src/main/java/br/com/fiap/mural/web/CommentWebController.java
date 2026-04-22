package br.com.fiap.mural.web;

import br.com.fiap.mural.comment.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class CommentWebController {

    private final CommentService commentService;

    public CommentWebController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/campaigns/{id}/comments")
    public String addComment(
            @PathVariable Long id,
            @RequestParam(name = "body", required = false, defaultValue = "") String body,
            Principal principal) {
        commentService.add(id, principal.getName(), body);
        return "redirect:/campaigns/" + id;
    }
}
