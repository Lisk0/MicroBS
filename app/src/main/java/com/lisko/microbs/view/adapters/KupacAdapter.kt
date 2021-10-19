package com.lisko.microbs.view.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.lisko.microbs.R
import com.lisko.microbs.databinding.KupacListBinding
import com.lisko.microbs.model.entities.Kupac
import com.lisko.microbs.view.fragments.AddKupacFragment
import com.lisko.microbs.view.fragments.KupacFragment
import com.lisko.microbs.view.fragments.KupacFragmentDirections

class KupacAdapter(private val fragment: Fragment) : RecyclerView.Adapter<KupacAdapter.ViewHolder>() {

    private var mKupacList: List<Kupac> = listOf()

    class ViewHolder (view: KupacListBinding): RecyclerView.ViewHolder(view.root){
        val tvNaziv= view.etNazivKupac
        val tvPib= view.etPibKupac
        val tvSifraKupac= view.etSifraKupac
        val tvZaposleni= view.etZaposleni
        val btnMore= view.buttonMore
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KupacAdapter.ViewHolder {
        val binding: KupacListBinding = KupacListBinding.inflate(
            LayoutInflater.from(fragment.context),
            parent, false)
        return KupacAdapter.ViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return mKupacList.size
    }

    fun setKupacList(list: List<Kupac>){
        mKupacList= list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val kupac= mKupacList[position]
        holder.tvNaziv.text= kupac.naziv
        holder.tvPib.text= kupac.pib
        holder.tvSifraKupac.text= kupac.sifra
        holder.tvZaposleni.text= kupac.zaposleni_sifra.toString()

        holder.btnMore.setOnClickListener {
            val popup= PopupMenu(fragment.requireContext(), holder.btnMore)
            popup.menuInflater.inflate(R.menu.kupac_menu, popup.menu)

            popup.setOnMenuItemClickListener {
               if(it.itemId== R.id.edit_kupac){
                  NavHostFragment.findNavController(fragment)
                      .navigate(KupacFragmentDirections
                          .actionNavigationKupacToAddKupacFragment(kupac))
               }
                else if (it.itemId== R.id.delete_kupac){
                   if (fragment is KupacFragment){
                       fragment.deleteSelectedKupac(kupac)
                   }
                }
                true
            }
            popup.show()
        }
    }
}