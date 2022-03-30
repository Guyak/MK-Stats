package fr.harmoniamk.statsmk.model

import fr.harmoniamk.statsmk.R
import fr.harmoniamk.statsmk.database.model.TOTAL_TRACK_SCORE
import fr.harmoniamk.statsmk.database.model.WarTrack

data class MKWarTrack(val track: WarTrack?) {

    val opponentScore: Int?
        get() {
            track?.teamScore?.takeIf { it != 0 }?.let {
                return TOTAL_TRACK_SCORE - it
            }
            return null
        }

    val diffScore: Int?
        get() {
            track?.teamScore?.let { teamScore ->
                opponentScore?.let {
                    return teamScore - it
                }
            }
            return null
        }
    val displayedResult: String
        get() = "${track?.teamScore} - $opponentScore"

    val displayedDiff: String
        get() {
            diffScore?.let {
                return if (it > 0) "+$it" else "$it"
            }
            return ""
        }

    val backgroundColor : Int
        get() {
            diffScore?.let {
                return when {
                    it > 0 -> R.color.win
                    it < 0 -> R.color.lose
                    else -> R.color.waluigi_second
                }
            }
            return R.color.waluigi_second
        }

}