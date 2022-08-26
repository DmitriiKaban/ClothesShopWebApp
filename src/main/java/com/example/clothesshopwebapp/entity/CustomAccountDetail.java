//package com.example.clothesshopwebapp.entity;
//
//import com.example.clothesshopwebapp.entity.Account;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.Set;
//
//public class CustomAccountDetail implements UserDetails {
//    private static final long serialVersionUID = 1L;
//    private Account account;
//    Set<GrantedAuthority> authorities = null;
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return authorities;
//    }
//
//    @Override
//    public String getPassword() {
//        return account.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return account.getFirstName();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return false;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return false;
//    }
//}
