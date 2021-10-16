package translator.infrastructure

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import translator.UnableToTranslateException
import java.net.http.HttpClient


class DeeplTranslatorTest {
    private val apiKey: String = System.getenv("DEEPL_AUTH_KEY")

    @Test
    fun `it must translate hello from english to spanish`() {
        val translator = DeeplTranslator(HttpClient.newHttpClient(), apiKey)
        val translation = translator.translate("hello", "EN", "ES")

        assertEquals("hola", translation)
    }

    @Test
    fun `if the API key specified is not correct it throws an exception`() {
        val apiKey = "invalid API key"

        val translator = DeeplTranslator(HttpClient.newHttpClient(), apiKey);
        assertThrows(UnableToTranslateException::class.java) {
            translator.translate("hello", "EN", "ES")
        }
    }

    @Test
    fun `if the translation languages are not valid it throws an exception`() {
        val translator = DeeplTranslator(HttpClient.newHttpClient(), apiKey)

        assertThrows(UnableToTranslateException::class.java) {
            translator.translate("hello", "EN", "InvalidLanguage")
        }
    }
}