import Quille.Q1
import Quille.Q10
import Quille.Q2
import Quille.Q7
import Quille.Q9
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

class LancerTest {

    @Test
    fun `score pour plusieurs quilles tombees`() {
        Lancer(quillesTombees = setOf(Q1, Q9)).score shouldBe 2
        Lancer(quillesTombees = setOf(Q1, Q9, Q2, Q7, Q10)).score shouldBe 5
        Lancer(quillesTombees = setOf(Q1, Q9, Q2)).score shouldBe 3
        Lancer(quillesTombees = setOf(Q1, Q9, Q9)).score shouldBe 2 // TODO
    }
}