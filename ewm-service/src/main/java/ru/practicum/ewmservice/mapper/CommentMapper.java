package ru.practicum.ewmservice.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewmservice.dto.CommentDto;
import ru.practicum.ewmservice.model.Comment;

@Component
@RequiredArgsConstructor
public class CommentMapper {
    private final UserMapper userMapper;
    private final EventMapper eventMapper;

    public CommentDto toCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getContent(),
                comment.getStatus(),
                userMapper.toUserShortDto(comment.getUser()),
                eventMapper.toEventShortDto(comment.getEvent()),
                comment.getCreated()
        );
    }

    public Comment toComment(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setContent(commentDto.getContent());
        comment.setStatus(commentDto.getStatus());
        return comment;
    }
}
