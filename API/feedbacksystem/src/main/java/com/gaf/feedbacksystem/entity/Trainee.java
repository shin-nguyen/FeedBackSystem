
package com.gaf.feedbacksystem.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Collection;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Trainee  extends BaseUserEntity{

    @Column(length = 50)
    private String phone;
    private String address;
    private boolean isActive;
    private Integer idSkill;
    @Column(length = 50)
    private String activationCode;
    @Column(length = 50)
    private String resetPasswordCode;

    // mappedBy trỏ tới tên biến  ở trong .
    @ManyToMany(mappedBy = "trainees")
    // LAZY để tránh việc truy xuất dữ liệu không cần thiết. Lúc nào cần thì mới query
    @EqualsAndHashCode.Exclude
    private Collection<Class> classes;

}