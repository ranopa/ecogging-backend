package com.pickupluck.ecogging.domain.plogging.dto;

import com.pickupluck.ecogging.domain.BaseEntity;
import com.pickupluck.ecogging.domain.plogging.entity.Event;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class EventDTO extends BaseEntity {
    private Integer eventId ;
    private String title;
    private String content;
    private LocalDate meetingDate;
    private LocalDate endDate;
    private Boolean activate;
    private Integer views;
    private String corpName;
    private Integer userId;
    private Long fileId;
    private String location;
    private String explanation;
    private LocalDateTime createdAt;
    private Boolean save;

    public EventDTO(Event event) {
        this.title = title;
        this.content = content;
        this.corpName = corpName;
        this.location = location;
        this.explanation = explanation;
    }
}
