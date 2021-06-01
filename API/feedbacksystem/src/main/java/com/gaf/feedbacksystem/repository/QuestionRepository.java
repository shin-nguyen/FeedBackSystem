package com.gaf.feedbacksystem.repository;

import com.gaf.feedbacksystem.entity.Class;
import com.gaf.feedbacksystem.entity.Question;
import com.gaf.feedbacksystem.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Integer> {

    @Query("from Question  where isDeleted=false")
    List<Question> findAllByDeletedFalse();

    Question findByQuestionID(Integer questionID);
    
    List<Question> findAllByTopic_TopicID(@Param("id") Integer id);

    @Transactional
    @Modifying
    @Query("update Question set isDeleted =true where questionID = :id ")
    void deleteByQuestionId(@Param("id") Integer id);

}
