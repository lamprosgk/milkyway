package me.lamprosgk.milkyway.ui

interface BasePresenter<T> {
    fun setView(view: T?)
    fun onDestroy()
}