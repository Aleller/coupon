package edu.sysu.sdcs.coupon.enums;

import edu.sysu.sdcs.coupon.entity.User;
import edu.sysu.sdcs.coupon.shiro.CustomHeaderSessionManager;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
    CUSTOMER("customer", 0),
    SELLER("saler", 1);

    private String name;
    private int index;

    public static Role getTypeByName(String name){
        for (var enums : Role.values()) {
            if (enums.getName() == name) {
                return enums;
            }
        }
        return null;
    }
}
