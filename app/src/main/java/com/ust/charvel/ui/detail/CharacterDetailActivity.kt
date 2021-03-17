package com.ust.charvel.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.ust.charvel.R
import com.ust.charvel.databinding.ActivityCharacterDetailBinding
import com.ust.charvel.network.Status
import com.ust.charvel.ui.BaseActivity
import com.ust.charvel.utils.CustomDialog
import com.ust.charvel.utils.gone
import com.ust.charvel.utils.visible
import com.ust.charvel.viewmodel.CharacterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailActivity : BaseActivity() {

    companion object {
        private val TAG = CharacterDetailActivity::class.qualifiedName
        const val KEY_CHARACTER_ID: String = "CHARACTER_ID_KEY"
    }

    private val characterViewModel: CharacterViewModel by viewModels()

    private lateinit var binding: ActivityCharacterDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val characterId = intent.getLongExtra(KEY_CHARACTER_ID, 0)
        getCharacter(characterId)
    }

    private fun getCharacter(characterId: Long) {
        characterViewModel.getCharacterDetail(characterId).observe(this, { status ->

            when (status) {
                is Status.Loading -> binding.dProgressBar.visible()
                is Status.Success -> {
                    binding.dProgressBar.gone()

                    Glide.with(this)
                        .load(status.data.imgUrl)
                        .placeholder(R.drawable.ic_marvel_placeholder)
                        .into(binding.dImageView)

                    binding.dName.text = status.data.name

                    val description = status.data.description
                    if (description.isEmpty()) {
                        binding.dDescription.gone()
                    } else {
                        binding.dDescription.text = description
                    }

                    binding.dComics.text = status.data.comics.toString()
                    binding.dSeries.text = status.data.series.toString()
                    binding.dStories.text = status.data.stories.toString()
                    binding.dEvents.text = status.data.events.toString()

                }
                is Status.Error -> CustomDialog.show(this, getString(R.string.load_data_error))
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}