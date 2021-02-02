package study.realWorld.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.realWorld.api.dto.LoginUserResponseDto;
import study.realWorld.entity.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserJpaRepositoryTest {


    @Autowired
    private UserJpaRepository userJpaRepository;

    @Test
    public void CRUDUserTest() throws Exception {
        User User1 = User
                .builder()
                .userName("User1")
                .email("user1@user.com")
                .build();

        User User2 = User
                .builder()
                .userName("User2")
                .email("user2@user.com")
                .build();

        //CREATE Test
        userJpaRepository.save(User1);
        userJpaRepository.save(User2);

        User findMember1 = userJpaRepository.findById(User1.getId()).get();
        User findMember2 = userJpaRepository.findById(User2.getId()).get();

        assertThat(findMember1).isEqualTo(User1);
        assertThat(findMember2).isEqualTo(User2);


        //READ TEST
        List<User> all = userJpaRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        long count = userJpaRepository.count();
        assertThat(count).isEqualTo(2);


        //DELETE TEST
        userJpaRepository.delete(User1);
        userJpaRepository.delete(User2);

        long deletedCount = userJpaRepository.count();
        assertThat(deletedCount).isEqualTo(0);
     }


}