package com.example.vibeapp.post;

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
    public String list(@org.springframework.web.bind.annotation.RequestParam(defaultValue = "1") int page,
            Model model) {
        int size = 5;
        List<Post> posts = postService.getPagedPosts(page, size);
        int totalCount = postService.getTotalCount();
        int totalPages = (int) Math.ceil((double) totalCount / size);

        model.addAttribute("posts", posts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
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

    @org.springframework.web.bind.annotation.PostMapping("/posts/{no}/save")
    public String savePost(@org.springframework.web.bind.annotation.PathVariable Long no,
            @org.springframework.web.bind.annotation.RequestParam String title,
            @org.springframework.web.bind.annotation.RequestParam String content) {
        postService.updatePost(no, title, content);
        return "redirect:/posts/" + no;
    }

    @org.springframework.web.bind.annotation.PostMapping("/posts/{no}/delete")
    public String deletePost(@org.springframework.web.bind.annotation.PathVariable Long no) {
        postService.deletePost(no);
        return "redirect:/posts";
    }
}
