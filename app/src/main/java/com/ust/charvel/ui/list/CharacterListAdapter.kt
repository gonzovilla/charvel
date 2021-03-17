package com.ust.charvel.ui.list

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ust.charvel.R
import com.ust.charvel.databinding.CharacterListItemBinding
import com.ust.charvel.model.LocalCharacter
import com.ust.charvel.utils.inflate

class CharacterListAdapter : RecyclerView.Adapter<CharacterListAdapter.CharacterHolder>() {

    private var localCharacters: List<LocalCharacter> = emptyList()

    var onCharacterClicked: ((LocalCharacter) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CharacterHolder(parent.inflate(R.layout.character_list_item))

    override fun onBindViewHolder(characterHolder: CharacterHolder, position: Int) =
        characterHolder.bind(localCharacters[position])

    override fun getItemCount() = localCharacters.size

    inner class CharacterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = CharacterListItemBinding.bind(itemView)

        fun bind(localCharacter: LocalCharacter) = with(itemView) {
            binding.name.text = localCharacter.name
            binding.description.text = localCharacter.description

            Glide.with(context)
                .load(localCharacter.imgUrl)
                .placeholder(R.drawable.ic_marvel_placeholder)
                .into(binding.imageView)

            itemView.setOnClickListener {
                onCharacterClicked?.invoke(localCharacter)
            }

        }
    }

    fun replaceItems(items: List<LocalCharacter>) {
        localCharacters = items
        notifyDataSetChanged()
    }

}