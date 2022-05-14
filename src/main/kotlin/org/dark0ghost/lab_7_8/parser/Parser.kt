package org.dark0ghost.lab_7_8.parser

import org.jsoup.Jsoup

fun getATag(html: String): MutableList<String> =
    Jsoup
        .parse(html)
        .select("a[href]")
        .toList()
        .map {
            it.attr("abs:href")
        } as MutableList