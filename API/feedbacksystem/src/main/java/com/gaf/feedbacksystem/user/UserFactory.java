package com.gaf.feedbacksystem.user;

import com.gaf.feedbacksystem.constant.SystemConstant;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class UserFactory {
    private final static Map<String, Supplier<BaseUser>> map = new HashMap<>();
    static {
        map.put(SystemConstant.ADMIN_ROLE, AdminUser::new);
        map.put(SystemConstant.TRAINEE_ROLE, TraineeUser::new);
    }
    private  UserFactory(){

    }
    public static final BaseUser getUser(String userType) {
        Supplier<BaseUser> user = map.get(userType);
        if (user == null) {
            throw new IllegalArgumentException("This user type is unsupported");
        }
        return user.get();
    }

}
