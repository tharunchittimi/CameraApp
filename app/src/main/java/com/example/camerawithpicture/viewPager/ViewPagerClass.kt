package com.example.camerawithpicture.viewPager

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.camerawithpicture.R
import com.example.camerawithpicture.base.BaseActivity
import com.example.camerawithpicture.tabSelected.FragmentOne
import com.example.camerawithpicture.tabSelected.FragmentThree
import com.example.camerawithpicture.tabSelected.FragmentTwo
import com.ogaclejapan.smarttablayout.SmartTabLayout

class ViewPagerClass : BaseActivity() {
    override fun setLayout(): Int {
        return R.layout.tab_view_pager
    }

    override fun initView(savedInstanceState: Bundle?) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(FragmentOne(), "ONE")
        adapter.addFragment(FragmentTwo(), "TWO")
        adapter.addFragment(FragmentThree(), "THREE")

        val viewPager = findViewById<ViewPager>(R.id.viewpager)
        viewPager.adapter = adapter
        val viewPagerTab = findViewById<SmartTabLayout>(R.id.viewpagertab)
        viewPagerTab.setViewPager(viewPager)
    }

    class ViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()
        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }
        override fun getPageTitle(position: Int): CharSequence {
            return mFragmentTitleList[position]
        }
    }
}