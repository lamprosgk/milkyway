package me.lamprosgk.milkyway.ui

interface BaseView<T> {
    var mPresenter: T
    fun showLoading()
    fun hideLoading()
    fun showError(messageResId: Int)
}