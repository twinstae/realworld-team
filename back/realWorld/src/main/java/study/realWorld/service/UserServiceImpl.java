package study.realWorld.service;

import org.springframework.stereotype.Service;
import study.realWorld.api.dto.userDtos.UserSignUpDto;
import study.realWorld.entity.User;
import study.realWorld.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean save(UserSignUpDto userSignUpDto) {
        if(userRepository.findByEmail(userSignUpDto.getEmail()).isEmpty()) {
            userRepository.save(userSignUpDto.toEntity());
            return true;
        }
        return false;
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
