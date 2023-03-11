package com.project.devlog.global.annotation

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@Component
@Transactional(readOnly = true, rollbackFor = [Exception::class])
annotation class ReadOnlyUseCase
