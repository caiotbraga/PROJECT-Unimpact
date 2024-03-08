package unicap.br.unimpact.api.config.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import unicap.br.unimpact.domain.entities.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Builder
public class UserDetailsImpl implements UserDetails {

    private Long id;

    private String name;

    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;


    public UserDetailsImpl(Long id, String name, String email, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (String function : user.getFunctions()) {
            authorities.add(new SimpleGrantedAuthority(function));
        }

        return new UserDetailsImpl(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public String getEmail() {
        return this.email;
    }

    public String getName() {
        return this.name;
    }

    public Long getId() {
        return this.id;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object other) {
        if (Objects.isNull(other) || getClass() != other.getClass())
            return false;

        UserDetailsImpl user = (UserDetailsImpl) other;
        return Objects.equals(email, user.email);
    }
}