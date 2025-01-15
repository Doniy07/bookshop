package org.company.bookshop.config;


import lombok.RequiredArgsConstructor;
import org.company.bookshop.api.entity.ProfileEntity;
import org.company.bookshop.api.repository.ProfileRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final ProfileRepository repository;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        Optional<ProfileEntity> optional = repository.findByPhoneAndVisibleTrue(phone);
        if (optional.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        ProfileEntity entity = optional.get();
        return new CustomUserDetail(entity);
    }
}
