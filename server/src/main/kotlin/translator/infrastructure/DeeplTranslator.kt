package translator.infrastructure

import org.apache.http.client.utils.URIBuilder
import org.springframework.boot.json.JsonParserFactory
import translator.TranslationService
import translator.UnableToTranslateException
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse


class DeeplTranslator(val httpClient: HttpClient, val apiKey: String) : TranslationService {
    private val deeplAPI = URI.create("https://api-free.deepl.com/v2/translate")

    override fun translate(text: String, langFrom: String, langTo: String): String {
        val requestURI = URIBuilder(deeplAPI)
            .setParameter("auth_key", apiKey)
            .setParameter("text", text)
            .setParameter("source_lang", langFrom)
            .setParameter("target_lang", langTo)
            .build()

        val request = HttpRequest.newBuilder().GET().uri(requestURI).build()

        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

        if (response.statusCode() != 200) {
            throw UnableToTranslateException(response.body())
        }

        val parser = JsonParserFactory.getJsonParser()
        val map = parser.parseMap(response.body())

        try {
            val translation = ((map["translations"] as List<*>)[0] as Map<*, *>)["text"].toString()
            return translation
        } catch (e: Exception) {
            throw UnableToTranslateException(e)
        }
    }
}

