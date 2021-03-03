package study.realWorld.service;

import org.springframework.transaction.annotation.Transactional;
import study.realWorld.api.dto.commentsDtos.CommentCreateDto;
import study.realWorld.api.dto.commentsDtos.CommentDto;
import study.realWorld.api.dto.commentsDtos.CommentListDto;

public interface CommentService {
    @Transactional(readOnly = true)
    CommentListDto getComments(String slug);

    @Transactional
    CommentDto addCommentToArticleBySlug(String slug, CommentCreateDto commentCreateDto);
}
