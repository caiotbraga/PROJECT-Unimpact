package unicap.br.unimpact.service.services.others;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import unicap.br.unimpact.api.config.security.jwt.JwtUtils;
import unicap.br.unimpact.api.config.security.services.UserDetailsImpl;
import unicap.br.unimpact.api.dtos.request.LoginRequest;
import unicap.br.unimpact.api.dtos.response.JwtResponse;
import unicap.br.unimpact.domain.entities.University;
import unicap.br.unimpact.domain.entities.User;
import unicap.br.unimpact.repository.JuridicalPersonRepository;
import unicap.br.unimpact.repository.PhysicalPersonRepository;
import unicap.br.unimpact.repository.UniversityRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private PhysicalPersonRepository physicalPersonRepository;

    @Autowired
    private JuridicalPersonRepository juridicalPersonRepository;

    @Autowired
    private UniversityRepository universityRepository;

    @Value("${unicap.email}")
    private String universityAccount;

    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getName(),
                userDetails.getEmail(),
                roles));
    }

    @Transactional
    public User getCurrentUser() {
        Optional<? extends User> userOptional;

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetailsImpl) {
            userOptional = physicalPersonRepository.findByEmail(((UserDetailsImpl) principal).getEmail());
            if (userOptional.isEmpty()) {
                userOptional = juridicalPersonRepository.findByEmail(((UserDetailsImpl) principal).getEmail());
                if (userOptional.isEmpty()) {
                    userOptional = universityRepository.findByEmail(((UserDetailsImpl) principal).getEmail());
                }
            }

            return userOptional.orElse(null);
        }

        return null;
    }


    public void encryptPassword(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
    }

    public User getUserByEmail(String email) {
        Optional<? extends User> userOptional = physicalPersonRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            userOptional = juridicalPersonRepository.findByEmail(email);
            if (userOptional.isEmpty()) {
                userOptional = universityRepository.findByEmail(email);
            }
        }
        return userOptional.orElse(null);
    }


    public List<String> getFunctions() {
        User user = this.getCurrentUser();
        return user.getFunctions();
    }


    public University getUniversityUnicap() {
        return this.universityRepository.findByEmail(universityAccount).orElse(null);
    }
}
