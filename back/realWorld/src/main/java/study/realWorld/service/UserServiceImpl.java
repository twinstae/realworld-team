package study.realWorld.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import study.realWorld.entity.User;
import study.realWorld.repository.UserRepository;

@Component
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Autowired// userRepository 주입
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void join(User user) {

//        if(userRepository.findBy){
//
//        }
    }

    @Override
    public User findUser(Long userId) {
        return null;
    }
}
