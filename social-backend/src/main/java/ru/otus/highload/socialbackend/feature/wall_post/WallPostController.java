package ru.otus.highload.socialbackend.feature.wall_post;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.highload.socialbackend.domain.WallPost;
import ru.otus.highload.util.rest.response.ListResponse;
import ru.otus.highload.util.rest.response.Response;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wall-post")
public class WallPostController {

    private final WallPostService wallPostService;

    @GetMapping()
    public ListResponse<WallPostDto> getUserPosts(@RequestParam(value = "userId", required = false) Long userId) {
        return new ListResponse<>(wallPostService.getUserLentaPosts(userId));
    }

    @PostMapping
    public Response<WallPostDto> savePost(@RequestBody WallPost wallPost) {
        return new Response<>(wallPostService.savePost(wallPost));
    }
}
