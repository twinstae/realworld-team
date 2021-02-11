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
    public UserDto signUp(UserSignUpDto userSignUpDto) {
        checkUserAlreadyExist(userSignUpDto);

        User user = userRepository.save(userSignUpDto.toEntity(passwordEncoder));
        return UserDto
                .builder()
                .username(user.getUserName())
                .email(user.getEmail())
                .build();
    }

    private void checkUserAlreadyExist(UserSignUpDto userSignUpDto) {
        userRepository.findByEmail(userSignUpDto.getEmail())
                .ifPresent((user)->{
                    throw new RuntimeException(user.getEmail() + "은 가입되어 있는 이메일입니다.");
                });
    }
}
