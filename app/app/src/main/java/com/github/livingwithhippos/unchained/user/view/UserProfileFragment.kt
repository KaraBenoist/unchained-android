package com.github.livingwithhippos.unchained.user.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.github.livingwithhippos.unchained.authentication.viewmodel.AuthenticationViewModel
import com.github.livingwithhippos.unchained.databinding.FragmentUserProfileBinding
import com.github.livingwithhippos.unchained.user.viewmodel.UserProfileViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [UserProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserProfileFragment : Fragment() {

    private lateinit var viewModel: UserProfileViewModel

    val args: UserProfileFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserProfileViewModel::class.java)
        viewModel.initRepository(args.token)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val userBinding = FragmentUserProfileBinding.inflate(inflater, container, false)

        viewModel.fetchUserInfo()

        viewModel.userLiveData.observe(viewLifecycleOwner, Observer {

            if (it != null)
                userBinding.user = it
            //todo: manage null
        })

        return userBinding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment.
         *
         * @return A new instance of fragment UserProfile.
         */
        @JvmStatic
        fun newInstance() =
            UserProfileFragment()
                .apply {
                }
    }
}