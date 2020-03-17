package com.icspl.tlpinstallation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.icspl.tlpinstallation.R

class TestStationFragment: Fragment()
{
    companion object
    {
        fun newInstance():TestStationFragment
        {
            return TestStationFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_teststation,container,false)
    }
}