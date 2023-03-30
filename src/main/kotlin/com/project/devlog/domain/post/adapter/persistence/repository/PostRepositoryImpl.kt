package com.project.devlog.domain.post.adapter.persistence.repository

import com.project.devlog.domain.account.adapter.persistence.entity.QAccountEntity.accountEntity
import com.project.devlog.domain.post.adapter.persistence.entity.QPostEntity.postEntity
import com.project.devlog.domain.post.adapter.presentation.data.response.PostCalendarResponse
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.*

@Repository
class PostRepositoryImpl(
    private val queryFactory: JPAQueryFactory
): PostRepositoryCustom {

    override fun countByOneYearAgo(accountIdx: UUID): List<PostCalendarResponse> =
        queryFactory.from(postEntity)
            .select(
                Projections.constructor(
                    PostCalendarResponse::class.java,
                    postEntity.count(),
                    postEntity.createdAt
                )
            )
            .join(postEntity.account, accountEntity)
            .where(
                postEntity.createdAt.after(LocalDate.now().minusYears(1).atStartOfDay())
                    .and(accountEntity.idx.eq(accountIdx))
            )
            .groupBy(postEntity.createdAt)
            .orderBy(postEntity.createdAt.desc())
            .fetch()

}