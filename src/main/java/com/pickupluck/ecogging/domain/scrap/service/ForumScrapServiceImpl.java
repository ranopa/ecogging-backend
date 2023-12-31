package com.pickupluck.ecogging.domain.scrap.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pickupluck.ecogging.domain.forum.repository.ForumRepository;
import com.pickupluck.ecogging.domain.scrap.entity.ForumScrap;
import com.pickupluck.ecogging.domain.scrap.repository.ForumscrapRepository;
import com.pickupluck.ecogging.domain.user.entity.User;
import com.pickupluck.ecogging.domain.user.repository.UserRepository;
import com.pickupluck.ecogging.domain.forum.entity.Forum;
import com.pickupluck.ecogging.domain.scrap.dto.MyForumScrapsResponseDto;


import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@Service
public class ForumScrapServiceImpl implements ForumScrapService{

    @Autowired
    private ForumRepository forumRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    ForumscrapRepository forumscrapRepository;
    
    
    @Override
    public Boolean setForumScrap(Long forumId, Long userId) throws Exception {
        System.out.println("스크랩 서비스");
        Optional<ForumScrap> oscrap = forumscrapRepository.findByForumIdAndUserId(forumId, userId);

        if(oscrap.isEmpty()) {
            System.out.println("디비에 스크랩 없음");
            Forum forum = forumRepository.findById(forumId).get();
            User user=userRepository.findById(userId).get();
            ForumScrap forumscrap = ForumScrap.builder()
                    .forum(forum)
                    .user(user)
                    .build();
            forumscrapRepository.save(forumscrap);
            return true;
        } else {
            forumscrapRepository.delete(oscrap.get());
            return false;
        }
    }

    @Override
    public Boolean isForumScrap(Long forumId, Long userId) throws Exception {
        Optional<ForumScrap> oscrap = forumscrapRepository.findByForumIdAndUserId(forumId, userId);
        if(oscrap.isEmpty()) return false;

        return true;
    }
    
    
  
  
  
    
    

    // MyPage 나의 커뮤니티 - 스크랩 ----------------------------------------------------------------------------------------------------------
    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getMyForumScrapsAll(Long userId, Pageable pageable) {

        // 현재 유저가 스크랩한 글 5개씩 가져오기
        Page<ForumScrap> myForumScrapsEntity = forumscrapRepository.findAllByUserId(userId, pageable);
        // 현재 유저가 스크랩한 모든 글 개수
        Long countScraps = forumscrapRepository.findAllByUserIdForCount(userId);

        // Entity -> DTO
        Page<MyForumScrapsResponseDto> myForumScrapDto = myForumScrapsEntity.map(s -> {
            if (s.getForum().getType().equals("경로")) {
                return MyForumScrapsResponseDto.builder()
                        .scrapId(s.getId())
                        .forumId(s.getForum().getId())
                        .title(s.getForum().getTitle())
                        .content(s.getForum().getContent())
                        .createdAt(s.getCreatedAt()) // 스크랩한 순으로 정렬
                        .views(s.getForum().getViews())
                        .type(s.getForum().getType())
                        .userId(s.getForum().getWriter().getId())
                        .nickname(s.getForum().getWriter().getNickname())
                        .location(s.getForum().getRouteLocation())
                        .build();
            } else if (s.getForum().getType().equals("나눔")) {
                return MyForumScrapsResponseDto.builder()
                        .scrapId(s.getId())
                        .forumId(s.getForum().getId())
                        .title(s.getForum().getTitle())
                        .content(s.getForum().getContent())
                        .createdAt(s.getCreatedAt()) // 스크랩한 순으로 정렬
                        .views(s.getForum().getViews())
                        .type(s.getForum().getType())
                        .status(s.getForum().getStatus())
                        .userId(s.getForum().getWriter().getId())
                        .nickname(s.getForum().getWriter().getNickname())
                        .build();
            }
            return null;
        });

        Map<String, Object> result = new HashMap<>();
        result.put("res", myForumScrapDto);
        result.put("all", countScraps);

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> searchMyForumScraps(Long userId, String criteria, String words, Pageable pageable) {

        Page<ForumScrap> myForumScrapsEntity = null;
        Long countScraps = null;

        // 전체 검색 or 나눔/경로 검색
        if(criteria.equals("전체")) {
            myForumScrapsEntity = forumscrapRepository.findByUserIdAndWords(userId, words, pageable);
            countScraps = forumscrapRepository.findByUserIdAndWordsForCount(userId, words);
        } else {
            myForumScrapsEntity = forumscrapRepository.findByUserIdAndTypeAndWords(userId, criteria, words, pageable);
            countScraps = forumscrapRepository.findByUserIdAndTypeAndWordsForCount(userId, criteria, words);
        }

        // Entity -> DTO
        Page<MyForumScrapsResponseDto> myForumScrapDto = myForumScrapsEntity.map(s -> {
            if (s.getForum().getType().equals("경로")) {
                return MyForumScrapsResponseDto.builder()
                        .scrapId(s.getId())
                        .forumId(s.getForum().getId())
                        .title(s.getForum().getTitle())
                        .content(s.getForum().getContent())
                        .createdAt(s.getCreatedAt()) // 스크랩한 순으로 정렬
                        .views(s.getForum().getViews())
                        .type(s.getForum().getType())
                        .userId(s.getForum().getWriter().getId())
                        .nickname(s.getForum().getWriter().getNickname())
                        .location(s.getForum().getRouteLocation())
                        .build();
            } else if (s.getForum().getType().equals("나눔")) {
                return MyForumScrapsResponseDto.builder()
                        .scrapId(s.getId())
                        .forumId(s.getForum().getId())
                        .title(s.getForum().getTitle())
                        .content(s.getForum().getContent())
                        .createdAt(s.getCreatedAt()) // 스크랩한 순으로 정렬
                        .views(s.getForum().getViews())
                        .type(s.getForum().getType())
                        .status(s.getForum().getStatus())
                        .userId(s.getForum().getWriter().getId())
                        .nickname(s.getForum().getWriter().getNickname())
                        .build();
            }
            return null;
        });

        Map<String, Object> result = new HashMap<>();
        result.put("res", myForumScrapDto);
        result.put("all", countScraps);

        return result;
    }
}
