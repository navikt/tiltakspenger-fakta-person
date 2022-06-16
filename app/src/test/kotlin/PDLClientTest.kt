import arrow.core.Either
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.assertions.fail
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import no.nav.tiltakspenger.fakta.person.pdl.PDLClient
import no.nav.tiltakspenger.fakta.person.pdl.PDLClientError
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test

class PDLClientTest {

    private fun mockClient(response: String): HttpClient {
        val mockEngine = MockEngine() {
            respond(
                content = response,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }
        return HttpClient(mockEngine) {
            install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }
        }
    }

    @Test
    fun `should be able to serialize non-errors`() {
        val response = this::class.java.getResource("pdlResponse.json").readText()
        val pdlClient = PDLClient(mockClient(response))

        runBlocking {
            pdlClient.hentPerson("test")
        }.shouldBeRight()
    }

    @Test
    fun `should be able to serialize errors`() {
        val response = this::class.java.getResource("pdlErrorResponse.json").readText()
        val pdlClient = PDLClient(mockClient(response))

        runBlocking {
            pdlClient.hentPerson("test")
        }.shouldBeLeft()
    }

    @Test
    fun `should map serialization errors`() {
        val pdlClient = PDLClient(mockClient("""{ "lol": "lal" }"""))

        runBlocking {
            pdlClient.hentPerson("test")
        }
            .mapLeft { it shouldBeSameInstanceAs PDLClientError.SerializationException(IllegalStateException("asdsa")) }
            .map { _ -> fail("Serialization of bad payload should result in an error") }
    }
}
