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

    val score = if (quillesTombees.size == 1) {
        quillesTombees.first().valeur
    } else {
        quillesTombees.size
    }

}

enum class Quille(val valeur: Int) {
    Q1(1), Q2(2), Q3(3), Q4(4), Q5(5), Q6(6), Q7(7), Q8(8), Q9(9), Q10(10), Q11(11), Q12(12)
}

data class Joueur(val nom: String)

class Partie(val joueurs: List<Joueur>) {

    private var tour = 0

    fun enregistre(lancer: Lancer): EtatDePartie {
        tour++
        return EtatDePartie(joueurCourant = joueurs.elementAt(tour % joueurs.size))
    }
}

data class EtatDePartie(val joueurCourant: Joueur)
