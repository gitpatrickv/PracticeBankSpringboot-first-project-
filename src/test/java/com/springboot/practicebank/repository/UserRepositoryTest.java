package com.springboot.practicebank.repository;


import com.springboot.practicebank.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void itShouldExistsByEmail() {

        User user = User.builder()
                .email("demo1@gmail.com")
                .build();

        User saveUser = userRepository.save(user);

        Assertions.assertThat(saveUser).isNotNull();
        Assertions.assertThat(saveUser).isEqualTo(user);

    }

    @Test
    public void existsByAccountNumber() {

        User user = User.builder()
                .accountNumber("20241842344")
                .build();

        User saveUser = userRepository.save(user);

        Assertions.assertThat(saveUser).isNotNull();
        Assertions.assertThat(saveUser).isEqualTo(user);
    }

    @Test
    public void findByAccountNumber() {
        User user = User.builder()
                .accountNumber("20241842344")
                .build();

        User saveUser = userRepository.save(user);

        Assertions.assertThat(saveUser).isNotNull();
        Assertions.assertThat(saveUser).isEqualTo(user);
    }

    @Test
    public void findByEmail() {

        User user = User.builder()
                .email("demo1@gmail.com")
                .build();

        User saveUser = userRepository.save(user);

        Assertions.assertThat(saveUser).isNotNull();
        Assertions.assertThat(saveUser).isEqualTo(user);

    }

    @Test
    void existsByAtmPin() {

        User user = User.builder()
                .atmPin("1234")
                .build();

        User saveUser = userRepository.save(user);

        Assertions.assertThat(saveUser).isNotNull();
        Assertions.assertThat(saveUser).isEqualTo(user);

    }
}