
package com.gaf.feedbacksystem.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Trainee{

    @Id
    @Column(name = "username",length = 50)
    private  String userName;
    @Column(name = "name",length = 50)
    private String name;
    @Column(name = "email",length = 50)
    private  String email;
    private String password;

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
    private Set<Class> classes = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trainee trainee = (Trainee) o;

        return userName.equals(trainee.userName);
    }

    @Override
    public int hashCode() {
        return userName.hashCode();
    }
}