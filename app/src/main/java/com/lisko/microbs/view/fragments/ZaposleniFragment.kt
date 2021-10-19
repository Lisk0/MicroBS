package com.lisko.microbs.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lisko.microbs.R
import com.lisko.microbs.application.MicroApplication
import com.lisko.microbs.databinding.DialogZaposleniDetailsBinding
import com.lisko.microbs.databinding.FragmentZaposleniBinding
import com.lisko.microbs.model.entities.Kupac
import com.lisko.microbs.model.entities.Zaposleni
import com.lisko.microbs.view.adapters.ZaposleniAdapter
import com.lisko.microbs.viewmodel.ZaposleniViewModel
import com.lisko.microbs.viewmodel.ZaposleniViewModelFactory

class ZaposleniFragment : Fragment() {


    private var _binding: FragmentZaposleniBinding? = null

    private val zaposleniViewModel: ZaposleniViewModel by viewModels {
        ZaposleniViewModelFactory((requireActivity().application
                as MicroApplication).repository)
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentZaposleniBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.all_zaposleni_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_add_zaposleni -> {
                findNavController().navigate(ZaposleniFragmentDirections.
                actionNavigationZaposleniToAddZaposleniActivity())
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvZaposleniList.layoutManager= LinearLayoutManager(requireActivity(),
            RecyclerView.VERTICAL, false)
        val adapter= ZaposleniAdapter(this@ZaposleniFragment)
        binding.rvZaposleniList.adapter= adapter

        zaposleniViewModel.allZaposleni.observe(viewLifecycleOwner){
            for(item in it){
                Log.i("zaposleni","${item.ime}:: ${item.grad}")
            }
            adapter.setZaposleniList(it)
        }



        }


}