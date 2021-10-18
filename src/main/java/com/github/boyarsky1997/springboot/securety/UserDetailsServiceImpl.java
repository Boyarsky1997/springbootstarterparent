package com.github.boyarsky1997.springboot.securety;

import com.github.boyarsky1997.springboot.models.Client;
import com.github.boyarsky1997.springboot.repo.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ClientRepo clientRepo;

    @Autowired
    public UserDetailsServiceImpl(ClientRepo clientRepo) {
        this.clientRepo = clientRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Client client = clientRepo.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("User don't exist"));

        return SecurityUser.fromUser(client);
    }
}
