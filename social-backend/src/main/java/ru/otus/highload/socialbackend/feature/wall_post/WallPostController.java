package ru.otus.highload.socialbackend.feature.wall_post;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.highload.socialbackend.domain.WallPost;
import ru.otus.highload.util.rest.response.ListResponse;
import ru.otus.highload.util.rest.response.Response;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wall-posts")
public class WallPostController {

    private final WallPostService wallPostService;

    @GetMapping
    public ListResponse<WallPostDto> getWallPosts(@RequestParam(value = "userId", required = false) Long userId) {
        return new ListResponse<>(wallPostService.getUserWallPosts(userId));
    }

    @GetMapping("/lenta")
    public ListResponse<WallPostDto> getUserLentaPosts(@RequestParam(value = "userId", required = false) Long userId) {
        return new ListResponse<>(wallPostService.getUserLentaPosts(userId));
    }

    @PostMapping
    public Response<WallPostDto> savePost(@RequestBody WallPost wallPost) {
        return new Response<>(wallPostService.savePost(wallPost));
    }
}
