package com.yuvaraj.financialManagement.repositories;

import com.yuvaraj.financialManagement.models.db.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    @Query("SELECT c FROM UserEntity c WHERE c.email = ?1 and c.status = ?2")
    UserEntity findByEmailAndStatus(String email, String status);

    @Query("SELECT c FROM UserEntity c WHERE c.email = ?1 and c.type = ?2 and c.subtype = ?3 and c.status in ?4")
    UserEntity findByEmailTypeSubtypeAndStatuses(String email, String type, String subtype, List<String> status);

    @Query("SELECT c FROM UserEntity c WHERE c.email = ?1")
    UserEntity findByEmail(String email);


    @Query("SELECT c FROM UserEntity c JOIN FETCH c.passwordEntity WHERE c.email = ?1")
    UserEntity findByEmailWithPassword(String email);
}
