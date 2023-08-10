package com.phen.shopr.domain.user;

public record UserVO(long id, String firstName, String lastName, String username, String email,
String bio, String image) {
    public UserVO(User user) {
        this(user.getId(),
                user.getUsername(), user.getLastName(), user.getUsername(), user.getEmail(),
                 user.getBio(), user.getImage());
    }
}
