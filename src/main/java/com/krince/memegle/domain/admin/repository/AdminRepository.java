package com.krince.memegle.domain.admin.repository;

import com.krince.memegle.domain.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByAdminIdAndPassword(String adminId, String password);
}
