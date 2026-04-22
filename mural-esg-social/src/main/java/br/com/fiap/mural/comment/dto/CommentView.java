package br.com.fiap.mural.comment.dto;

import br.com.fiap.mural.comment.Comment;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public record CommentView(Long id, String authorName, String body, String createdAtLabel) {

    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").withZone(ZoneId.systemDefault());

    public static CommentView from(Comment c) {
        return new CommentView(
                c.getId(),
                c.getAuthor().getUsername(),
                c.getBody(),
                FMT.format(c.getCreatedAt())
        );
    }
}
