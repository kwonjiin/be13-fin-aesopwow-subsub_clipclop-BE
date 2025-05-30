package com.aesopwow.subsubclipclop.domain.post.repository;

import com.aesopwow.subsubclipclop.entity.QnaPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<QnaPost, Long> {
}
