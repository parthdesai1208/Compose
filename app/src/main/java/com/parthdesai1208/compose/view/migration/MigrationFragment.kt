 package com.parthdesai1208.compose.view.migration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.databinding.FragmentMigrationBinding
import com.parthdesai1208.compose.view.theme.ComposeTheme


class MigrationFragment : Fragment() {
    private var _binding: FragmentMigrationBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMigrationBinding.inflate(layoutInflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtCenter.text = getString(R.string.hello_i_am_view)
        binding.composeViewFr.apply {
            //always include strategy, if you use ComposeView in xml(view based)
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                ComposeTheme {
                    Text(
                        text = stringResource(id = R.string.hello_i_am_from_compose),
                        color = MaterialTheme.colors.onSurface,
                        modifier = Modifier
                            .wrapContentWidth(align = Alignment.CenterHorizontally)
                            .wrapContentHeight()
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}