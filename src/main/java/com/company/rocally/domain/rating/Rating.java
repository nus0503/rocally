package com.company.rocally.domain.rating;

import com.company.rocally.domain.BaseTimeEntity;
import com.company.rocally.domain.travel.Comment;
import com.company.rocally.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ratings")
@Entity
public class Rating extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Float rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    private Rating(Float rating, User user, Comment comment) {
        this.rating = rating;
        this.user = user;
        this.comment = comment;
    }

    public static Rating generateEntity(Float rating, User user, Comment comment) {
        return new Rating(rating, user, comment);
    }
}


