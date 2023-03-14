package com.project.devlog.domain.comment.adapter.persistence.entity

import com.project.devlog.domain.account.adapter.persistence.entity.AccountEntity
import com.project.devlog.domain.post.adapter.persistence.entity.PostEntity
import com.project.devlog.global.entity.BaseTimeEntity
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "comment")
class CommentEntity(
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)", nullable = false, name = "post_idx")
    val idx: UUID,
    var comment: String,
    @ManyToOne(fetch = FetchType.LAZY)
    val account: AccountEntity,
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val post: PostEntity
): BaseTimeEntity()

fun CommentEntity.toUpdate(comment: String) {
    this.comment = comment
}