//package com.example.demo.config;
//
//import com.example.demo.admin.AdminRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CustomUserDetails implements UserDetailsService {
//
//    @Autowired
//    private AdminRepository adminRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // Find the AdminEntity by username
//        // The orElseThrow method returns the AdminEntity object, which implements UserDetails.
//        return adminRepository.findByUsername(username) // <-- Возвращает Optional<AdminEntity>
//                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + username));
//    }
//}