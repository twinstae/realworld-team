package study.realWorld.service;

import org.springframework.stereotype.Service;
import study.realWorld.api.dto.userDtos.UserDto;
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
    public UserDto save(UserSignUpDto userSignUpDto) {
        checkUserAlreadyExist(userSignUpDto);
        User user = userRepository.save(userSignUpDto.toEntity());
        return UserDto
                .builder()
                .username(user.getUserName())
                .email(user.getEmail())
                .build();
    }

    private void checkUserAlreadyExist(UserSignUpDto userSignUpDto) {
        userRepository.findByEmail(userSignUpDto.getEmail());
        // todo : 에러를 띄운다
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
