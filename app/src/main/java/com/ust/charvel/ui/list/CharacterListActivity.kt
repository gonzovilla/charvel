package com.ust.charvel.ui.list

import android.content.Intent
import android.os.Bundle
import android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ust.charvel.R
import com.ust.charvel.databinding.ActivityCharacterListBinding
import com.ust.charvel.model.LocalCharacter
import com.ust.charvel.network.Status
import com.ust.charvel.ui.BaseActivity
import com.ust.charvel.ui.detail.CharacterDetailActivity
import com.ust.charvel.utils.CustomDialog
import com.ust.charvel.viewmodel.CharacterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterListActivity : BaseActivity() {

    companion object {
        private val TAG = CharacterListActivity::class.qualifiedName
    }

    private val characterViewModel: CharacterViewModel by viewModels()

    private lateinit var binding: ActivityCharacterListBinding

    private lateinit var adapter: CharacterListAdapter

    private lateinit var localCharacterListObservable: LiveData<Status<List<LocalCharacter>>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.charactersList.setEmptyListViews(binding.emptyListDescription, binding.refreshButton)
        binding.refreshButton.setOnClickListener { getCharactersList() }

        binding.charactersList.setProgressBar(binding.progressBar)

        adapter = CharacterListAdapter()
        adapter.onCharacterClicked = {
            val intent = Intent(this, CharacterDetailActivity::class.java)
            intent.putExtra(CharacterDetailActivity.KEY_CHARACTER_ID, it.characterId)
            startActivity(intent)
        }

        binding.charactersList.adapter = adapter
        binding.charactersList.layoutManager = LinearLayoutManager(this)

        setPaginationListener()

        getCharactersList()
    }

    private fun setPaginationListener() {
        binding.charactersList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == SCROLL_STATE_IDLE && !binding.charactersList.isLoading()) {
                    getCharactersList(adapter.itemCount)
                }
            }
        })
    }

    private fun getCharactersList(offset: Int = 0) {

        localCharacterListObservable = characterViewModel.getCharacterList(offset)
        localCharacterListObservable.observe(this, { status ->

            when (status) {
                is Status.Error -> {
                    adapter.replaceItems(emptyList())
                    CustomDialog.show(this, getString(R.string.no_internet))
                }
                is Status.Loading -> {
                    binding.charactersList.showLoading()
                }
                is Status.Success -> {
                    if (status.data.isEmpty()) {
                        CustomDialog.show(this, getString(R.string.load_data_error))
                    }
                    adapter.replaceItems(status.data)
                }
            }
        })
    }


}