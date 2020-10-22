package com.einschpanner.catchup.user.service;

import com.einschpanner.catchup.user.exception.UserNotFoundException;
import com.einschpanner.catchup.domain.user.dao.UserRepository;
import com.einschpanner.catchup.domain.user.domain.User;
import com.einschpanner.catchup.domain.user.dto.ProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public ProfileDto.UpdateRes saveProfile(Long userId, ProfileDto.UpdateReq updateReq) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.update(updateReq);
        return new ProfileDto.UpdateRes(user);
    }

    @Transactional
    public ProfileDto.Res getProfile(long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return new ProfileDto.Res(user);
    }
}
