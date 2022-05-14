package org.dark0ghost.lab_7_8.internet

sealed interface CalculateDep {
    fun getSites(): List<Pair<String, UInt>>
}