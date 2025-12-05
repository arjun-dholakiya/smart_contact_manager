package com.example.smart_contact_manager.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.smart_contact_manager.Dao.UserRepository;
import com.example.smart_contact_manager.entities.User;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       

        // Fetching User From Database
        User user = userRepository.getUserByUserName(username);

        if (user == null) {
            throw new UsernameNotFoundException("Couldn't Found User");
        }
        
       CustomUserDetails customUserDetails = new CustomUserDetails(user);
       return customUserDetails;
    }

}
