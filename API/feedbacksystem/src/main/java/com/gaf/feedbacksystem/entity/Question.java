package com.gaf.feedbacksystem.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer questionID;

    @ManyToOne
    @JoinColumn(name = "topicId")
    private Topic topic;

    private  String questionContent;
    private  boolean isDeleted;



    // mappedBy trỏ tới tên biến  ở trong .
    @ManyToMany(mappedBy = "questions")
    // LAZY để tránh việc truy xuất dữ liệu không cần thiết. Lúc nào cần thì mới query
    @EqualsAndHashCode.Exclude
    private Collection<Feedback> feedbacks;
}
