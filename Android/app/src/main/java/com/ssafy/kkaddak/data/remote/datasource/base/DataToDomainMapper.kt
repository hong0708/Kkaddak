package com.ssafy.kkaddak.data.remote.datasource.base

interface DataToDomainMapper<T> {
    fun toDomainModel(): T
}