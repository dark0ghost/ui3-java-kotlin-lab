package org.dark0ghost.lab_7_8.internet

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.dark0ghost.lab_7_8.data.GlobalConst
import org.dark0ghost.lab_7_8.parser.getATag
import java.net.URL
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedQueue

object Crawler : CalculateDep {
    private val cacheList = ConcurrentHashMap<String, UInt>()


    private suspend fun algorithm(url: String, port: Int): MutableList<String> {
        val wrapper = try {
            RequestWrapper(url, port)
        } catch (e: Exception) {
            println("url $url not valid")
            return mutableListOf()
        }
        val html = try {
            wrapper.getHtml()
        } catch (e: java.io.IOException) {
            println("site $url is not response")
            return mutableListOf()
        }

        val links = getATag(html)
        links.removeAll(listOf(""))
        links.removeAll(listOf(" "))
        return links
    }

    fun request(url: String, port: Int = 80, maxDepth: UInt = 0u, maxCacheLinks: Int) = runBlocking {
        val links = ConcurrentLinkedQueue<String>()
        val res = algorithm(url, port)
        links.addAll(res)
        println(links.size)

        repeat(maxDepth.toInt()) {
            launch(coroutineContext + Dispatchers.IO) {
                links.forEach { link ->
                    launch(coroutineContext + Dispatchers.IO) {
                        if (!(link.startsWith(GlobalConst.URL_PREFIX) or link.startsWith(GlobalConst.URL_PREFIX_SECURITY))) return@launch
                        if (cacheList.putIfAbsent(
                                URL(link).host,
                                it.toUInt() + 1u
                            ) != null
                        ) return@launch// ссылка не уникальная
                        println(URL(link).host)
                        val result = algorithm(link, 80)
                        val indexRes = if (result.size > maxCacheLinks) maxCacheLinks else result.size
                        links.addAll(result.slice(0 until indexRes))
                    }
                }
            }
        }
    }

    override fun getSites(): List<Pair<String, UInt>> = cacheList.map { it.key to it.value }.sortedBy { it.second }
}