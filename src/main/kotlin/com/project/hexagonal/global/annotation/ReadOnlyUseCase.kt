package com.project.hexagonal.global.annotation

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.Exception

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
annotation class ReadOnlyUseCase
