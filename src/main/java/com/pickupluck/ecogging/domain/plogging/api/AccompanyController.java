package com.pickupluck.ecogging.domain.plogging.api;

import com.pickupluck.ecogging.domain.BoardType;
import com.pickupluck.ecogging.domain.comment.dto.CommentResponse;
import com.pickupluck.ecogging.domain.comment.service.CommentService;
import com.pickupluck.ecogging.domain.forum.service.ForumService;
import com.pickupluck.ecogging.domain.plogging.dto.AccompanyDTO;
import com.pickupluck.ecogging.domain.plogging.service.AccompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AccompanyController {

    private final AccompanyService accompanyService;

    private final CommentService commentService;

    @Autowired
    private ForumService forumService;

    @GetMapping("/accompanies/{page}")
    public ResponseEntity<Map<String,Object>> accompanyList(@PathVariable Integer page,
                                                            @RequestParam("orderby") String orderby) {
        System.out.println(orderby);
        ResponseEntity<List<AccompanyDTO>> res = null;
        try {
            Map<String,Object> map = accompanyService.getAccompanyList(page, orderby);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/accompanieswrite/{temp}")
    public ResponseEntity<String> accompanyWrite(@PathVariable Integer temp, @RequestBody AccompanyDTO accompanyDTO) {
        System.out.println(temp);
        System.out.println(accompanyDTO);

        ResponseEntity<String> res = null;
        try {
            accompanyService.setAccompany(temp, accompanyDTO);
            return new ResponseEntity<>("동행모집 등록", HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/accompaniesmodify")
    public ResponseEntity<String> accompanyModify(@RequestBody AccompanyDTO accompanyDTO) {
        System.out.println(accompanyDTO);

        ResponseEntity<String> res = null;
        try {
            accompanyService.setAccompany(0, accompanyDTO);
            return new ResponseEntity<>("동행모집 등록", HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/accompaniesdetail")
    public ResponseEntity<Map<String,Object>> accompanyDetail(@RequestBody Map<String,Long> param) {
        System.out.println(param);
        ResponseEntity<Map<String,Object>> res = null;
        try {
            Map<String,Object> map = new HashMap<>();
            AccompanyDTO accompanyDTO = accompanyService.getAccompany(param.get("accompanyId"));
            map.put("accompany", accompanyDTO);
            // comment
            List<CommentResponse> comments = commentService
                    .findByBoardTypeAndArticleId(BoardType.ACCOMPANY, param.get("accompanyId"));
            map.put("comments", comments);

            if (param.get("userId") != null) {
                Boolean isParticipation = accompanyService.isParticipation(param.get("userId"), param.get("accompanyId"));
                map.put("isParticipation", isParticipation);
                Boolean isAccompanyscrap = accompanyService.isAccompanyScrap(param.get("userId"), param.get("accompanyId"));
                map.put("isAccompanyscrap", isAccompanyscrap);
            }

            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/accompaniesdelete/{id}")
    public ResponseEntity<Boolean> accompanyDelete(@PathVariable Long id) {
        ResponseEntity<Boolean> res = null;
        try {
            accompanyService.removeAccompany(id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/participation")
    public ResponseEntity<Boolean> participation(@RequestBody Map<String,Long> param) {
        ResponseEntity<Boolean> res = null;
        try {
            Boolean isParticipation =  accompanyService.toggleParticipation(param.get("userId"), param.get("accompanyId"));
            return new ResponseEntity<>(isParticipation, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/accompaniesscrap")
    public ResponseEntity<Boolean> accompanyScrap(@RequestBody Map<String,Long> param) {
        ResponseEntity<Boolean> res = null;
        try {
            Boolean isScrap =  accompanyService.toggleAccompanyScrap(param.get("userId"), param.get("accompanyId"));
            return new ResponseEntity<>(isScrap, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/myaccompanies")
    public ResponseEntity<Map<String,Object>> myAccompanyList(@RequestBody Map<String,Object> param) {
        Long userId = Long.parseLong((String) param.get("userId"));
        Integer page = Integer.valueOf((String)param.get("page"));
        try {
            Map<String,Object> map= accompanyService.getMyAccompanyList(userId, page);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/myaccompaniestemp")
    public ResponseEntity<Map<String,Object>> myAccompanyTempList(@RequestBody Map<String,Object> param) {
        Long userId = Long.parseLong((String) param.get("userId"));
        Integer page = Integer.valueOf((String)param.get("page"));
        try {
            Map<String,Object> map= accompanyService.getMyAccompanyTempList(userId, page);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/myparticipations")
    public ResponseEntity<Map<String,Object>> myParticipationList(@RequestBody Map<String,Object> param) {
        Long userId = Long.parseLong((String) param.get("userId"));
        Integer page = Integer.valueOf((String)param.get("page"));
        try {
            Map<String,Object> map= accompanyService.getMyParticipationList(userId, page);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/myaccompaniesscrap")
    public ResponseEntity<Map<String,Object>> myAccompanyscrapList(@RequestBody Map<String,Object> param) {
        Long userId = Long.parseLong((String) param.get("userId"));
        Integer page = Integer.valueOf((String)param.get("page"));
        try {
            Map<String,Object> map= accompanyService.getMyAccompanyscrapList(userId, page);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/myforum")
    public ResponseEntity<Map<String,Object>> getMyForum(@RequestBody Map<String,Object> param) {
        Long userId = Long.valueOf((String)param.get("userId"));
        Integer page = Integer.valueOf((String)param.get("page"));
        String order = (String)param.get("order");
        try {
            Map<String, Object> res = forumService.getMyForumList(userId, page, order);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/reviewsdelete/{forumId}")
    public ResponseEntity<Boolean> myForumDelete(@PathVariable Long forumId) {
        try {
            forumService.myForumDelete(forumId);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}