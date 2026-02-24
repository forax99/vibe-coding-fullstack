package com.example.vibeapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public String list(Model model) {
        List<Post> posts = postService.getPosts();
        model.addAttribute("posts", posts);
        return "posts";
    }

    @GetMapping("/posts/{no}")
    public String detail(@org.springframework.web.bind.annotation.PathVariable Long no, Model model) {
        Post post = postService.getPostWithViewCount(no);
        model.addAttribute("post", post);
        return "post_detail";
    }

    @GetMapping("/posts/{no}/edit")
    public String editForm(@org.springframework.web.bind.annotation.PathVariable Long no, Model model) {
        Post post = postService.getPost(no);
        model.addAttribute("post", post);
        return "post_edit_form";
    }

    @GetMapping("/posts/new")
    public String form() {
        return "post_new_form";
    }

    @org.springframework.web.bind.annotation.PostMapping("/posts/add")
    public String addPost(@org.springframework.web.bind.annotation.RequestParam String title,
            @org.springframework.web.bind.annotation.RequestParam String content) {
        postService.addPost(title, content);
        return "redirect:/posts";
    }
}
