package winged.example.presentation.doggoFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import winged.example.presentation.GlideApp
import winged.example.presentation.R
import winged.example.presentation.databinding.FragmentDoggoBinding

@AndroidEntryPoint
class DoggoFragment : Fragment(R.layout.fragment_doggo) {

    // creating a VM instance like this is so easy that it feels wrong
    // but I just cannot get over how clean this is
    private val viewModel: DoggoFragmentViewModel by viewModels()

    // dataBinding
    private var _binding: FragmentDoggoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_doggo, container, false)
        binding.doggoFragmentVariable = this
        return binding.root
    }

    fun buttonRequestNewDoggo() {
        binding.button.isEnabled = false // prevents making multiple calls before one is finished
        viewModel
            .makeRequest()
            .subscribe(
                { response ->
                    // this "success" could be replaced by an enum
                    if (response.status == "success") {
                        // attempt to load the image only if the API call was successful
                        // and to clarify: it will not be successful only if something happens
                        // on the API's side (and I do not mean 400 or 503 result codes, those are
                        // handled by our request interceptor)
                        GlideApp.with(requireContext()).load(response.message).into(binding.imageView)
                    }
                    // allows for another call after the previous one is finished
                    binding.button.isEnabled = true
                },
                { throwable ->
                    Toast.makeText(requireContext(), throwable.message, Toast.LENGTH_SHORT).show()
                }
            )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
