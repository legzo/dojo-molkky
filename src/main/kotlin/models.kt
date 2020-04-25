import Quille.Q1
import Quille.Q10
import Quille.Q11
import Quille.Q12
import Quille.Q2
import Quille.Q3
import Quille.Q4
import Quille.Q5
import Quille.Q6
import Quille.Q7
import Quille.Q8
import Quille.Q9

class Lancer(
    q1: Boolean = false,
    q2: Boolean = false,
    q3: Boolean = false,
    q4: Boolean = false,
    q5: Boolean = false,
    q6: Boolean = false,
    q7: Boolean = false,
    q8: Boolean = false,
    q9: Boolean = false,
    q10: Boolean = false,
    q11: Boolean = false,
    q12: Boolean = false
) {

    private val quillesTombees = mutableSetOf<Quille>().apply {
        if (q1) add(Q1)
        if (q2) add(Q2)
        if (q3) add(Q3)
        if (q4) add(Q4)
        if (q5) add(Q5)
        if (q6) add(Q6)
        if (q7) add(Q7)
        if (q8) add(Q8)
        if (q9) add(Q9)
        if (q10) add(Q10)
        if (q11) add(Q11)
        if (q12) add(Q12)
    }.toSet()

    private val nombreDeQuillesTombees = quillesTombees.size

    val score = when (nombreDeQuillesTombees) {
        1 -> quillesTombees.first().valeur
        else -> nombreDeQuillesTombees
    }

}

enum class Quille(val valeur: Int) {
    Q1(1), Q2(2), Q3(3), Q4(4), Q5(5), Q6(6), Q7(7), Q8(8), Q9(9), Q10(10), Q11(11), Q12(12)
}

data class Joueur(val nom: String)

class Partie(val joueurs: List<Joueur>) {

    private var tour = 0
    private val scores =
        joueurs.map { it to mutableListOf(0) }.toMap().toMutableMap()

    private val scoreMaximal = 50
    private val scoreDePenalite = 25

    fun enregistre(lancer: Lancer): EtatDePartie {

        val historiqueDuJoueurCourant = scores[joueurCourant()] ?: throw IllegalStateException()

        val nouveauScore = calculeNouveauScore(historiqueDuJoueurCourant.last(), lancer)
        historiqueDuJoueurCourant.add(nouveauScore)

        elimineJoueurCourantSiNecessaire(historiqueDuJoueurCourant)

        tour++

        return if (nouveauScore < scoreMaximal) {
            PartieEnCours(
                joueurCourant = joueurCourant(),
                scores = scores.mapValues { (_, historique) -> historique.last() }
            )
        } else PartieTerminee(vainqueur = joueurCourant())
    }

    private fun elimineJoueurCourantSiNecessaire(historiqueDuJoueurCourant: MutableList<Int>) {
        if (historiqueDuJoueurCourant.size > 3
            && historiqueDuJoueurCourant
                .zipWithNext { current, next -> next - current }
                .takeLast(3)
                .all { it == 0 }
        ) {
            scores.remove(joueurCourant())
        }
    }

    private fun calculeNouveauScore(
        scorePrecedent: Int,
        lancer: Lancer
    ): Int {
        val futurScore = scorePrecedent + lancer.score

        return when {
            futurScore > scoreMaximal -> scoreDePenalite
            else -> futurScore
        }
    }

    private fun joueurCourant() = joueurs.elementAt(tour % joueurs.size)
}

sealed class EtatDePartie

data class PartieEnCours(
    val joueurCourant: Joueur,
    val scores: Map<Joueur, Int>
) : EtatDePartie()

data class PartieTerminee(
    val vainqueur: Joueur
) : EtatDePartie()
