package com.github.livingwithhippos.unchained.torrentdetails.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.github.livingwithhippos.unchained.databinding.FragmentTorrentDetailsBinding
import com.github.livingwithhippos.unchained.torrentdetails.viewmodel.TorrentDetailsViewmodel

class TorrentDetailsFragment : Fragment() {

    private val viewModel: TorrentDetailsViewmodel by viewModels()

    val args: TorrentDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val torrentBinding = FragmentTorrentDetailsBinding.inflate(inflater, container, false)

        viewModel.torrentLiveData.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty())
                torrentBinding.torrent = it.first()
        })

        viewModel.fetchTorrentDetails(args.torrentID)
        return torrentBinding.root
    }
}