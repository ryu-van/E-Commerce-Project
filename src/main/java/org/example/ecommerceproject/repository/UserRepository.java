package org.example.ecommerceproject.repository;

import org.example.ecommerceproject.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public  interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u LEFT JOIN u.roles r " +
            "WHERE u.fullname LIKE %:keyword% " +
            "OR u.email LIKE %:keyword% " +
            "OR u.phone LIKE %:keyword% " +
            "OR r.name LIKE %:keyword%")
    Page<User> findByFullnameOrEmailOrPhoneOrRole(@Param("keyword") String keyword, Pageable pageable);

}
