package com.project.hexagonal.domain.post.adapter.persistence.entity

import com.project.hexagonal.domain.account.adapter.persistence.entity.AccountEntity
import com.project.hexagonal.global.entity.BaseTimeEntity
import org.hibernate.annotations.GenericGenerator
import java.util.UUID
import javax.persistence.*

@Entity
@Table(name = "post")
class PostEntity(
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)", nullable = false, name = "post_idx")
    val idx: UUID,
    var title: String,
    var content: String,
    @ManyToOne
    @JoinColumn(name = "idx")
    val account: AccountEntity,
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "post_tag", joinColumns = [JoinColumn(name = "post_idx")])
    var tag: MutableList<String>
): BaseTimeEntity()

fun PostEntity.toUpdate(title: String, content: String, tag: MutableList<String>) {
    this.title = title
    this.content = content
    this.tag = tag
}