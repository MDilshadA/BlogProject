package com.myblog.myblog.repository;

import com.myblog.myblog.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    // Custom methods if needed

    Optional<Role> findByName(String name);
}
