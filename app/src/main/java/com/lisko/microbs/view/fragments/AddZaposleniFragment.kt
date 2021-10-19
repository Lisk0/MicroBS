package com.lisko.microbs.view.fragments


import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.lisko.microbs.R
import com.lisko.microbs.application.MicroApplication
import com.lisko.microbs.databinding.FragmentAddZaposleniBinding
import com.lisko.microbs.model.entities.Zaposleni
import com.lisko.microbs.viewmodel.ZaposleniViewModel
import com.lisko.microbs.viewmodel.ZaposleniViewModelFactory

class AddZaposleniFragment : Fragment() {
    private lateinit var binding: FragmentAddZaposleniBinding
    private var listaSifra: List<String> = listOf()
    private val mZaposleniViewModel: ZaposleniViewModel by viewModels {
        ZaposleniViewModelFactory((requireActivity().application
                as MicroApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAddZaposleniBinding.inflate(inflater, container, false)

        binding.btnAddZaposleni.setOnClickListener{
            if(validateZaposleni()){
                val ime= binding.etIme.text.toString()
                val prezime= binding.etPrezime.text.toString()
                val sifra= binding.etSifra.text.toString()
                val grad= binding.etGrad.text.toString()
                val magacin= binding.etMagacin.text.toString()

                val zaposleni = Zaposleni(ime,prezime,sifra,grad,magacin)

                mZaposleniViewModel.insert(zaposleni)

                showToast(getString(R.string.success_add_zaposleni))

                findNavController().navigateUp()
            }
        }

        mZaposleniViewModel.allSifre.observe(viewLifecycleOwner){
            listaSifra= it
        }

        return binding.root

    }

    
    private fun validateZaposleni(): Boolean{
        val ime= binding.etIme.text.toString()
        val prezime= binding.etPrezime.text.toString()
        val sifra= binding.etSifra.text.toString()
        val grad= binding.etGrad.text.toString()
        val magacin= binding.etMagacin.text.toString()

        when {
            ime.isEmpty() -> {
                showToast(getString(R.string.error_ime))
                return false
            }
            prezime.isEmpty() -> {
                showToast(getString(R.string.error_prezime))
                return false
            }
            sifra.isEmpty() -> {
                showToast(getString(R.string.error_sifra))
                return false
            }
            grad.isEmpty() -> {
                showToast(getString(R.string.error_grad))
                return false
            }
            magacin.isEmpty() -> {
                showToast(getString(R.string.error_magacin))
                return false
            }
            listaSifra.contains(sifra) ->{
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

}