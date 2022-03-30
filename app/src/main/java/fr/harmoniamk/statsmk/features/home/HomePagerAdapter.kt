package fr.harmoniamk.statsmk.features.home

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import fr.harmoniamk.statsmk.R
import fr.harmoniamk.statsmk.features.home.settings.SettingsFragment
import fr.harmoniamk.statsmk.features.home.tournament.TournamentFragment
import fr.harmoniamk.statsmk.features.home.war.WarFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class HomePagerAdapter(val fa: FragmentActivity) : FragmentStateAdapter(fa) {

    fun getTabTitle(position: Int): String = fa.getString(when (position) {
        //0 -> R.string.tournament
        0 -> R.string.team_war
        else -> R.string.settings
    })

    override fun getItemCount() = 2

    override fun createFragment(position: Int) = when (position) {
        //0 -> TournamentFragment()
        0 -> WarFragment()
        else -> SettingsFragment()
    }
}