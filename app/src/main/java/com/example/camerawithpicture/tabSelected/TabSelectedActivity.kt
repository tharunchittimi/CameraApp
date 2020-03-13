package com.example.camerawithpicture.tabSelected

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.camerawithpicture.R
import com.example.camerawithpicture.application.MyApplication
import com.example.camerawithpicture.base.BaseActivity
import kotlinx.android.synthetic.main.activity_tab_selected.*


class TabSelectedActivity:BaseActivity() {
    override fun setLayout(): Int {
        return R.layout.activity_tab_selected
    }

    override fun initView(savedInstanceState: Bundle?) {

        tabLayout.setupWithViewPager(viewPager)
        viewPager.adapter=ViewPagerAdapter(supportFragmentManager)
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(FragmentOne(), "ONE")
        adapter.addFragment(FragmentTwo(), "TWO")
        adapter.addFragment(FragmentThree(), "THREE")
        viewPager.adapter = adapter

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