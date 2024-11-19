package me.khw7385.click_clean.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Article extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false)
    private String title;

    @Column(nullable=false, columnDefinition = "TEXT")
    private String body;
    @Column(nullable=false, columnDefinition = "TEXT")
    private String summary;
    @Column(nullable=false)
    private String media;
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Category category;
    @Column(nullable=false)
    private String author;
    @Column(nullable = false)
    private String url;

    @Column(nullable = false, precision = 5, scale = 3)
    private BigDecimal probability;

    @OneToMany(mappedBy = "article")
    private List<Vote> votes = new ArrayList<>();


    @Builder
    public Article(Long id, String title, String body, String summary, String media, Category category, String author, String url, BigDecimal probability) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.summary = summary;
        this.media = media;
        this.category = category;
        this.author = author;
        this.url = url;
        this.probability = probability;
    }
}
