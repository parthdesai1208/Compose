package com.parthdesai1208.compose.view.migration

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.parthdesai1208.compose.R
import com.parthdesai1208.compose.databinding.ActivityMigrationBinding
import com.parthdesai1208.compose.view.theme.ComposeTheme

class MigrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMigrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMigrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var amount by mutableStateOf(0) //here we don't use remember because its not compose fun.
        binding.composeView.setContent {
            ComposeTheme {
                Surface {
                    Text(
                        text = stringResource(id = R.string.hello_i_am_from_compose),
                        modifier = Modifier
                            .wrapContentWidth(align = Alignment.CenterHorizontally)
                            .wrapContentHeight()
                    )
                }
            }
        }

        //region interact compose <-> view
        binding.btnAdd.setOnClickListener {
            amount++
        }
        binding.cvAddBtn.setContent {
            ComposeTheme {
                Surface {
                    Text(text = "Value is updating from view: $amount")
                }
            }
        }
        //endregion

        binding.btnGoToFragment.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    MigrationFragmentActivity::class.java
                )
            )
        }

        binding.btnGoToViewInsideComposeActivity.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    ViewInsideComposeActivity::class.java
                )
            )
        }
    }

}