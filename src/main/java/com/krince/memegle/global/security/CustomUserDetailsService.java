package com.krince.memegle.global.security;

import com.krince.memegle.admin.domain.admin.entity.Admin;
import com.krince.memegle.admin.domain.admin.repository.AdminRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public CustomUserDetails loadUserById(Long id) throws UsernameNotFoundException {
        Admin admin = adminRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException(""));

        return new CustomUserDetails(admin.getId(), admin.getRole());
    }
}
