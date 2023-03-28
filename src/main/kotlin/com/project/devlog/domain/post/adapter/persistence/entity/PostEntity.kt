package com.project.devlog.domain.post.adapter.persistence.entity

import com.project.devlog.domain.account.adapter.persistence.entity.AccountEntity
import com.project.devlog.global.entity.BaseDateEntity
import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "post")
class PostEntity(
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)", nullable = false, name = "post_idx")
    val idx: UUID,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    var content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_idx")
    val account: AccountEntity,

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "post_tag", joinColumns = [JoinColumn(name = "post_idx")])
    var tag: List<String>,

    @Column(nullable = false, columnDefinition = "TEXT")
    val thumbnailUrl: String?
): BaseDateEntity()
