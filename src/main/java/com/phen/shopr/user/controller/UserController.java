package com.phen.shopr.user.controller;

import com.phen.shopr.user.VO.UserRecord;
import com.phen.shopr.user.service.UserService;
import com.phen.shopr.user.model.User;
import com.phen.shopr.user.VO.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<Object> getAllStudents() {
        return userService.getAllStudents();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserRecord> getUser(@PathVariable("userId") long userId) {
        User user = userService.getUser(userId);
        UserRecord response = new UserRecord(new UserVO(user));

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserRecord> updateUser(@PathVariable("userId") long userId, @RequestBody User userDetails) {
        User user = userService.updateUser(userDetails, userId);
        UserRecord response = new UserRecord(new UserVO(user));

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User successfully deleted.");
    }

}
