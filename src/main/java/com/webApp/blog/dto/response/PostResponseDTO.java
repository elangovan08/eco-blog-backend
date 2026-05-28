package com.webApp.blog.dto.response;

import com.webApp.blog.model.Post;

import java.time.LocalDateTime;
import java.util.List;

public class PostResponseDTO {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private int likeCount;
    private int bookmarkCount;
    private UserResponseDTO author;
    private List<CommentResponseDTO> comments;

    public PostResponseDTO() {
    }

    public PostResponseDTO(
            Long id,
            String title,
            String content,
            LocalDateTime createdAt,
            int likeCount,
            int bookmarkCount,
            UserResponseDTO author,
            List<CommentResponseDTO> comments
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.likeCount = likeCount;
        this.bookmarkCount = bookmarkCount;
        this.author = author;
        this.comments = comments;
    }

    public static PostResponseDTO fromEntity(Post post) {
        List<CommentResponseDTO> comments = post.getComments().stream()
                .map(CommentResponseDTO::fromEntity)
                .toList();
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

    public static PostResponseDTO summaryFromEntity(Post post) {
        return new PostResponseDTO(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getLikeCount(),
                post.getBookmarkedBy().size(),
                UserResponseDTO.fromEntity(post.getAuthor()),
                List.of()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getBookmarkCount() {
        return bookmarkCount;
    }

    public void setBookmarkCount(int bookmarkCount) {
        this.bookmarkCount = bookmarkCount;
    }

    public UserResponseDTO getAuthor() {
        return author;
    }

    public void setAuthor(UserResponseDTO author) {
        this.author = author;
    }

    public List<CommentResponseDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentResponseDTO> comments) {
        this.comments = comments;
    }
}
