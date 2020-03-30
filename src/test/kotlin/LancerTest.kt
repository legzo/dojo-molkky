import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

class LancerTest {

    @Test
    fun `score pour plusieurs quilles tombees`() {
        Lancer(q1 = true, q9 = true).score shouldBe 2
        Lancer(q1 = true, q9 = true, q2 = true, q7 = true, q10 = true).score shouldBe 5
        Lancer(q1 = true, q9 = true, q2 = true).score shouldBe 3
        Lancer(q1 = true, q9 = true).score shouldBe 2
    }

    @Test
    fun `score pour une seule quilles tombee`() {
        Lancer(q1 = true).score shouldBe 1
        Lancer(q5 = true).score shouldBe 5
        Lancer(q12 = true).score shouldBe 12
    }

    @Test
    fun `score nul pour un echec`() {
        Lancer().score shouldBe 0
    }
}