package me.lamprosgk.milkyway

import org.mockito.Mockito

// cast null to a type, to bypass Mockito's any() not allowed to return null
fun <T> any(): T = Mockito.any()