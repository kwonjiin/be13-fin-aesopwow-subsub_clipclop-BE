package com.aesopwow.subsubclipclop.controller;

import com.aesopwow.subsubclipclop.domain.comment.dto.CommentRequestDto;
import com.aesopwow.subsubclipclop.domain.comment.dto.CommentResponseDto;
import com.aesopwow.subsubclipclop.domain.comment.service.CommentService;
import com.aesopwow.subsubclipclop.domain.common.dto.BaseResponseDto;
import com.aesopwow.subsubclipclop.domain.post.dto.PostRequestDto;
import com.aesopwow.subsubclipclop.domain.post.dto.PostResponseDto;
import com.aesopwow.subsubclipclop.domain.post.service.QnaPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/qna")
public class QnaController {

    private final QnaPostService postService;
    private final CommentService commentService;

    /**
     * 모든 문의글 조회
     */
    @GetMapping
    public ResponseEntity<BaseResponseDto<List<PostResponseDto>>> getAllPosts() {
        List<PostResponseDto> posts = postService.findAll();
        return ResponseEntity.ok(new BaseResponseDto<>(HttpStatus.OK, posts));
    }

    /**
     * 특정 문의글 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseDto<PostResponseDto>> getPost(@PathVariable Long id) {
        PostResponseDto post = postService.findById(id);
        return ResponseEntity.ok(new BaseResponseDto<>(HttpStatus.OK, post));
    }

    /**
     * 문의글 작성
     */
    @PostMapping
    public ResponseEntity<BaseResponseDto<Void>> createPost(@RequestBody @Valid PostRequestDto dto) {
        postService.create(dto);
        return ResponseEntity.ok(new BaseResponseDto<>(HttpStatus.OK, null));
    }

    /**
     * 문의글에 달린 관리자 댓글 조회
     */
    @GetMapping("/{id}/comment")
    public ResponseEntity<BaseResponseDto<CommentResponseDto>> getComment(@PathVariable Long id) {
        CommentResponseDto comment = commentService.findByPostId(id);
        return ResponseEntity.ok(new BaseResponseDto<>(HttpStatus.OK, comment));
    }

    /**
     * 관리자 댓글 작성
     */
    @PostMapping("/{id}/comment")
    public ResponseEntity<BaseResponseDto<Void>> createComment(
            @PathVariable Long id,
            @RequestBody @Valid CommentRequestDto dto
    ) {
        commentService.create(id, dto);
        return ResponseEntity.ok(new BaseResponseDto<>(HttpStatus.OK, null));
    }

    @PutMapping("/{postId}/comment")
    public ResponseEntity<Void> updateComment(
            @PathVariable Long postId,
            @RequestBody @Valid CommentRequestDto dto
    ) {
        commentService.update(postId, dto);
        return ResponseEntity.ok().build();
    }
}