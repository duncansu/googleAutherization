package com.example.demo.repository;

import com.example.demo.model.FinalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Transactional
public interface UserRepository extends JpaRepository<FinalUser, UUID> {


    @Query("select b from FinalUser b where b.email = ?1" )
    Optional<FinalUser> findByEmail(String email);
    @Modifying
    @Query("delete from FinalUser b where b.name= ?1" )
    void deleteByName(String name);
    @Modifying
    @Query("update FinalUser b set b.email= ?1 where b.name= ?2 ")
    List<FinalUser> UpdateTheInformation(String name,String email);

    @Query("select b from FinalUser b where b.name = ?1")
    Optional<FinalUser>findByName(String author);
    @Modifying
    @Query("update FinalUser b set b.status= ?1 where b.name= ?2 ")
    void updatestatus(boolean aa,String name);


}