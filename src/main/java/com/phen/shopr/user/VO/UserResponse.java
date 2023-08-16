package com.phen.shopr.user.VO;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserResponse {
    private long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String bio;
    private String image;
}
