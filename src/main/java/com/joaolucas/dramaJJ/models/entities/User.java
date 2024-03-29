package com.joaolucas.dramaJJ.models.entities;

import com.joaolucas.dramaJJ.models.enums.Gender;
import com.joaolucas.dramaJJ.models.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "tb_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "profile_pic_img_url")
    private String profilePicImgUrl;

    @Column(name = "bio")
    private String bio;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @ElementCollection
    private List<Drama> favoriteDramas = new ArrayList<>();

    @ElementCollection
    private List<Drama> planToWatch = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    private List<Review> reviews = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "followed_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    private List<User> followers = new ArrayList<>();

    @ManyToMany(mappedBy = "followers")
    private List<User> following = new ArrayList<>();

    @ManyToMany(mappedBy = "followers")
    private List<Actor> followingActors = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
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
}
