package dev.haskin.cookrecipes.service.auth;

/**
 *
 */
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import dev.haskin.cookrecipes.dto.auth.SignUpRequest;
import dev.haskin.cookrecipes.dto.auth.UserDTO;
import dev.haskin.cookrecipes.model.User;
import dev.haskin.cookrecipes.repository.UserRepository;
import dev.haskin.cookrecipes.security.UserPrincipal;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Jay
 *
 */
@Service
@Slf4j
public class AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserRepository userRepository;

    public User getUser(UserPrincipal user) {
        return userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
    }

    public UserDTO signUp(@Valid SignUpRequest signUpRequest) {

        if (existsByUsername(signUpRequest.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        // Creating user's account
        User user = this.mapper.map(signUpRequest, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return mapper.map(userRepository.save(user), UserDTO.class);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public void deleteById(Long id, UserPrincipal principal) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (!principal.getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        userRepository.deleteById(id);
    }
}
