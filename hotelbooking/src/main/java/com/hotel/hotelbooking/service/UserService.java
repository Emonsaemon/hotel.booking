package com.hotel.hotelbooking.service;

import com.hotel.hotelbooking.exception.ElementNotFoundException;
import com.hotel.hotelbooking.model.User;
import com.hotel.hotelbooking.repository.UserRepository;
import com.hotel.hotelbooking.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository repository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository,
                       PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByEmail(String email) {
        Optional<User> user = repository.findUserByEmail(email);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new NoSuchElementException("Email not found!");
        }
    }

    public boolean emailExists(String email) {
        return repository.existsByEmail(email);
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(Long id) {
        return repository.findById(id).get();
    }

    public User getCurrentUser() {
        Object user = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        if (user instanceof String) {
            return null;
        }
        UserDetailsImpl userDetails  = (UserDetailsImpl) user;
        return findByEmail(userDetails.getUsername());
    }

    public User create(User user) {
        user.setId(-1L);
        return repository.save(user);
    }

    public User update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public void deleteById(Long id) throws ElementNotFoundException{
        Optional<User> user = repository.findById(id);
        if (user.isPresent()) {
            repository.delete(user.get());
        } else {
            throw new ElementNotFoundException("User for deletion not found!");
        }
    }
}
