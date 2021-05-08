package com.gaf.feedbacksystem.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Set;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Class {
    @Id
    private String classID;
    private String className;
    private String capacity;
    private Date startTime;
    private Date endTime;
    private boolean isDeleted = false;


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // Quan hệ n-n với đối tượng ở dưới (Person) (1 địa điểm có nhiều người ở)
    @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
    @ToString.Exclude // Khoonhg sử dụng trong toString()

    @JoinTable(name = "enrollment", //Tạo ra một join Table tên là ""
            joinColumns = @JoinColumn(name = "classID"),  // TRong đó, khóa ngoại chính là  trỏ tới class hiện tại ()
            inverseJoinColumns = @JoinColumn(name = "traineeID",referencedColumnName = "username") //Khóa ngoại thứ 2 trỏ tới thuộc tính ở dưới
    )
    private Collection<Trainee> trainees;
}
