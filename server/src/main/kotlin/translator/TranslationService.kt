package translator

class UnableToTranslateException(message: String?, cause: Throwable?) : Exception(message, cause) {
    constructor(message: String) : this(message, null) {}
    constructor(cause: Throwable) : this(cause.message, cause)
}

interface TranslationService {
    fun translate(text: String, langFrom: String, langTo: String): String
}
