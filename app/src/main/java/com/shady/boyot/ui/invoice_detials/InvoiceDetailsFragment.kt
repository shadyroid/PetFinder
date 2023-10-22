package com.shady.boyot.ui.invoice_detials

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.shady.domain.entity.beans.InvoiceBean
import com.shady.boyot.R
import com.shady.boyot.databinding.FragmentInvoiceDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InvoiceDetailsFragment : Fragment() {
    private var _binding: FragmentInvoiceDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var invoiceBean: InvoiceBean

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (_binding == null) {
            _binding = FragmentInvoiceDetailsBinding.inflate(inflater, container, false)
            init()
        }
        return binding.root
    }

    private fun init() {
        val args = InvoiceDetailsFragmentArgs.fromBundle(requireArguments())
        invoiceBean = args.invoice
        setData()
    }


    private fun setData() {
//        Glide.with(this)
//            .load(invoiceBean.displayMediumImage)
//            .placeholder(R.drawable.placeholder)
//            .error(R.drawable.placeholder)
//            .into(binding.ivImage)
//        binding.tvName.text = getString(R.string.name_is, invoiceBean.displayName)
//        binding.tvSize.text = getString(R.string.size_is, invoiceBean.displaySize)
//        binding.tvPrimaryColor.text = getString(R.string.color_is, invoiceBean.displayPrimaryColor)
//        binding.tvAddress.text = getString(R.string.address_is, invoiceBean.displayAddress)
//        binding.btnInvoiceWebsite.setOnClickListener { openLink(invoiceBean.url!!) }
    }

    private fun openLink(link: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(browserIntent)
    }
}