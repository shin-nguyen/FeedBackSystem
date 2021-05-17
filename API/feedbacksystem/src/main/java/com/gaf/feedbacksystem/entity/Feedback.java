package com.gaf.feedbacksystem.entity;


import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Feedback {
    @Id
    private Integer feedbackID;

    private  String title;

    @ManyToOne
    @JoinColumn(name = "adminID", nullable = false,referencedColumnName = "username")
    private Admin admin;
    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "typeFeedBackId",referencedColumnName = "typeID")
    private TypeFeedback typeFeedback;


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // Quan hệ n-n với đối tượng ở dưới (Person) (1 địa điểm có nhiều người ở)
    @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
    @ToString.Exclude // Khoonhg sử dụng trong toString()

    @JoinTable(name = "feedback_question", //Tạo ra một join Table tên là ""
            joinColumns = @JoinColumn(name = "feedbackID"),  // TRong đó, khóa ngoại chính là  trỏ tới class hiện tại ()
            inverseJoinColumns = @JoinColumn(name = "questionID") //Khóa ngoại thứ 2 trỏ tới thuộc tính ở dưới
    )
    private Collection<Question> questions;

}
