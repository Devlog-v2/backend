package com.project.devlog.global.entity

import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class BaseDateEntity {

    @Column(nullable = false, updatable = false)
    val createdAt: LocalDate = LocalDate.now()

}