package study.realWorld.service;

import study.realWorld.api.dto.commentsDtos.CommentCreateDto;
import study.realWorld.api.dto.commentsDtos.CommentDto;
import study.realWorld.api.dto.commentsDtos.CommentListDto;

public interface CommentService {

    CommentListDto getComments(String slug);

    CommentDto save(String slug,CommentCreateDto commentCreateDto);
}
