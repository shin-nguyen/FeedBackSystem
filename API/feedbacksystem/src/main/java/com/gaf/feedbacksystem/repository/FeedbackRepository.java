package com.gaf.feedbacksystem.repository;

import java.util.List;

import javax.transaction.Transactional;

import com.gaf.feedbacksystem.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback,Integer> {
	
	@Query("from Feedback where isDeleted=false")
    List<Feedback> findAllByDeletedIsFalse();

    Feedback save(Feedback feedback);
    
    Feedback findByFeedbackID(Integer feedbackID);
    @Transactional
    @Modifying
    @Query("update Feedback set isDeleted =true where feedbackID = :id ")
    void deleteByFeedbackId(@Param("id") Integer id);
}
