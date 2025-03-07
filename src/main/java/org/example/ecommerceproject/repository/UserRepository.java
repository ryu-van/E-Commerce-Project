package org.example.ecommerceproject.repository;

import org.example.ecommerceproject.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

@Repository
public  interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT DISTINCT u FROM User u " +
            "JOIN u.roles r " +
            "WHERE (u.fullname LIKE %:keyword% " +
            "OR u.email LIKE %:keyword% " +
            "OR u.phone LIKE %:keyword%) " +
            "AND r.id != 3")
    Page<User> findByFullnameOrEmailOrPhone(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT DISTINCT u FROM User u " +
            "JOIN u.roles r " +
            "WHERE r.id <> 3")
    Page<User> findUsersByRole(Pageable pageable);

    @Query("SELECT DISTINCT u FROM User u " +
            "JOIN u.roles r " +
            "WHERE (u.fullname LIKE %:keyword% " +
            "OR u.email LIKE %:keyword% " +
            "OR u.phone LIKE %:keyword%) " +
            "AND r.id = 3")
    Page<User> findByFullnameOrEmailOrPhoneClient(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT DISTINCT u FROM User u " +
            "JOIN u.roles r " +
            "WHERE r.id = 3")
    Page<User> findUsersByRoleClient(Pageable pageable);










}
