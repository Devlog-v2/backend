package com.project.devlog.domain.like.adapter.persistence.entity

import com.project.devlog.domain.account.adapter.persistence.entity.AccountEntity
import com.project.devlog.domain.post.adapter.persistence.entity.PostEntity
import com.project.devlog.global.entity.BaseTimeEntity
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*

@Entity
@Table(name = "like")
class LikeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_idx", nullable = false)
    val idx: Long,
    val isLiked: Boolean,
    @ManyToOne
    val account: AccountEntity,
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val post: PostEntity
): BaseTimeEntity()