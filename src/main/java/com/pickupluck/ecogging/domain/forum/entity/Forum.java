package com.pickupluck.ecogging.domain.forum.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pickupluck.ecogging.domain.BaseEntity;
import com.pickupluck.ecogging.domain.plogging.entity.Accompany;
import com.pickupluck.ecogging.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "id", callSuper = false)
public class Forum extends BaseEntity {
    @Id // PK 매핑
    @Column(name = "forum_id") // 'forum_id' 컬럼에 매핑
    @GeneratedValue(strategy= GenerationType.IDENTITY) // auto_increment에 PK 설정 위임
    private Long id; // Froum 글 ID

    @Column(nullable = false) // null 불가능 -> 기본값 0 설정됨
    private String type; // Forum 글 타입 (후기/경로/나눔)

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "writer_id")
    private User writer; // Forum 테이블의 writer_id : FK -> User 테이블의 PK인 userId 가져와서 저장

    @Column(nullable = false)
    private String title; // 글 제목

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String content; // 글 내용

    @Column(nullable = false)
    private Integer views; // 글 조회수 (기본값 0)

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private ForumFile file; // 나눔 - 첨부파일 -> DB: ForumFile PK인 첨부파일 ID 저장

    @Column(name = "file_id", insertable = false, updatable = false)
    private Long fileId; // 나눔 - 첨부파일 ID

    @JsonIgnore
    @Column(name = "is_temporary", nullable = false)
    private Boolean isTemporary; // 임시저장 여부 (기본값 0 == false)

    @Column(name = "route_location")
    private String routeLocation; // 경로추천 - 위치 (카카오맵)

    @Column(name = "route_location_detail")
    private String routeLocationDetail; // 경로추천 - 상세 위치

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accomp_id")
    private Accompany thisAccompany; // 후기 - 해당 플로깅 모임글 -> DB: Accomapny PK인 글 ID 저장

    @Column(name="status")
    private String status;  //무료나눔 - 진행상황 (진행증, 완료)

    @Builder
    public Forum(Long id, String type, User writer, String title, String content, Integer views, ForumFile file, Boolean isTemporary, String routeLocation, String routeLocationDetail, Accompany thisAccompany, String status) {
        this.id = id;
        this.type = type;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.views = views;
        this.file = file;
        this.isTemporary = isTemporary;
        this.routeLocation = routeLocation;
        this.routeLocationDetail = routeLocationDetail;
        this.thisAccompany = thisAccompany;
        this.status=status;
    }

    public Boolean getIsTemporary() {
        return this.isTemporary;
    }

}
