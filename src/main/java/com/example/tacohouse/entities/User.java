package com.example.tacohouse.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Users") //cause user is reserved as table name and gives error
@JsonIgnoreProperties(value = {"authorities"}, ignoreUnknown = true) //remove authorities field from serialization due to it being abstract class provided by spring security, getting errors due to enable field which is unrecognized
//@RestResource(rel="users", path="users")

public class User implements UserDetails{ //spring uses UserDetailService which uses UserDetails to fetch password and username.
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER) //to fetch it together with the user entity //used instead of @jointable (used when using simple data types like strings and not other entities)
    @CollectionTable(name = "User_roles_Ref", //used with elementalCollection to edit the table
            joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role") //changes name of column in elementCollection not main entity
    private Set<String> roles = new HashSet<>();

    private String fullName;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phoneNumber;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    private List<TacoOrder> tacoOrders = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toSet());
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



    public User(String username,String password,Set roles){ //to add user without the info
        this.username=username;
        this.password=password;
        this.roles=roles;
    }

    public User(String username, String password, Set roles, String fullName, String street, String city, String state, String zip,String phoneNumber) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.fullName = fullName;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNumber=phoneNumber;
    }

    public User(String username, String password, Set roles, String fullName){
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.fullName = fullName;
    }
    public void addTacoOrder(TacoOrder tacoOrder) {
        tacoOrders.add(tacoOrder);
    }
}
