package com.ddd.moodof.acceptance;

import com.ddd.moodof.application.dto.TagDTO;
import com.ddd.moodof.domain.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;
import static com.ddd.moodof.adapter.presentation.TagController.API_TAG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class TagAcceptanceTest extends AcceptanceTest{
    private Long userId;

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
        User user = signUp();
        userId = user.getId();
    }

    @Test
    public void 유저아이디_태그_전체_조회() throws Exception {
        // given
        태그_생성(userId, "tag1");
        태그_생성(userId, "tag2");
        태그_생성(userId, "tag3");
        TagDTO.TagResponse tagResponse = 태그_생성(userId, "tag4");

        // when
        String uri = UriComponentsBuilder.fromUriString(API_TAG)
                .build().toUriString();
        List<TagDTO.TagResponse> response = getListWithLogin(uri, TagDTO.TagResponse.class, userId);

        // then
        assertAll(
                () -> assertThat(response.get(0)).isNotNull()
        );
    }

    @Test
    public void 태그를_생성한다() throws Exception {
        // given
        TagDTO.TagResponse response = 태그_생성(userId, "tagName1");

        // when
        // then
        assertAll(
                () -> assertThat(response.getId()).isNotNull(),
                () -> assertThat(response.getUserId()).isEqualTo(userId),
                () -> assertThat(response.getTagName()).isEqualTo("tagName1"),
                () -> assertThat(response.getCreatedDate()).isNotNull(),
                () -> assertThat(response.getLastModifiedDate()).isNotNull()
        );
    }
    @Test
    public void 태그를_삭제한다() throws Exception {
        // given
        태그_생성(userId, "tagName1");
        TagDTO.TagResponse response = 태그_생성(userId, "tagName2");

        // when
        태그_리스트_출력(userId);
        deleteWithLogin(API_TAG, response.getId(), userId);
        태그_리스트_출력(userId);
    }
    @Test
    public void 태그를_수정() throws Exception {
        // given
        TagDTO.TagResponse createList = 태그_생성(userId, "tagName1");

        // when
        TagDTO.TagResponse updateList = 태그_수정(userId, "tagName2");

        // then
        assertAll(
                () -> assertThat(!createList.getTagName().equals(updateList.getTagName()))
        );
        System.err.println(updateList.getTagName());

    }
    public void 태그_리스트_출력(Long userId) throws Exception {
        String uri = UriComponentsBuilder.fromUriString(API_TAG)
                .build().toUriString();
        List<TagDTO.TagResponse> reponseList = getListWithLogin(uri, TagDTO.TagResponse.class, userId);
        for (TagDTO.TagResponse r : reponseList) {
            System.err.println("userId: " + userId + " id: "+ r.getId());
        }
    }
}
