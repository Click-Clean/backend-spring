package me.khw7385.click_clean.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
@Getter
@NoArgsConstructor
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String user_name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String refreshToken;

    @Enumerated(value=EnumType.ORDINAL)
    @Column(nullable = false)
    private SocialLogin social;

    @OneToMany(mappedBy = "user")
    private List<Subscription> subscriptions= new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Vote> votes = new ArrayList<>();

    public enum SocialLogin{
        KAKAO, GOOGLE
    }

    @Builder
    public User(Long id, String user_name, String email, String refreshToken, SocialLogin social) {
        this.id = id;
        this.user_name = user_name;
        this.email = email;
        this.refreshToken = refreshToken;
        this.social = social;
    }
}
