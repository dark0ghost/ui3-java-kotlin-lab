package org.dark0ghost.lab_7_8.data

object GlobalConst {
    const val URL_PREFIX = "http://"
    const val URL_PREFIX_SECURITY = "https://"
    val regexAref = "<a href=\"[какой либо URL начинающийся с http://]\">".toRegex()
}