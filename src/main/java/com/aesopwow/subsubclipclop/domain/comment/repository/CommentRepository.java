package com.aesopwow.subsubclipclop.domain.comment.repository;

import com.aesopwow.subsubclipclop.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByQnaPost_QnaPostNo(Long qnaPostNo);
}