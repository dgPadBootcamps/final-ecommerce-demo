package com.dgpad.ecommerceuserdemo.service;

import com.dgpad.ecommerceuserdemo.model.User;
import com.dgpad.ecommerceuserdemo.repository.UserRepository;
import com.dgpad.ecommerceuserdemo.security.UserInfoDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<User> findUserByUserName(String username){
        return userRepository.findUserByUserName(username);
    }

    public Boolean userNameExists(String username){ return userRepository.existsByUsername(username);}

    public Optional<User> findUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }

    public Boolean userEmailExists(String email){ return userRepository.existsByEmail(email);}

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(UUID id){
        return userRepository.findById(id).orElse(null);
    }

    public User createUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(UUID id, String userName, String password, String address, boolean enable){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            user.get().setUsername(userName);
            user.get().setAddress(address);
            user.get().setEnabled(enable);
            if(!password.isEmpty())
                user.get().setPassword(passwordEncoder.encode(password));
            return userRepository.save(user.get());
        }
        return null;
    }

    public User updateUserByAdmin(UUID id, String userName, String password, String email, String roles, String address, boolean enable){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            user.get().setUsername(userName);
            user.get().setPassword(passwordEncoder.encode(password));
            user.get().setEmail(email);
            user.get().setRoles(roles);
            user.get().setAddress(address);
            user.get().setEnabled(enable);
            return userRepository.save(user.get());
        }
        return null;
    }

    public User deActivateUser(UUID id){
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(value -> value.setEnabled(false));
        return user.orElse(null);
    }

    public User activate(UUID id){
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(value -> value.setEnabled(true));
        return user.orElse(null);
    }

    public boolean validId(UserDetails userDetails, String id){
        UserInfoDetails userInfoDetails = (UserInfoDetails) userDetails;
        return userInfoDetails.getUserId().equals(id);
    }
}
