package com.lisko.microbs.view.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lisko.microbs.R
import com.lisko.microbs.application.MicroApplication
import com.lisko.microbs.databinding.FragmentKupacBinding
import com.lisko.microbs.model.database.MicroRepository
import com.lisko.microbs.model.entities.Kupac
import com.lisko.microbs.view.adapters.KupacAdapter
import com.lisko.microbs.viewmodel.KupacViewModel
import com.lisko.microbs.viewmodel.KupacViewModelFactory
import com.lisko.microbs.viewmodel.ZaposleniViewModel
import com.lisko.microbs.viewmodel.ZaposleniViewModelFactory

class KupacFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private val adapter= KupacAdapter(this@KupacFragment)
    private val kupacViewModel: KupacViewModel by viewModels {
        KupacViewModelFactory((requireActivity().application as
                MicroApplication).repository)
    }
    private val zaposleniViewModel: ZaposleniViewModel by viewModels {
        ZaposleniViewModelFactory((requireActivity().application
                as MicroApplication).repository)
    }

    private var _binding: FragmentKupacBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.all_kupac_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentKupacBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_add_kupac -> {
                findNavController().navigate(KupacFragmentDirections.
                actionNavigationKupacToAddKupacFragment())
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.rvKupacList.layoutManager= LinearLayoutManager(requireContext(), RecyclerView.VERTICAL,
            false)
        binding.rvKupacList.adapter= adapter



        zaposleniViewModel.allZaposleni.observe(viewLifecycleOwner){
            val arrayList= arrayListOf<String>()
            arrayList.add("SVI")
            for(zaposleni in it){
                arrayList.add(zaposleni.sifra)
            }
            val spinnerAdapter= ArrayAdapter(requireContext(),
                android.R.layout.simple_spinner_item, arrayList)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
            binding.spinner.adapter= spinnerAdapter
            binding.spinner.onItemSelectedListener= this
        }

    }

    fun deleteSelectedKupac(kupac: Kupac){
        val builder= AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.title_delete_zaposleni))
        var poruka= "Da li ste sigurni da hocete da obrisete\n${kupac.naziv}"
        builder.setMessage(poruka)
        builder.setNegativeButton(getString(R.string.ne)){
            dialogInterface, _ ->
            dialogInterface.dismiss()
        }
        builder.setPositiveButton(getString(R.string.da)){
            dialogInterface, _ ->
            kupacViewModel.delete(kupac)
            dialogInterface.dismiss()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()

    }

    override fun onItemSelected(av: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                if(av!=null){
                    if(pos==0)kupacViewModel.allKupac.observe(viewLifecycleOwner){
                        adapter.setKupacList(it)
                        binding.rvKupacList.visibility=View.VISIBLE
                    }
                    else{
                        Log.i("FILTRIRANJE", av.getItemAtPosition(pos).toString())
                        kupacViewModel.filter(av.getItemAtPosition(pos).toString()).observe(viewLifecycleOwner){
                            kupci ->
                            kupci.let {
                                if(it.isNotEmpty()){
                                    binding.rvKupacList.visibility=View.VISIBLE
                                    adapter.setKupacList(it)
                                    for(x in it)
                                        Log.i("filtriranje stvari", x.toString())
                                }
                                else{
                                    binding.rvKupacList.visibility=View.GONE
                                }
                            }
                        }

                    }
                }

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        //
    }
}