import io.kotlintest.matchers.types.shouldBeTypeOf
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

            etatDeLaPartieApres1Coup.shouldBeTypeOf<PartieEnCours> {
                it.joueurCourant shouldBe jojo
            }

            val etatDeLaPartieApres2Coups = maPartie.enregistre(Lancer(q3 = true, q7 = true))
            etatDeLaPartieApres2Coups.shouldBeTypeOf<PartieEnCours> {
                it.joueurCourant shouldBe julien
            }

            val etatDeLaPartieApres3Coups = maPartie.enregistre(Lancer(q3 = true, q7 = true))
            etatDeLaPartieApres3Coups.shouldBeTypeOf<PartieEnCours> {
                it.joueurCourant shouldBe ernest
            }
        }

        @Test
        fun `le score de chaque joueur se cumule en fonctions des lancers`() {
            val ernest = Joueur("Ernest")
            val jojo = Joueur("Joséphine")

            val maPartie = Partie(joueurs = listOf(ernest, jojo))

            maPartie.enregistre(Lancer(q12 = true))
            val etatApres2Coups = maPartie.enregistre(Lancer(q8 = true))

            etatApres2Coups.shouldBeTypeOf<PartieEnCours> {
                it.joueurCourant shouldBe ernest
                it.scores[ernest] shouldBe 12
                it.scores[jojo] shouldBe 8
            }

            maPartie.enregistre(Lancer(q12 = true))
            val etatApres4Coups = maPartie.enregistre(Lancer(q1 = true, q4 = true))

            etatApres4Coups.shouldBeTypeOf<PartieEnCours> {
                it.scores[ernest] shouldBe 24
                it.scores[jojo] shouldBe 10
            }

        }

        @Test
        fun `si un joueur depasse 50 points il retombe a 25`() {
            val ernest = Joueur("Ernest")
            val maPartie = Partie(joueurs = listOf(ernest))

            maPartie.enregistre(Lancer(q12 = true))
            maPartie.enregistre(Lancer(q12 = true))
            maPartie.enregistre(Lancer(q12 = true))
            maPartie.enregistre(Lancer(q12 = true)) // 48
            val etatATester = maPartie.enregistre(Lancer(q3 = true))  // 48 + 3 = 51 ! dommage !

            etatATester.shouldBeTypeOf<PartieEnCours> {
                it.scores[ernest] shouldBe 25
            }

        }

        @Test
        fun `si un joueur arrive pile a 50 points il gagne et la partie s'arrete`() {
            val ernest = Joueur("ernest")
            val maPartie = Partie(joueurs = listOf(ernest))

            maPartie.enregistre(Lancer(q12 = true))
            maPartie.enregistre(Lancer(q12 = true))
            maPartie.enregistre(Lancer(q12 = true))
            maPartie.enregistre(Lancer(q12 = true))
            val etatFinal = maPartie.enregistre(Lancer(q2 = true)) // 48 + 2 = 50 : gagné !

            etatFinal shouldBe PartieTerminee(vainqueur = ernest)
        }

        @Test
        fun `si un joueur fait 3 blancs consecutifs il est elimine`() {
            val ernest = Joueur("ernest")
            val jojo = Joueur("jojo")

            val maPartie = Partie(joueurs = listOf(ernest, jojo))

            maPartie.enregistre(Lancer())
            maPartie.enregistre(Lancer(q1 = true))
            maPartie.enregistre(Lancer())
            maPartie.enregistre(Lancer(q1 = true))
            val etatATester = maPartie.enregistre(Lancer())

            etatATester.shouldBeTypeOf<PartieEnCours> {
                it.joueurCourant shouldBe jojo
                it.scores shouldBe mapOf(jojo to 2)
            }
        }
    }


}