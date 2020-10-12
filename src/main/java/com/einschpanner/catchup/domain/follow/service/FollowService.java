package com.einschpanner.catchup.domain.follow.service;

import com.einschpanner.catchup.domain.follow.domain.Follow;
import com.einschpanner.catchup.domain.follow.repository.FollowQueryRepository;
import com.einschpanner.catchup.domain.follow.repository.FollowRepository;
import com.einschpanner.catchup.domain.user.dao.UserRepository;
import com.einschpanner.catchup.domain.user.domain.User;
import com.einschpanner.catchup.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final FollowQueryRepository followQueryRepository;

    /**
     * 토글 following, follower ( 팔로우 추가, 삭제 )
     */
    @Transactional
    public void toggle(Long followerId, Long followingId) {

        User follower = userRepository.findById(followerId)
                .orElseThrow(UserNotFoundException::new);
        User following = userRepository.findById(followingId)
                .orElseThrow(UserNotFoundException::new);

        Follow follow = followQueryRepository.exists(followerId, followingId);
        if (ObjectUtils.isEmpty(follow)){
            follower.plusFollowingCount();
            following.plusFollowerCount();
            followRepository.save(Follow.of(follower, following));
        } else {
            follower.minusFollowingCount();
            following.minusFollowerCount();
            followRepository.deleteById(follow.getFollowId());
        }
    }

    /**
     * 특정 ID가 팔로잉하는 사용자 리스트 불러오기
     */
    @Transactional
    public List<Follow> findAllFollowing(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        return followQueryRepository.findAllFollowingByFollowerId(userId);
    }

    /**
     * 특정 ID의 팔로워 리스트 불러오기
     */
    @Transactional
    public List<Follow> findAllFollower(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        return followQueryRepository.findAllFollowerByFollowingId(userId);
    }
}
