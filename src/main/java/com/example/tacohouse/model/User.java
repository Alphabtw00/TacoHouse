package com.example.tacohouse.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Data
@Entity(name = "\"USER\"")
@NoArgsConstructor(access = AccessLevel.PROTECTED,force = true)
@RequiredArgsConstructor
//@RestResource(rel="users", path="users")

public class User implements UserDetails {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private final String username;
    private final String password;
    private final String role;
    private final String fullname;
    private final String street;
    private final String city;
    private final String state;
    private final String zip;
    private final String phoneNumber;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
    }


    @OneToMany
    @JoinTable(
            name = "User_TacoOrder",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tacoOrder_id"))
    private List<TacoOrder> tacoOrders = new ArrayList<>();
    public void addTacoOrder(TacoOrder tacoOrder) {
        tacoOrder.setUser(this);
        tacoOrders.add(tacoOrder);
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
    public User(String username,String password,String role){
        this.username=username;
        this.password=password;
        this.role=role;
        roles.add(role);
        this.fullname="";
        this.street="";
        this.city="";
        this.state="";
        this.zip="";
        this.phoneNumber="";
    }
}
