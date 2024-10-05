package com.soma.coinviewer.common_ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseBindingFragment<T : ViewDataBinding, V : BaseViewModel>
    (private val inflate: Inflate<T>) : Fragment() {

    private var _binding: T? = null
    protected val binding
        get() = _binding ?: throw IllegalStateException("Binding is not initialized")

    protected abstract val fragmentViewModel: V

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}