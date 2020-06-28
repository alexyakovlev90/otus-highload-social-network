package ru.otus.highload.socialbackend.feature.wall_post;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.highload.socialbackend.domain.WallPost;
import ru.otus.highload.socialbackend.feature.user.UseLiteDtoConverter;

@Component
@RequiredArgsConstructor
public class WallPostToDtoConverter implements Converter<WallPost, WallPostDto> {

    private final UseLiteDtoConverter useLiteDtoConverter;

    @Override
    public WallPostDto convert(WallPost wallPost) {
        return new WallPostDto()
                .setId(wallPost.getId())
                .setFromUser(useLiteDtoConverter.convert(wallPost.getFromUser()))
                .setToUser(useLiteDtoConverter.convert(wallPost.getToUser()))
                .setText(wallPost.getText())
                .setDateCreated(wallPost.getDateCreated().toString());
    }
}
