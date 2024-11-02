package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.SimpleUserDto;
import com.capgemini.wsb.fitnesstracker.user.api.SimpleUserEmailDto;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;
    private final SimpleUserMapper userSimpleMapper;
    private final SimpleUserEmailMapper userEmailSimpleMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody UserDto userDto) throws InterruptedException {
        User user = null;
        
        try {
            user = userMapper.toEntity(userDto);
            userService.createUser(user);
        } catch (Exception e) {
            throw new IllegalArgumentException("Cannot add user by email: " + userDto.email() 
                    + "error message: " + e.getMessage());
        }
        return user;
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @GetMapping("/simple")
    public List<SimpleUserDto> getAllUsersAsSimple() {
        return userService.findAllUsers()
                .stream()
                .map(userSimpleMapper::toSimpleDto)
                .toList();
    }

    @GetMapping("/email")
    public List<SimpleUserEmailDto> getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmailIgnoreCase(email)
                .stream()
                .map(userEmailSimpleMapper::toSimpleUserEmailDto)
                .toList();
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable Long userId) {
        return userService.getUser(userId)
                .map(userMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("User ID: " + userId + " doesn't exist"));
    }

    @GetMapping("/older/{time}")
    public List<UserDto> getUsersOlderThan(@PathVariable LocalDate time) {
        return userService.getUserOlderThan(time)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        try {
            User foundUser = userService.getUser(userId).orElseThrow(() ->
                    new IllegalArgumentException("User with ID: " + userId + "doesn't exist"));
            User updatedUser = userMapper.toUpdateEntity(userDto, foundUser);
            return userService.updateUser(updatedUser);
        } catch (Exception e) {
            throw new IllegalArgumentException("Cannot update user with ID: " + userId + "error: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
        } catch (Exception e) {
            throw new IllegalArgumentException("Cannot delete user with ID: " + userId + "error: " + e.getMessage());
        }
    }
}