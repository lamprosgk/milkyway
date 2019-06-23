package me.lamprosgk.milkyway.data.mapper

/**
 * Interface for model mappers to convert from data to domain models
 */
interface Mapper<From, To> {
    fun mapTo(from: List<From>): List<To>
    fun mapTo(from: From): To
}