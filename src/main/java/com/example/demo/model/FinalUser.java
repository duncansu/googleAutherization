package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.*;


@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "final_user")
public class FinalUser implements UserDetails {
    @Column(name = "id", nullable = false)
    @Id
    @Schema(description = "系統自行產生的ID碼")
    private UUID uuid;

    @Schema(description = "the email of user")
    @Column(name = "email")
    private String email;

    @JsonIgnore
    @Column(name = "passwordDigest")
    @Schema(description = "email of user")
    private String passwordDigest;
    @Column(name = "name")
    @Schema(description = "the name of user")
    private String name;
    @Schema(description = "系統自行產生的secret 密鑰")
    private String secret;
    @Schema(description = "判斷是否有登入過，可否直接進行驗證")
    private boolean status;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_user"));
        return authorities;
    }


    @JsonIgnore
    @Override
    public String getPassword() {
        return getPasswordDigest();
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return getUuid().toString();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        FinalUser finalUser = (FinalUser) o;
        return uuid != null && Objects.equals(uuid, finalUser.uuid);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
