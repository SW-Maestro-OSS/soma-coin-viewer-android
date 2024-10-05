package com.soma.coinviewer.common_ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope

abstract class BaseBindingFragment<T : ViewDataBinding, V : BaseViewModel>(
    @LayoutRes private val layoutId: Int
) : Fragment() {
    private var _binding: T? = null
    protected val binding get() = _binding ?: throw IllegalStateException("Binding is not initialized")

    protected abstract val fragmentViewModel: V

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = this.viewLifecycleOwner
        return _binding?.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}