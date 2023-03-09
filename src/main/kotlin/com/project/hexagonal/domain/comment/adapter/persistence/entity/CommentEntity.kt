package com.project.hexagonal.domain.comment.adapter.persistence.entity

import com.project.hexagonal.domain.account.adapter.persistence.entity.AccountEntity
import com.project.hexagonal.domain.post.adapter.persistence.entity.PostEntity
import com.project.hexagonal.global.entity.BaseTimeEntity
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*

@Entity
class CommentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_idx", nullable = false)
    val idx: Long,
    var comment: String,
    @ManyToOne
    val account: AccountEntity,
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val post: PostEntity
): BaseTimeEntity()