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
        value = "insert into user(first_name,last_name,user_id) values (:firstName,:lastName,:userId);"
        ,nativeQuery = true
)
void insertUser(@Param("firstName")String firstName, @Param("lastName")String lastName, @Param("userId")String userId);

/*
 * Retrieve user from the userNameuser table.
 */
@Query(
		value= "select * from user where user_id = ?1", nativeQuery = true)
User findByUserName(String userId);

/*
 * delete the specific user from user table.
 */
@Modifying
@Transactional
@Query(
		value = "delete from user where user_Id = ?1", nativeQuery = true)
void deleteByUserName(String userId);
}
