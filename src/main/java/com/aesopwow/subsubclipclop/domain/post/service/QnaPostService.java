package com.aesopwow.subsubclipclop.domain.post.service;

import com.aesopwow.subsubclipclop.domain.post.dto.PostRequestDto;
import com.aesopwow.subsubclipclop.domain.post.dto.PostResponseDto;
import com.aesopwow.subsubclipclop.domain.post.repository.PostRepository;
import com.aesopwow.subsubclipclop.entity.QnaPost;
import com.aesopwow.subsubclipclop.entity.User;
import com.aesopwow.subsubclipclop.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QnaPostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public List<PostResponseDto> findAll() {
        return postRepository.findAll().stream()
                .map(post -> new PostResponseDto(
                        post.getQnaPostNo(),
                        post.getTitle(),
                        post.getContent(),
                        post.getCreatedAt().toString()
                ))
                .toList();
    }

    public PostResponseDto findById(Long postId) {
        QnaPost post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("문의글이 존재하지 않습니다."));
        return new PostResponseDto(
                post.getQnaPostNo(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt().toString()
        );
    }

    public void create(PostRequestDto dto) {
        User user = userRepository.findById(dto.getUserNo())
                .orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다."));

        QnaPost post = QnaPost.builder()
                .user(user)
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();

        postRepository.save(post);
    }
}