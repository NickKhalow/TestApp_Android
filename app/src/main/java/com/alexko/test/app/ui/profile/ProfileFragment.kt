package com.alexko.test.app.ui.profile

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.alexko.test.app.R
import com.alexko.test.app.databinding.FragmentProfileBinding
import com.alexko.test.app.dc.WeatherData
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.util.regex.Pattern

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var binding: FragmentProfileBinding

    private lateinit var progressDialog: ProgressDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view).apply {
            val pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")
            password.editText!!.doAfterTextChanged {
                password.isErrorEnabled = it != null && pattern.matcher(it).find()
            }
            button.setOnClickListener {
                viewModel.onLogin()
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.events.collect { event ->
                when (event) {
                    is ProfileViewModel.Event.SendRequest -> showDialog()
                    is ProfileViewModel.Event.SuccessResponse -> {
                        dismissDialog()
                        showSnackbar(event.data)
                    }
                    is ProfileViewModel.Event.FailureResponse -> {
                        dismissDialog()
                        Snackbar.make(
                            requireView(),
                            getString(R.string.network_error, event.errorCode),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    private fun showDialog() {
        progressDialog = ProgressDialog.show(
            requireContext(),
            getString(R.string.auth),
            getString(R.string.auth_wait)
        )
    }

    private fun showSnackbar(data: WeatherData?) {
        Snackbar.make(
            requireView(),
            if (data != null) getString(
                R.string.weather_info,
                data.name!!,
                data.main!!.temp!!,
                data.clouds!!.all!!,
                data.main.humidity
            ) else getString(R.string.no_info),
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun dismissDialog() {
        progressDialog.dismiss()
    }
}