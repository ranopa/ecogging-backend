package com.pickupluck.ecogging.domain.plogging.repository;

import com.pickupluck.ecogging.domain.plogging.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    Page<Event> findBySaveFalse(Pageable pageable);

    @Modifying
    @Query(value = "update Event e set e.views = e.views + 1 where e.userId = :id", nativeQuery = true)
    int updateView(@Param("id") Integer id);

    Page<Event> findByUserId(Long userId, PageRequest paging);
    List<Event> findByUserId(Long userId);
    Page<Event> findByUserIdAndSaveTrue(Long userId, PageRequest paging);
    Page<Event> findByUserIdAndSaveFalse(Long userId, PageRequest paging);



    // Main Events
    @Query("SELECT e FROM Event e WHERE e.save = false")
    Page<Event> findAllWithoutTemp(Pageable pageable);
}

