package io.propwave.tree.config.security;

import io.propwave.tree.auth.domain.User;
import io.propwave.tree.auth.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findBySocialInformationEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다."));

        return new CustomUserDetails(user);
    }
}
