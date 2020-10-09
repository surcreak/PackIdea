package com.surcreak.packidea

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.surcreak.packidea.homepage.ui.fragment.HomeFragment
import com.surcreak.packidea.mainpage.ui.fragment.GalleryFragment
import com.surcreak.packidea.mainpage.ui.fragment.SendFragment
import com.surcreak.packidea.mainpage.ui.fragment.ShareFragment
import com.surcreak.packidea.mainpage.ui.fragment.ToolsFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


@ExperimentalCoroutinesApi
class MainFragmentFactory
@Inject
constructor(
    private val someString: String
): FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className) {
            HomeFragment::class.java.name -> {
                HomeFragment()
            }
            GalleryFragment::class.java.name -> {
                GalleryFragment()
            }
            SendFragment::class.java.name -> {
                SendFragment()
            }
            ShareFragment::class.java.name -> {
                ShareFragment()
            }
            ToolsFragment::class.java.name -> {
                ToolsFragment()
            }
            else -> return super.instantiate(classLoader, className)
        }
    }
}