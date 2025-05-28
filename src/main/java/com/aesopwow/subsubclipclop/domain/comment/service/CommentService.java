package com.aesopwow.subsubclipclop.domain.comment.service;

import com.aesopwow.subsubclipclop.domain.comment.dto.CommentRequestDto;
import com.aesopwow.subsubclipclop.domain.comment.dto.CommentResponseDto;
import com.aesopwow.subsubclipclop.domain.comment.repository.CommentRepository;
import com.aesopwow.subsubclipclop.domain.post.repository.PostRepository;
import com.aesopwow.subsubclipclop.domain.user.repository.UserRepository;
import com.aesopwow.subsubclipclop.entity.Comment;
import com.aesopwow.subsubclipclop.entity.QnaPost;
import com.aesopwow.subsubclipclop.entity.Role;
import com.aesopwow.subsubclipclop.entity.User;
import com.aesopwow.subsubclipclop.global.enums.ErrorCode;
import com.aesopwow.subsubclipclop.global.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentResponseDto findByPostId(Long postId) {
        return commentRepository.findByQnaPost_QnaPostNo(postId)
                .map(c -> new CommentResponseDto(
                        c.getCommentNo(),
                        c.getContent(),
                        c.getCreatedAt().toString()
                ))
                .orElse(null);
    }

    public void create(Long postId, CommentRequestDto dto) {
        // ❗ 1. 이미 답변이 존재하면 예외 처리
        if (commentRepository.findByQnaPost_QnaPostNo(postId).isPresent()) {
            throw new CustomException(ErrorCode.ALREADY_ANSWERED);
        }

        // 2. 존재하는 게시글인지 확인
        QnaPost post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        // 3. 관리자 검증
        validateAdminUser(dto.getUserNo());

        // 4. 사용자(관리자) 조회
        User user = userRepository.findById(dto.getUserNo())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 5. 답변 저장
        Comment comment = Comment.builder()
                .qnaPost(post)
                .user(user)
                .content(dto.getContent())
                .build();

        commentRepository.save(comment);
    }

    // ✅ 공통 검증 메서드
    private void validateAdminUser(Long userNo) {
        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (Boolean.TRUE.equals(user.getIsDeleted())) {
            throw new CustomException(ErrorCode.DELETED_USER);
        }

        if (!Role.RoleType.ADMIN.equals(user.getRole().getName())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ADMIN_ONLY);
        }
    }
}