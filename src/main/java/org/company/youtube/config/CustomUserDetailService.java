package org.company.youtube.config;



import lombok.RequiredArgsConstructor;
import org.company.youtube.entity.profile.ProfileEntity;
import org.company.youtube.repository.profile.ProfileRepository;
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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<ProfileEntity> optional = repository.findByEmail(email);
        if (optional.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        ProfileEntity entity = optional.get();
        return new CustomUserDetail(entity);
    }
}
