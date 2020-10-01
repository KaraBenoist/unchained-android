package com.github.livingwithhippos.unchained.lists.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.github.livingwithhippos.unchained.R
import com.github.livingwithhippos.unchained.data.model.TorrentItem
import com.github.livingwithhippos.unchained.databinding.DialogTorrentItemBinding
import com.github.livingwithhippos.unchained.lists.viewmodel.TorrentDialogViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class TorrentContextualDialogFragment : DialogFragment {

    private var item: TorrentItem? = null

    val viewModel: TorrentDialogViewModel by viewModels()

    constructor(item: TorrentItem) : super() {
        this.item = item
    }

    constructor() : super()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {


            // Use the Builder class for convenient dialog construction
            val builder = MaterialAlertDialogBuilder(it)

            // Get the layout inflater
            val inflater = it.layoutInflater

            val binding = DialogTorrentItemBinding.inflate(inflater)

            var title = ""
            item?.let { item ->
                title = item.filename
                viewModel.setItem(item)
            }

            if (item==null) {
                item = viewModel.getItem()
                title = item?.filename ?: ""
            }

            binding.bDelete.setOnClickListener {
                item?.let { torrent ->
                        setFragmentResult("torrentActionKey", bundleOf("deletedTorrentKey" to torrent.id))
                        dismiss()
                }
            }

            binding.bOpen.setOnClickListener {
                item?.let { torrent ->
                    setFragmentResult("torrentActionKey", bundleOf("openedTorrentItem" to torrent.id))
                    dismiss()
                }
            }

            binding.bDownload.setOnClickListener {
                item?.let { torrent ->
                    setFragmentResult("torrentActionKey", bundleOf("downloadedTorrentItem" to torrent))
                    dismiss()
                }
            }

            builder.setView(binding.root)
                .setTitle(title)
                .setNeutralButton(resources.getString(R.string.close)) { dialog, _ ->
                    dialog.cancel()
                }
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}