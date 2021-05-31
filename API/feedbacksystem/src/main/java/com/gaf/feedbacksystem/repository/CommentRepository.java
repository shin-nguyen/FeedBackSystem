package com.gaf.feedbacksystem.repository;

import com.gaf.feedbacksystem.entity.Class;
import com.gaf.feedbacksystem.entity.TraineeComment;
import com.gaf.feedbacksystem.entity.TraineeCommentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<TraineeComment, TraineeCommentId> {

}
