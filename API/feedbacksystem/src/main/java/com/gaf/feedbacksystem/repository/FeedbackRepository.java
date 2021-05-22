package com.gaf.feedbacksystem.repository;

import com.gaf.feedbacksystem.entity.Class;
import com.gaf.feedbacksystem.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback,Integer> {

    @Query("from Feedback  where isDeleted=false")
    List<Feedback> findAllByDeletedIsFalse();
    @Transactional
    @Modifying
    @Query("update Feedback set isDeleted =true where feedbackID = :id ")
    void deleteByFeedbackId(@Param("id") Integer id);
}
