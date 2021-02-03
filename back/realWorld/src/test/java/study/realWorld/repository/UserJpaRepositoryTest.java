package study.realWorld.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.realWorld.entity.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserJpaRepositoryTest {

    User user1 = User
            .builder()
            .userName("user1")
            .email("user1@user.com")
            .build();

    User user2 = User
            .builder()
            .userName("user2")
            .email("user2@user.com")
            .build();

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void tearDown() throws Exception {
        userRepository.deleteAll();
    }

    @Test
    public void CreateUserTest() throws Exception {
        //CREATE Test
        userRepository.save(user1);
        User findUser1 = userRepository.findOneByEmail(user1.getEmail());

        assertUserIsEqualTo(findUser1, user1);
    }

    private void assertUserIsEqualTo(User actual, User expected) {
        assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
        assertThat(actual.getUserName()).isEqualTo(expected.getUserName());
    }

    @Test
     public void ReadUserTest() throws Exception{
         userRepository.save(user1);
         userRepository.save(user2);

         //READ TEST
         List<User> all = userRepository.findAll();
         assertThat(all.size()).isEqualTo(2);

         long count = userRepository.count();
         assertThat(count).isEqualTo(2);

     }

    @Test
    public void DeleteUserTest() throws Exception{
        userRepository.save(user1);
        //DELETE TEST
        userRepository.delete(user1);

        long deletedCount = userRepository.count();
        assertThat(deletedCount).isEqualTo(0);
    }
}