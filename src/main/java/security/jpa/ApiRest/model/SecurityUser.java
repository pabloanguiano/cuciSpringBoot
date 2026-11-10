package security.jpa.ApiRest.model;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SecurityUser implements UserDetails {

    private final UserModel user;

    public SecurityUser(UserModel user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //this building rewturn is for only return form bd a single or more roles
      /*  return Arrays.stream(user
                .getRoles()
                .split(","))
                        .map(SimpleGrantedAuthority::new)
                .toList();*/

        String roleName = switch (user.getRoles()) {
            case 1 -> "ADMIN";
            case 2 -> "CAPTURE";
            case 3 -> "CONSULT";
            default -> throw new IllegalStateException("Unexpected user role: " + user.getRoles());
        };
        // Return it as a single-element list
        return List.of(new SimpleGrantedAuthority(roleName));
    }

    @Override
    public @Nullable String getPassword() {
        return user.getPass();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
