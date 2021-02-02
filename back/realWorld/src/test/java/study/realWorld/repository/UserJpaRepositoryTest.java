package study.realWorld.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.realWorld.entity.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserJpaRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    //꺼내서 시작전 초기화해서 고용으로 쓰고 싶은데.. 어찌해야 할 지..
//    @BeforeEach
//    void init() {
//        User User1 = User
//                .builder()
//                .userName("User1")
//                .email("user1@user.com")
//                .build();
//
//        User User2 = User
//                .builder()
//                .userName("User2")
//                .email("user2@user.com")
//                .build();
//
//        //CREATE Test
//        userRepository.save(User1);
//        userRepository.save(User2);
//    }


    @Test
    public void CRUDUserTest() throws Exception {
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

        //CREATE Test
        userRepository.save(user1);
        userRepository.save(user2);

        User findUser1 = userRepository.findById(user1.getId()).get();
        User findUser2 = userRepository.findById(user2.getId()).get();

        assertThat(findUser1).isEqualTo(user1);
        assertThat(findUser2).isEqualTo(user2);


        //READ TEST
        List<User> all = userRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        long count = userRepository.count();
        assertThat(count).isEqualTo(2);


        //DELETE TEST
        userRepository.delete(user1);
        userRepository.delete(user2);

        long deletedCount = userRepository.count();
        assertThat(deletedCount).isEqualTo(0);
     }

     @Test
     void findByEmailTest() throws Exception {
         User user1 = User
                 .builder()
                 .userName("User1")
                 .email("user1@user.com")
                 .build();


         //이렇게 변수로 하는게 좋을까?
         boolean TF = userRepository.findByEmail("user1@user.com");
         assertThat(TF).isTrue();

         userRepository.delete(user1);

         //이렇게 합치는게 좋을까?
         assertThat(userRepository.findByEmail("user1@user.com")).isFalse();
      }

}