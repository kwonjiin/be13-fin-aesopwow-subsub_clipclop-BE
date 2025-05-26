package com.aesopwow.subsubclipclop.domain.comment.service;

import com.aesopwow.subsubclipclop.domain.comment.dto.CommentRequestDto;
import com.aesopwow.subsubclipclop.domain.comment.dto.CommentResponseDto;
import com.aesopwow.subsubclipclop.domain.comment.repository.CommentRepository;
import com.aesopwow.subsubclipclop.domain.post.repository.PostRepository;
import com.aesopwow.subsubclipclop.entity.Comment;
import com.aesopwow.subsubclipclop.entity.QnaPost;
import com.aesopwow.subsubclipclop.entity.Role;
import com.aesopwow.subsubclipclop.entity.User;
import com.aesopwow.subsubclipclop.domain.user.repository.UserRepository;
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
        QnaPost post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("문의글이 존재하지 않습니다."));

        User user = userRepository.findById(dto.getUserNo())
                .orElseThrow(() -> new RuntimeException("해당 계정이 존재하지 않습니다."));

        if (user.getRole().getName() != Role.RoleType.ADMIN) {
            throw new RuntimeException("답변 작성은 관리자만 가능합니다.");
        }

        Comment comment = Comment.builder()
                .qnaPost(post)
                .user(user)
                .content(dto.getContent())
                .build();

        commentRepository.save(comment);
    }

    public void update(Long postId, CommentRequestDto dto) {
        QnaPost post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("문의글이 존재하지 않습니다."));

        User user = userRepository.findById(dto.getUserNo())
                .orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다."));

        // 관리자 권한 확인
        if (user.getRole().getName() != Role.RoleType.ADMIN) {
            throw new RuntimeException("답변 수정은 관리자만 가능합니다.");
        }

        Comment comment = commentRepository.findByQnaPost_QnaPostNo(postId)
                .orElseThrow(() -> new RuntimeException("해당 문의글에 대한 답변이 존재하지 않습니다."));

        comment.setContent(dto.getContent());
    }

}