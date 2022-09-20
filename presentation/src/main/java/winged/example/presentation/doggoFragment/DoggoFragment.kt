package winged.example.presentation.doggoFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
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
                    Glide.with(requireContext()).load(response.message).into(binding.imageView)
                    binding.button.isEnabled = true // allows for another call after one is finished
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
