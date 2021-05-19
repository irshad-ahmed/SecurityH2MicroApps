package com.irsplay.patient.service;

import com.irsplay.patient.model.ApplicationUser;
import com.irsplay.patient.repo.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserAuthService implements UserDetailsService {

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<ApplicationUser> user = applicationUserRepository.findById(userName);
        if (user.isPresent()) {
            return new User(user.get().getMobile(), user.get().getPassword(), new ArrayList<>());
        }
        //return new User("foo", "foo", new ArrayList<>());
        return null;
    }
}