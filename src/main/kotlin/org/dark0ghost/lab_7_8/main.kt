package org.dark0ghost.lab_7_8

import org.dark0ghost.lab_7_8.internet.Crawler
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val (url: String, depth: String) = args
    val maxCacheLinks: Int = args.getOrNull(3)?.toInt() ?: 100
    Crawler.request("https://ktor.io/changelog/1.5/", 80, 3u, maxCacheLinks)
    println("site ${Crawler.getSites()}")
    println("site ${Crawler.getSites().size}")
    exitProcess(1)
}
