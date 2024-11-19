package me.khw7385.click_clean.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userTitle;
    @Column(nullable = false, precision = 5, scale = 3)
    private BigDecimal probability;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="article_id", nullable = false)
    private Article article;

    @OneToMany(mappedBy = "comment")
    private List<CommentVote> commentVotes = new ArrayList<>();

    @Builder
    public Comment(Long id, String userTitle, BigDecimal probability, User user, Article article) {
        this.id = id;
        this.userTitle = userTitle;
        this.probability = probability;
        this.user = user;
        this.article = article;
    }
}
