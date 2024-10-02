package com.martynov.articles.service;

import com.martynov.articles.models.Person;
import com.martynov.articles.repository.PersonRepository;
import com.martynov.articles.util.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(Person person) {
        person.setIsConfirmed(false);
        String encodedPassword = passwordEncoder.encode(person.getPassword());
        person.setRole(Role.ROLE_USER);
        person.setCreatedAt(new Date());
        person.setPassword(encodedPassword);
        personRepository.save(person);
    }
}
