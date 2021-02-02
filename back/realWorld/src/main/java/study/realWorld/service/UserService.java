package study.realWorld.service;

import study.realWorld.entity.User;

public interface UserService {

    void join(User user);

    User findUser(Long userId);

}
