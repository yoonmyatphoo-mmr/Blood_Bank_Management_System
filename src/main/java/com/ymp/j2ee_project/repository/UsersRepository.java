package com.ymp.j2ee_project.repository;

import com.ymp.j2ee_project.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    // @Query(nativeQuery = true, value="select * from users where email=:em and password=:pwd")
    // List<Users> retrieveByEmailAndPwd(@Param("em") String email, @Param("pwd") String password);
    // as you like for the method name


    // if you don't add query, and if you use build in method query, you must need to same with field name which declare in entity class
    List<Users> findByEmailAndPassword(String email, String password);


//  @Query(nativeQuery = true, value="select * from users where email=:em")
//  List<Users> retrieveByEmail(@Param("em") String email); // as you like for the method name

    // if you don't add query, and if you use build in method query, you must need to same with field name which declare in entity class
    List<Users> findByEmail(String email);


}
