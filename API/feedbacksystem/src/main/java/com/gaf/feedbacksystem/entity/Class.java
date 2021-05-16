
package com.gaf.feedbacksystem.entity;

import java.time.LocalDate;
import java.util.Collection;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Generated;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "classID", unique = true, nullable = false)
    private Integer classID;
    private String className;
    private String capacity;

    @JsonFormat(pattern="dd/MM/yyyy")
    @Column(name = "startTime",columnDefinition = "DATE")
    private LocalDate  startTime;
    
    @JsonFormat(pattern="dd/MM/yyyy")
    @Column(name ="endTime",columnDefinition = "DATE")
    private LocalDate  endTime;

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