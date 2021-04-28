package study.realWorld.api.dto.commentsDtos;


import lombok.*;
import study.realWorld.api.dto.articleDtos.ArticleDto;

import java.util.List;

@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommentListDto {

    private List<CommentDto> comments;
}
