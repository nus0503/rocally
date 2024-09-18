package com.company.rocally.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("select u from User u where u.email = :email")
    User findByEmailAsObject(@Param("email") String email);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u where u.username = :name AND u.phoneNumber = :phoneNumber")
    Optional<User> findIdAsNameAndPhone(@Param("name") String name, @Param("phoneNumber") String phoneNumber);

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.username = :name AND u.phoneNumber = :phoneNumber")
    Optional<User> findByEmailAndNameAndPhoneNumber(@Param("email") String email, @Param("name") String name, @Param("phoneNumber") String phoneNumber);
}
