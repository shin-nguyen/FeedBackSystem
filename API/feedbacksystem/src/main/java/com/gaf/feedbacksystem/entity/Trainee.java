
package com.gaf.feedbacksystem.entity;


import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Trainee  extends BaseUserEntity{


    private String phone;
    private String address;
    private boolean isActive;
    private Integer idSkill;
    private String activationCode;
    private String resetPasswordCode;

    // mappedBy trỏ tới tên biến  ở trong .
    @ManyToMany(mappedBy = "trainees")
    // LAZY để tránh việc truy xuất dữ liệu không cần thiết. Lúc nào cần thì mới query
    @EqualsAndHashCode.Exclude

    private Collection<Class> classes;

}