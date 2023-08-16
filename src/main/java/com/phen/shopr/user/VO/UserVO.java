package com.phen.shopr.user.VO;

import com.phen.shopr.user.model.User;

public record UserVO(long id, String firstName, String lastName, String username, String email,
                     String bio, String image) {
    public UserVO(User user) {
        this(user.getId(),
                user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail(),
                 user.getBio(), user.getImage());
    }
}
