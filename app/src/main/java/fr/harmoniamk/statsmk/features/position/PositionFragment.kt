package fr.harmoniamk.statsmk.features.position

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import fr.harmoniamk.statsmk.R
import fr.harmoniamk.statsmk.databinding.FragmentPositionBinding
import fr.harmoniamk.statsmk.extension.backPressedDispatcher
import fr.harmoniamk.statsmk.extension.clicks
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PositionFragment : Fragment(R.layout.fragment_position) {

    private val binding: FragmentPositionBinding by viewBinding()
    private val viewModel: PositionViewModel by viewModels()
    private var track: Int? = null
    private var tmId: Int? = null
    private var warTrackId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tmId = arguments?.getInt("tmId")
        track = arguments?.getInt("track")
        warTrackId = arguments?.getString("warTrackId")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.trackView.bind(track)
        viewModel.bind(
            tournamentId = tmId,
            warTrackId = warTrackId,
            chosenTrack = track ?: -1,
            onPos1 = binding.pos1.clicks(),
            onPos2 = binding.pos2.clicks(),
            onPos3 = binding.pos3.clicks(),
            onPos4 = binding.pos4.clicks(),
            onPos5 = binding.pos5.clicks(),
            onPos6 = binding.pos6.clicks(),
            onPos7 = binding.pos7.clicks(),
            onPos8 = binding.pos8.clicks(),
            onPos9 = binding.pos9.clicks(),
            onPos10 = binding.pos10.clicks(),
            onPos11 = binding.pos11.clicks(),
            onPos12 = binding.pos12.clicks(),
            onBack = requireActivity().backPressedDispatcher(viewLifecycleOwner)
        )

        viewModel.validateTrack
            .filter { findNavController().currentDestination?.id == R.id.positionFragment }
            .onEach { when {
                tmId != null -> findNavController().popBackStack()
                warTrackId != null -> findNavController().popBackStack()
            } }
            .launchIn(lifecycleScope)

        viewModel.sharedQuit
            .filter { findNavController().currentDestination?.id == R.id.positionFragment }
            .onEach { findNavController().popBackStack() }
            .launchIn(lifecycleScope)

        viewModel.sharedGoToResult
            .filter { findNavController().currentDestination?.id == R.id.positionFragment }
            .onEach { findNavController().navigate(PositionFragmentDirections.goToResult(it, track ?: -1)) }
            .launchIn(lifecycleScope)

        viewModel.sharedSelectedPositions
            .onEach {
                showAllPositions()
                it.forEach { pos -> hidePosition(pos) }
            }.launchIn(lifecycleScope)

        viewModel.sharedPlayerLabel
            .onEach { binding.posTitle.text = "Sélectionnez la position de $it" }
            .launchIn(lifecycleScope)
    }

    private fun showAllPositions() {
        binding.pos1.isVisible = true
        binding.pos2.isVisible = true
        binding.pos3.isVisible = true
        binding.pos4.isVisible = true
        binding.pos5.isVisible = true
        binding.pos6.isVisible = true
        binding.pos7.isVisible = true
        binding.pos8.isVisible = true
        binding.pos9.isVisible = true
        binding.pos10.isVisible = true
        binding.pos11.isVisible = true
        binding.pos12.isVisible = true
    }

    private fun hidePosition(position: Int) = when (position) {
        1 -> binding.pos1.visibility = View.INVISIBLE
        2 -> binding.pos2.visibility = View.INVISIBLE
        3 -> binding.pos3.visibility = View.INVISIBLE
        4 -> binding.pos4.visibility = View.INVISIBLE
        5 -> binding.pos5.visibility = View.INVISIBLE
        6 -> binding.pos6.visibility = View.INVISIBLE
        7 -> binding.pos7.visibility = View.INVISIBLE
        8 -> binding.pos8.visibility = View.INVISIBLE
        9 -> binding.pos9.visibility = View.INVISIBLE
        10 -> binding.pos10.visibility = View.INVISIBLE
        11 -> binding.pos11.visibility = View.INVISIBLE
        12 -> binding.pos12.visibility = View.INVISIBLE
        else -> {}
    }
}