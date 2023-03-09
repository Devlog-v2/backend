package com.project.hexagonal.domain.post.application.usecase

import com.project.hexagonal.domain.post.application.port.QueryPostPort
import com.project.hexagonal.global.annotation.ReadOnlyUseCase

@ReadOnlyUseCase
class QueryAllPostUseCase(
    private val queryPostPort: QueryPostPort
) {


}