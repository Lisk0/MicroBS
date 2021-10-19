package com.lisko.microbs.view.fragments

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lisko.microbs.R
import com.lisko.microbs.application.MicroApplication
import com.lisko.microbs.databinding.FragmentAddKupacBinding
import com.lisko.microbs.databinding.FragmentZaposleniBinding
import com.lisko.microbs.model.entities.Kupac
import com.lisko.microbs.view.adapters.ZaposleniAdapter
import com.lisko.microbs.viewmodel.KupacViewModel
import com.lisko.microbs.viewmodel.KupacViewModelFactory
import com.lisko.microbs.viewmodel.ZaposleniViewModel
import com.lisko.microbs.viewmodel.ZaposleniViewModelFactory


class AddKupacFragment: Fragment() {
    private lateinit var binding: FragmentAddKupacBinding
    private lateinit var mDialog: Dialog
    private val args: AddKupacFragmentArgs by navArgs()
    private var listaSifra: List<String> = listOf()

    private val mKupacViewModel: KupacViewModel by viewModels {
        KupacViewModelFactory((requireActivity().application
                as MicroApplication).repository)
    }

    private val zaposleniViewModel: ZaposleniViewModel by viewModels {
        ZaposleniViewModelFactory((requireActivity().application
                as MicroApplication).repository)
    }

    private var selected: String=""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAddKupacBinding.inflate(inflater, container, false)

        binding.etZaposleni.setOnClickListener {
            showZaposleniDialog()
        }

        if(args.target!=null) {
            binding.btnAddKupac.text= getString(R.string.update)
            binding.etSifra.focusable= View.NOT_FOCUSABLE
        }
        else{
            binding.etSifra.focusable= View.FOCUSABLE
        }

        binding.btnAddKupac.setOnClickListener{
            if(validateKupac()){
                val naziv= binding.etNaziv.text.toString()
                val pib= binding.etPib.text.toString()
                val sifra= binding.etSifra.text.toString()
                val zaposleni= binding.etZaposleni.text.toString()
                val kupac = Kupac(naziv, pib, sifra, zaposleni)
                if(args.target==null) {
                    mKupacViewModel.insert(kupac)
                }
                else{
                    mKupacViewModel.update(kupac)
                }


                findNavController().navigateUp()
            }
        }

        args.target?.let {
            binding.etNaziv.setText(args.target!!.naziv)
            binding.etPib.setText(args.target!!.pib)
            binding.etSifra.setText(args.target!!.sifra)
            binding.etZaposleni.setText(args.target!!.zaposleni_sifra)
        }


        mKupacViewModel.allSifreKupac.observe(viewLifecycleOwner){
            listaSifra=it
        }

        return binding.root

    }

    private fun showZaposleniDialog() {
        val dialogBind = FragmentZaposleniBinding.inflate(layoutInflater)
        mDialog= Dialog(requireContext())
        mDialog.setContentView(dialogBind.root)
        mDialog.setTitle(requireContext().getString(R.string.lb_zaposleni))
        dialogBind.rvZaposleniList.layoutManager= LinearLayoutManager(requireContext(),
            RecyclerView.VERTICAL,false)
        val adapter= ZaposleniAdapter(this@AddKupacFragment)
        zaposleniViewModel.allZaposleni.observe(viewLifecycleOwner){
            adapter.setZaposleniList(it)
        }

        dialogBind.rvZaposleniList.adapter= adapter
        mDialog.show()
    }


    private fun validateKupac(): Boolean{
        val naziv= binding.etNaziv.text.toString()
        val pib= binding.etPib.text.toString()
        val sifra= binding.etSifra.text.toString()


        when {
            naziv.isEmpty() -> {
                showToast(getString(R.string.error_naziv))
                return false
            }
            pib.isEmpty() -> {
                showToast(getString(R.string.error_pib))
                return false
            }
            sifra.isEmpty() -> {
                showToast(getString(R.string.error_sifra))
                return false
            }
            binding.etZaposleni.text!!.isNullOrEmpty() -> {
                showToast(getString(R.string.error_zaposleni))
                return false
            }

            listaSifra.contains(sifra) ->{
                if(args.target!=null) return true
                showToast(getString(R.string.error_sifra_unique))
                return false
            }


            else -> return true
        }


    }

    private fun showToast(message: String){
        Toast.makeText(activity,
            message, Toast.LENGTH_SHORT).show()
    }

    fun setSelection(x: String){
        selected=x
        binding.etZaposleni.setText(selected)
        mDialog.dismiss()
    }


}