import io.kotlintest.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class MolkkyTest {

    @Nested
    @DisplayName("Le score doit être")
    inner class CalculDeScore {

        @Test
        fun `le nombre de quilles pour plusieurs quilles tombees`() {
            Lancer(q1 = true, q9 = true).score shouldBe 2
            Lancer(q1 = true, q9 = true, q2 = true, q7 = true, q10 = true).score shouldBe 5
            Lancer(q1 = true, q9 = true, q2 = true).score shouldBe 3
            Lancer(q1 = true, q9 = true).score shouldBe 2
        }

        @Test
        fun `la valeur de la quille pour une seule quille tombee`() {
            Lancer(q1 = true).score shouldBe 1
            Lancer(q5 = true).score shouldBe 5
            Lancer(q12 = true).score shouldBe 12
        }

        @Test
        fun `nul pour un echec`() {
            Lancer().score shouldBe 0
        }


    }

    @Nested
    @DisplayName("Déroulement de la partie")
    inner class DeroulementDeLaPartie {

        @Test
        fun `les joueurs lancent chacun a leur tour`() {
            val maPartie = Partie(
                joueurs = listOf(
                    Joueur("Ernest"),
                    Joueur("Joséphine"),
                    Joueur("Julien")
                )
            )

            val etatDeLaPartieApres1Coup = maPartie.enregistre(Lancer(q1 = true))
            etatDeLaPartieApres1Coup.joueurCourant shouldBe Joueur("Joséphine")

            val etatDeLaPartieApres2Coups = maPartie.enregistre(Lancer(q3 = true, q7 = true))
            etatDeLaPartieApres2Coups.joueurCourant shouldBe Joueur("Julien")

            val etatDeLaPartieApres3Coups = maPartie.enregistre(Lancer(q3 = true, q7 = true))
            etatDeLaPartieApres3Coups.joueurCourant shouldBe Joueur("Ernest")

        }

    }


}