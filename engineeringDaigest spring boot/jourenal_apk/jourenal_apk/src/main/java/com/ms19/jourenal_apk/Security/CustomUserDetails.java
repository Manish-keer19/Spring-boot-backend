package com.ms19.jourenal_apk.Security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ms19.jourenal_apk.entity.journalEntryModel;
import com.ms19.jourenal_apk.entity.userModel;

public class CustomUserDetails implements UserDetails {

    private String username;
    private String password;
    private String email;
    private List<journalEntryModel> journalEntries; // Custom field
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(userModel user) {
        this.username = user.getUserName();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.journalEntries = user.getJournalEntries(); // Custom field
        this.authorities = user.getRole().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role)) // Convert roles to GrantedAuthority
                .toList();
    }

    public String getEmail() {
        return email;
    }

    public List<journalEntryModel> getJournalEntries() {
        return journalEntries;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Customize if you want to add account expiration logic
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Customize if you want to add account locking logic
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Customize if you want to add credential expiration logic
    }

    @Override
    public boolean isEnabled() {
        return true; // Customize if you want to enable/disable users
    }
}
