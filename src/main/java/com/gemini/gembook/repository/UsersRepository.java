package com.gemini.gembook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gemini.gembook.model.User;

@Repository
public interface UsersRepository extends JpaRepository<User, String>{

/*
    Insert user to the user table.
*/
@Modifying
@Transactional
@Query(
        value = "insert into user(first_name,last_name,user_name,passowrd) values (:firstName,:lastName,:userName,:password);"
        ,nativeQuery = true
)
void insertUser(@Param("firstName")String firstName, @Param("lastName")String lastName, @Param("userName")String userName, @Param("password")char[] password);

//@Query("select * from user where userName= :userName;")
//User findByUserName(@Param("userName")String userName);

}
