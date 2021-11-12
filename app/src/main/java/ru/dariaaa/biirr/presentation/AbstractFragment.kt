package ru.dariaaa.biirr.presentation

import android.view.View
import androidx.fragment.app.Fragment

abstract class AbstractFragment<HOLDER : AbstractFragment.Holder> : Fragment() {
    protected var holder: HOLDER? = null

    protected fun requireHolder() = holder!!

    protected abstract fun createHolder(view: View): HOLDER

    abstract class Holder {

    }
}