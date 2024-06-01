package com.krince.memegle.global.security;

import com.krince.memegle.admin.domain.admin.entity.Admin;
import com.krince.memegle.admin.domain.admin.repository.AdminRepository;
import com.krince.memegle.client.domain.user.entity.User;
import com.krince.memegle.client.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public CustomUserDetails loadUserById(Long id) throws UsernameNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException(""));

        return new CustomUserDetails(user.getId(), user.getRole());
    }

    public CustomUserDetails loadAdminById(Long id) throws UsernameNotFoundException {
        Admin admin = adminRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException(""));

        return new CustomUserDetails(admin.getId(), admin.getRole());
    }
}
