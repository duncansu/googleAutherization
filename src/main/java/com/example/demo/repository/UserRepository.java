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


    @Query(value = "select b from FinalUser b where b.email = ?1")
    Optional<FinalUser> findByEmail(String email);

    @Modifying
    @Query(value = "delete from final_user b where b.name= ?1", nativeQuery = true)
    void deleteByName(String name);

    @Modifying
    @Query(value = "update final_user b set b.email= ?1 where b.name= ?2 ", nativeQuery = true)
    List<FinalUser> UpdateTheInformation(String name, String email);

    @Query("select b from FinalUser b where b.name = ?1")
    Optional<FinalUser> findByName(String author);

    @Modifying
    @Query(value = "update final_user b set b.status= ?1 where b.name= ?2 ", nativeQuery = true)
    void updatestatus(boolean status, String name);


}