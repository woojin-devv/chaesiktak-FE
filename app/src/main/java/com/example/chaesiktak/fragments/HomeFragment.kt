package com.example.chaesiktak.fragments

import BannerAdapter
import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.chaesiktak.BannerData
import com.example.chaesiktak.R
import android.os.Handler


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: BannerAdapter
    private lateinit var infiniteBanners: List<BannerData>
    private val handler = Handler(Looper.getMainLooper()) // Main Thread Handler
    private var currentPage = 0

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // XML 레이아웃을 인플레이트
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // ViewPager 설정
        viewPager = view.findViewById(R.id.banner)
        val originalBanners = listOf(
            BannerData("비건 라이프를 더 간편하게,", "채식탁으로 시작하세요!", R.drawable.banner_icon),
            BannerData("건강한 식단을 준비하세요!", "지금 시작해보세요.", R.drawable.banner_icon),
            BannerData("환경을 생각하는 선택!", "채식의 시작은 여기서.", R.drawable.banner_icon)
        )

        infiniteBanners = listOf(originalBanners.last()) + originalBanners + listOf(originalBanners.first())
        adapter = BannerAdapter(infiniteBanners)
        viewPager.adapter = adapter

        // 페이지 변경 이벤트 처리
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 0) { // 첫 번째 (가짜) 페이지
                    viewPager.setCurrentItem(infiniteBanners.size - 2, false)
                } else if (position == infiniteBanners.size - 1) { // 마지막 (가짜) 페이지
                    viewPager.setCurrentItem(1, false)
                }
            }
        })

        // 자동 스크롤 시작
        startAutoScroll()

        // 하단 탭 네비게이션 설정
        view.findViewById<ImageView>(R.id.scannerTap).setOnClickListener {
            view.findNavController().navigate(R.id.action_homeFragment_to_scannerFragment)
        }
        view.findViewById<ImageView>(R.id.myinfoTap).setOnClickListener {
            view.findNavController().navigate(R.id.action_homeFragment_to_myInfoFragment)
        }

        return view
    }

    private fun startAutoScroll() {
        val runnable = object : Runnable {
            override fun run() {
                if (::viewPager.isInitialized) {
                    val nextPage = (viewPager.currentItem + 1) % infiniteBanners.size
                    viewPager.setCurrentItem(nextPage, true)
                }
                handler.postDelayed(this, 2500)
            }
        }
        handler.post(runnable)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}