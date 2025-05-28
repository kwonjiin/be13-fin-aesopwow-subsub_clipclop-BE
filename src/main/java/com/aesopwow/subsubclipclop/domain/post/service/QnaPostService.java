package com.aesopwow.subsubclipclop.domain.post.service;

import com.aesopwow.subsubclipclop.domain.comment.repository.CommentRepository;
import com.aesopwow.subsubclipclop.domain.post.dto.PostRequestDto;
import com.aesopwow.subsubclipclop.domain.post.dto.PostResponseDto;
import com.aesopwow.subsubclipclop.domain.post.repository.PostRepository;
import com.aesopwow.subsubclipclop.entity.QnaPost;
import com.aesopwow.subsubclipclop.entity.Role;
import com.aesopwow.subsubclipclop.entity.User;
import com.aesopwow.subsubclipclop.domain.user.repository.UserRepository;
import com.aesopwow.subsubclipclop.global.enums.ErrorCode;
import com.aesopwow.subsubclipclop.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QnaPostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    // 전체 조회
    public List<PostResponseDto> findAll() {
        return postRepository.findAll().stream()
                .map(post -> new PostResponseDto(
                        post.getQnaPostNo(),
                        post.getUser().getUserNo(), // ✅ userNo 추가
                        post.getTitle(),
                        post.getContent(),
                        post.getCreatedAt().toString()
                ))
                .toList();
    }

    // 단건 조회
    public PostResponseDto findById(Long postId) {
        QnaPost post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        return new PostResponseDto(
                post.getQnaPostNo(),
                post.getUser().getUserNo(), // ✅ userNo 추가
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt().toString()
        );
    }


    // 작성
    public void create(PostRequestDto dto) {
        User user = userRepository.findById(dto.getUserNo())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        QnaPost post = QnaPost.builder()
                .user(user)
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();

        postRepository.save(post);
    }

    // 수정
    public void update(Long postId, PostRequestDto dto) {
        QnaPost post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        // 작성자 일치 확인
        if (!post.getUser().getUserNo().equals(dto.getUserNo())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());

        // ✅ 변경사항을 DB에 반영
        postRepository.save(post);
    }

    public void delete(Long postId, Long userNo) {
        QnaPost post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 🔒 작성자 본인 or 관리자만 삭제 가능
        boolean isOwner = post.getUser().getUserNo().equals(userNo);
        boolean isAdmin = Role.RoleType.ADMIN.equals(user.getRole().getName());

        if (!isOwner && !isAdmin) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        // 1. 댓글 먼저 삭제 (있을 경우)
        commentRepository.findByQnaPost_QnaPostNo(postId)
                .ifPresent(commentRepository::delete);

        // 2. 게시글 삭제
        postRepository.delete(post);
    }


}
