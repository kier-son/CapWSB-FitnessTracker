package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.SimpleUserEmailDto;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.stereotype.Component;

@Component
public class SimpleUserEmailMapper {
    SimpleUserEmailDto toSimpleUserEmailDto(User user) {
        return new SimpleUserEmailDto(user.getId(), user.getEmail());
    }
    
    User toUser(SimpleUserEmailDto userDto) {
        return new User(null, null, null, userDto.email());
    }
}
