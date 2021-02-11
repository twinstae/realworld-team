package study.realWorld.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import study.realWorld.api.dto.userDtos.UserDto;
import study.realWorld.api.dto.userDtos.UserSignUpDto;
import study.realWorld.entity.User;
import study.realWorld.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto save(UserSignUpDto userSignUpDto) {
        checkUserAlreadyExist(userSignUpDto);

        User user = userRepository.save(userSignUpDto.toEntity(passwordEncoder));
        return UserDto
                .builder()
                .email(user.getEmail())
                .build();
    }

    private void checkUserAlreadyExist(UserSignUpDto userSignUpDto) {
        if (userRepository.findByEmail(userSignUpDto.getEmail()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }
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
