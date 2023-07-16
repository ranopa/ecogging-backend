package com.pickupluck.ecogging.domain.notification.entity;

import com.pickupluck.ecogging.domain.BaseEntity;
import com.pickupluck.ecogging.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @Column(name = "is_read")
    private Boolean isRead;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "target_id")
    private Long targetId;

    @Enumerated(value = EnumType.STRING)
    private NotificationType type;

    @Builder
    public Notification(User sender, User receiver, Boolean isRead, Boolean isDeleted, Long targetId, NotificationType type) {
        this.sender = sender;
        this.receiver = receiver;
        this.isRead = isRead;
        this.isDeleted = isDeleted;
        this.targetId = targetId;
        this.type = type;
    }
}
