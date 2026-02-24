package com.example.vibeapp.post;

import com.example.vibeapp.post.dto.PostCreateDto;
import com.example.vibeapp.post.dto.PostListDto;
import com.example.vibeapp.post.dto.PostResponseDTO;
import com.example.vibeapp.post.dto.PostUpdateDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public String listPosts(@RequestParam(defaultValue = "1") int page, Model model) {
        int size = 5;
        List<PostListDto> posts = postService.findPagedPosts(page, size);
        int totalCount = postService.countPosts();
        int totalPages = (int) Math.ceil((double) totalCount / size);

        model.addAttribute("posts", posts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "post/posts";
    }

    @GetMapping("/posts/{no}")
    public String viewPost(@PathVariable Long no, Model model) {
        PostResponseDTO post = postService.viewPost(no);
        model.addAttribute("post", post);
        return "post/post_detail";
    }

    @GetMapping("/posts/{no}/edit")
    public String updatePostForm(@PathVariable Long no, Model model) {
        PostResponseDTO post = postService.findPost(no);
        model.addAttribute("post", post);
        model.addAttribute("postUpdateDto", new PostUpdateDto(post.title(), post.content()));
        return "post/post_edit_form";
    }

    @GetMapping("/posts/new")
    public String createPostForm(Model model) {
        model.addAttribute("postCreateDto", new PostCreateDto());
        return "post/post_new_form";
    }

    @PostMapping("/posts/add")
    public String addPost(@Valid @ModelAttribute PostCreateDto postCreateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "post/post_new_form";
        }
        postService.addPost(postCreateDto);
        return "redirect:/posts";
    }

    @PostMapping("/posts/{no}/save")
    public String savePost(@PathVariable Long no, @Valid @ModelAttribute PostUpdateDto postUpdateDto,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            PostResponseDTO post = postService.findPost(no);
            model.addAttribute("post", post);
            return "post/post_edit_form";
        }
        postService.updatePost(no, postUpdateDto);
        return "redirect:/posts/" + no;
    }

    @PostMapping("/posts/{no}/delete")
    public String deletePost(@PathVariable Long no) {
        postService.deletePost(no);
        return "redirect:/posts";
    }
}
