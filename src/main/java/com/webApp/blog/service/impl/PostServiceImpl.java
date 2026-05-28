package com.webApp.blog.service.impl;

import com.webApp.blog.dto.request.PostRequestDTO;
import com.webApp.blog.dto.response.PostResponseDTO;
import com.webApp.blog.exception.ResourceNotFoundException;
import com.webApp.blog.mapper.PostMapper;
import com.webApp.blog.model.Post;
import com.webApp.blog.model.User;
import com.webApp.blog.repository.PostRepository;
import com.webApp.blog.repository.UserRepository;
import com.webApp.blog.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postMapper = postMapper;
    }

    @Override
    public List<PostResponseDTO> getAllPosts() {
        return postRepository.findAll().stream()
                .map(postMapper::toSummaryResponse)
                .toList();
    }

    @Override
    public PostResponseDTO getPostById(Long id) {
        return postMapper.toDetailResponse(findPost(id));
    }

    @Override
    @Transactional
    public PostResponseDTO createPost(PostRequestDTO request) {
        User author = findUser(request.getAuthorId());

        Post post = postMapper.toEntity(request, author);

        return postMapper.toDetailResponse(postRepository.save(post));
    }

    @Override
    @Transactional
    public PostResponseDTO updatePost(Long id, PostRequestDTO request) {
        Post post = findPost(id);
        User author = findUser(request.getAuthorId());

        postMapper.updateEntity(post, request, author);

        return postMapper.toDetailResponse(postRepository.save(post));
    }

    @Override
    @Transactional
    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new ResourceNotFoundException("Post not found with id: " + id);
        }
        postRepository.deleteById(id);
    }

    @Override
    public List<PostResponseDTO> searchPosts(String keyword) {
        return postRepository.findByTitleContainingIgnoreCase(keyword).stream()
                .map(postMapper::toSummaryResponse)
                .toList();
    }

    @Override
    public Page<PostResponseDTO> getPostsPage(String keyword, Pageable pageable) {
        Page<Post> posts = StringUtils.hasText(keyword)
                ? postRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword, keyword, pageable)
                : postRepository.findAll(pageable);

        return posts.map(postMapper::toSummaryResponse);
    }

    @Override
    @Transactional
    public PostResponseDTO likePost(Long postId, Long userId) {
        Post post = findPost(postId);
        User user = findUser(userId);

        if (post.getLikedBy().add(user)) {
            post.setLikeCount(post.getLikedBy().size());
        }

        return postMapper.toDetailResponse(postRepository.save(post));
    }

    @Override
    @Transactional
    public PostResponseDTO bookmarkPost(Long postId, Long userId) {
        Post post = findPost(postId);
        User user = findUser(userId);
        post.getBookmarkedBy().add(user);
        return postMapper.toDetailResponse(postRepository.save(post));
    }

    private Post findPost(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
    }

    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }
}
