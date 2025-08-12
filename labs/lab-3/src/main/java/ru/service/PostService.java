package ru.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.domain.Owner;
import ru.domain.Pet;
import ru.domain.Post;
import ru.dto.PostDto;
import ru.exceptions.ResourceNotFoundException;
import ru.repository.OwnerRepository;
import ru.repository.PetRepository;
import ru.repository.PostRepository;
import ru.request.CreatePostRequest;
import ru.request.UpdatePostRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final OwnerRepository ownerRepository;
    private final PetRepository petRepository;

    public PostDto createPost(CreatePostRequest request) {
        Owner owner = ownerRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));
        Pet pet = petRepository.findById(request.getPetId())
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found"));

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setDescription(request.getDescription());
        post.setOwner(owner);
        post.setPet(pet);

        return convertPostToDto(postRepository.save(post));
    }

    public PostDto updatePost(long id, UpdatePostRequest request) {
        Post updated = postRepository.findById(id).map(existingPost -> {
            existingPost.setTitle(request.getTitle());
            existingPost.setDescription(request.getDescription());
            existingPost.setPrice(request.getPrice());
            return postRepository.save(existingPost);
        }).orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        return convertPostToDto(updated);
    }

    public void deletePost(long id) {
        postRepository.findById(id).ifPresentOrElse(postRepository::delete, () -> {
            throw new ResourceNotFoundException("Post not found");
        });
    }

    public List<PostDto> getPostsWithPriceBelow(BigDecimal price) {
        validatePrice(price);
        return convertListToDto(postRepository.findByPriceLessThan(price));
    }

    public List<PostDto> getPostsWithPriceAbove(BigDecimal price) {
        validatePrice(price);
        return convertListToDto(postRepository.findByPriceGreaterThan(price));
    }

    public List<PostDto> getPostsWithPriceBetween(BigDecimal min, BigDecimal max) {
        validatePrice(min);
        validatePrice(max);
        if (min.compareTo(max) > 0) {
            throw new IllegalArgumentException("Minimum price cannot be greater than maximum price");
        }
        return convertListToDto(postRepository.findByPriceBetween(min, max));
    }

    public List<PostDto> getPostsWithTitleContaining(String title) {
        List<Post> posts = (title == null || title.isEmpty())
                ? postRepository.findAll()
                : postRepository.findByTitleContaining(title);
        return convertListToDto(posts);
    }

    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        return convertPostToDto(post);
    }

    public List<PostDto> getPostsByOwnerId(long ownerId) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));
        return convertListToDto(postRepository.findByOwner(owner));
    }

    public PostDto convertPostToDto(Post post) {
        return modelMapper.map(post, PostDto.class);
    }

    private List<PostDto> convertListToDto(List<Post> posts) {
        return posts.stream()
                .map(this::convertPostToDto)
                .toList();
    }

    private void validatePrice(BigDecimal price) {
        if (price == null) {
            throw new IllegalArgumentException("Price cannot be null");
        }
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
    }
}