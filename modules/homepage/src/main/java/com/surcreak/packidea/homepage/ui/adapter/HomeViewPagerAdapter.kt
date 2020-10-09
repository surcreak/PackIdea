package com.surcreak.packidea.homepage.ui.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.surcreak.packidea.base.utils.Logger
import com.surcreak.packidea.homepage.R
import com.surcreak.packidea.homepage.ui.fragment.RecommendFragment
import com.surcreak.packidea.homepage.vo.HomePager

class HomeViewPagerAdapter(fragment: Fragment,
                           val pagers: List<HomePager>) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = pagers.size

    override fun createFragment(position: Int): Fragment {
        val homePager = pagers[position]

        return when(homePager) {
            HomePager.RECOMMEND -> {
                RecommendFragment().apply {
                    arguments = Bundle().apply {
                        // Our object is just an integer :-P
                        putString(ARG_OBJECT, homePager.text)
                    }
                }
            }
//            HomePager.HOT -> TODO()
//            HomePager.VIDEO -> TODO()
//            HomePager.MUSIC -> TODO()
            else -> {
                DemoObjectFragment().apply {
                    arguments = Bundle().apply {
                        // Our object is just an integer :-P
                        putString(ARG_OBJECT, homePager.text)
                    }
                }
            }
        }
    }
}

private const val ARG_OBJECT = "object"

class DemoObjectFragment: Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d("gaol DemoObjectFragment onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.homepage_fragment_collection_object, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            val textView: TextView = view.findViewById(R.id.text1)
            val pagerIndex = getString(ARG_OBJECT).toString()
            textView.text = pagerIndex
            Logger.e("gaol DemoObjectFragment onViewCreated pagerIndex=$pagerIndex")
        }
    }
}