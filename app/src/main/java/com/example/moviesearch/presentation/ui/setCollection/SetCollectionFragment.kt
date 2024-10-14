package com.example.moviesearch.presentation.ui.setCollection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.moviesearch.R
import com.example.moviesearch.dataBase.App
import com.example.moviesearch.databinding.FragmentSetCollectionBinding
import com.example.moviesearch.presentation.ui.serialPage.SerialPageViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SetCollectionFragment : Fragment() {
    private var nameCollection = ""
    private val viewModel: SetCollectionViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                val wordsDao = (requireContext().applicationContext as App).db.movieDao()
                return SetCollectionViewModel(wordsDao) as T
            }
        }
    }

    private var _binding: FragmentSetCollectionBinding? = null

    private val binding: FragmentSetCollectionBinding
        get() {
            return _binding!!
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSetCollectionBinding.inflate(inflater)
        val editText = binding.setCollectionEditText.editText
        if (editText != null) {
            editText.doOnTextChanged { text, _, _, _ ->
                nameCollection = text.toString()
            }
        }
        binding.buttonSetCollectionAndGoAway.setOnClickListener {
            var idCollection: Int
            lifecycleScope.launch(Dispatchers.IO) {
                idCollection = viewModel.getCollectionList().size + 1
                viewModel.loadCollection(
                    idCollection,
                    nameCollection,
                    0,
                    "R.drawable.baseline_person_collection"
                )

            }

                findNavController().navigate(R.id.action_setCollectionFragment_to_navigation_profile)
        }
        return binding.root
    }

}