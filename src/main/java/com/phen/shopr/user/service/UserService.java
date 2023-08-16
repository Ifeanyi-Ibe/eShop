package com.phen.shopr.user.service;

import com.phen.shopr.user.VO.UserResponse;
import com.phen.shopr.user.dto.SignUpRequest;
import com.phen.shopr.user.model.User;
import com.phen.shopr.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User signUp(SignUpRequest request) {
        if(userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email `%s` already exists".formatted(request.email()));
        }

        if(userRepository.existsByUsername(request.username())) {
            throw new IllegalArgumentException("Username `%s` is already taken".formatted(request.username()));
        }

        User newUser = this.createNewUser(request);
        return userRepository.save(newUser);
    }

    @Transactional
    public List<Object> getAllStudents() {
        var users = userRepository.findAll();

        if(users.size() == 0)  {
            return List.of();
        }

        return users.stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public User getUser(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    @Transactional
    public User updateUser(User user, long userId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with id `%s` not found".formatted(userId)));

        existingUser.setImage(user.getImage());
        existingUser.setBio(user.getBio());

        return userRepository.save(existingUser);
    }

    public void deleteUser(long userId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with id `%s` not found".formatted(userId)));

        userRepository.delete(existingUser);
    }

    private User createNewUser(SignUpRequest request) {
        return User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .username(request.username())
                .email(request.email())
                .password(request.password())
                .build();
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .bio(user.getBio())
                .image(user.getImage())
                .build();
    }
}
