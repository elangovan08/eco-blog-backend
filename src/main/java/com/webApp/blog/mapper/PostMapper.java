package com.webApp.blog.mapper;

import com.webApp.blog.dto.request.PostRequestDTO;
import com.webApp.blog.dto.response.CommentResponseDTO;
import com.webApp.blog.dto.response.PostResponseDTO;
import com.webApp.blog.dto.response.UserResponseDTO;
import com.webApp.blog.model.Post;
import com.webApp.blog.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostMapper {

    public PostResponseDTO toDetailResponse(Post post) {
        List<CommentResponseDTO> comments = post.getComments().stream()
                .map(CommentResponseDTO::fromEntity)
                .toList();
        return toResponse(post, comments);
    }

    public PostResponseDTO toSummaryResponse(Post post) {
        return toResponse(post, List.of());
    }

    public Post toEntity(PostRequestDTO request, User author) {
        Post post = new Post();
        updateEntity(post, request, author);
        return post;
    }

    public void updateEntity(Post post, PostRequestDTO request, User author) {
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setAuthor(author);
    }

    private PostResponseDTO toResponse(Post post, List<CommentResponseDTO> comments) {
        return new PostResponseDTO(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getLikeCount(),
                post.getBookmarkedBy().size(),
                UserResponseDTO.fromEntity(post.getAuthor()),
                comments
        );
    }
}
