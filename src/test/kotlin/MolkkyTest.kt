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
            val ernest = Joueur("Ernest")
            val jojo = Joueur("Joséphine")
            val julien = Joueur("Julien")

            val maPartie = Partie(joueurs = listOf(ernest, jojo, julien))

            val etatDeLaPartieApres1Coup = maPartie.enregistre(Lancer(q1 = true))
            etatDeLaPartieApres1Coup.joueurCourant shouldBe jojo

            val etatDeLaPartieApres2Coups = maPartie.enregistre(Lancer(q3 = true, q7 = true))
            etatDeLaPartieApres2Coups.joueurCourant shouldBe julien

            val etatDeLaPartieApres3Coups = maPartie.enregistre(Lancer(q3 = true, q7 = true))
            etatDeLaPartieApres3Coups.joueurCourant shouldBe ernest
        }

        @Test
        fun `le score de chaque joueur se cumule en fonctions des lancers`() {
            val ernest = Joueur("Ernest")
            val jojo = Joueur("Joséphine")

            val maPartie = Partie(joueurs = listOf(ernest, jojo))

            maPartie.enregistre(Lancer(q12 = true))
            val etatApres2Coups = maPartie.enregistre(Lancer(q8 = true))

            etatApres2Coups.joueurCourant shouldBe ernest
            etatApres2Coups.scores[ernest] shouldBe 12
            etatApres2Coups.scores[jojo] shouldBe 8

            maPartie.enregistre(Lancer(q12 = true))
            val etatApres4Coups = maPartie.enregistre(Lancer(q1 = true, q4 = true))

            etatApres4Coups.scores[ernest] shouldBe 24
            etatApres4Coups.scores[jojo] shouldBe 10

        }
    }


}