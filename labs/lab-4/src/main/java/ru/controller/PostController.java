package ru.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dto.PostDto;
import ru.request.CreatePostRequest;
import ru.request.UpdatePostRequest;
import ru.response.ApiResponse;
import ru.service.PostService;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/posts")
public class PostController {
    private final PostService postService;

    private <T> ResponseEntity<ApiResponse<T>> ok(T data) {
        return ResponseEntity.ok(new ApiResponse<>("Success", data));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostDto>> getPostById(@PathVariable long id) {
        return ok(postService.getPostById(id));
    }

    @GetMapping("/price/below")
    public ResponseEntity<ApiResponse<List<PostDto>>> getPostsByPriceBelow(@RequestParam BigDecimal price) {
        return ok(postService.getPostsWithPriceBelow(price));
    }

    @GetMapping("/price/above")
    public ResponseEntity<ApiResponse<List<PostDto>>> getPostsByPriceAbove(@RequestParam BigDecimal price) {
        return ok(postService.getPostsWithPriceAbove(price));
    }

    @GetMapping("/price/between")
    public ResponseEntity<ApiResponse<List<PostDto>>> getPostsByPriceBetween(@RequestParam BigDecimal min,
                                                                             @RequestParam BigDecimal max) {
        return ok(postService.getPostsWithPriceBetween(min, max));
    }

    @GetMapping("/title/contains")
    public ResponseEntity<ApiResponse<List<PostDto>>> getPostsByTitleContaining(@RequestParam String title) {
        return ok(postService.getPostsWithTitleContaining(title));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<ApiResponse<List<PostDto>>> getPostsByOwnerId(@PathVariable long ownerId) {
        return ok(postService.getPostsByOwnerId(ownerId));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PostDto>> createPost(@Valid @RequestBody CreatePostRequest request) {
        return ok(postService.createPost(request));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostDto>> updatePost(@PathVariable long postId,
                                                           @Valid @RequestBody UpdatePostRequest request) {
        return ok(postService.updatePost(postId, request));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable long postId) {
        postService.deletePost(postId);
        return ok(null);
    }
}
