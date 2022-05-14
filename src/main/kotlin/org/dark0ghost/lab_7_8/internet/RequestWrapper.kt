package org.dark0ghost.lab_7_8.internet

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.apache.*
import io.ktor.client.request.*
import org.dark0ghost.lab_7_8.data.GlobalConst.URL_PREFIX
import org.dark0ghost.lab_7_8.data.GlobalConst.URL_PREFIX_SECURITY
import java.net.MalformedURLException

class RequestWrapper(private val url: String, private val port: Int) {
    private val client = HttpClient(Apache)

    init {
        if (!(url.startsWith(URL_PREFIX) or url.startsWith(URL_PREFIX_SECURITY))) {
            throw MalformedURLException("url must start with $URL_PREFIX or $URL_PREFIX_SECURITY: $url")
        }
    }

    suspend fun getHtml(): String
       = client.get(url).body()

}