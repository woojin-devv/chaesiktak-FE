package com.example.chaesiktak.fragments

import BannerAdapter
import RecommendRecipeAdapter
import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.chaesiktak.BannerData
import com.example.chaesiktak.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chaesiktak.RecommendRecipe
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private var recipeList: ArrayList<RecommendRecipe> = ArrayList()
    private lateinit var recommendrecipeAdapter: RecommendRecipeAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: BannerAdapter
    private lateinit var infiniteBanners: List<BannerData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // ViewPager 설정
        viewPager = view.findViewById(R.id.banner)

        // RecyclerView 설정
        recyclerView = view.findViewById(R.id.recipeRecyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // RecyclerView를 가로 방향으로 설정
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // RecyclerView 데이터 설정
        recipeList.apply {
            add(RecommendRecipe(R.drawable.sample_image, "타이틀 1", "1인분, 15분"))
            add(RecommendRecipe(R.drawable.sample_image, "타이틀 2", "2인분, 30분"))
            add(RecommendRecipe(R.drawable.sample_image, "타이틀 3", "3인분, 45분"))
            add(RecommendRecipe(R.drawable.sample_image, "타이틀 4", "4인분, 60분"))
        }

        recommendrecipeAdapter = RecommendRecipeAdapter(recipeList)
        recyclerView.adapter = recommendrecipeAdapter

        // 배너 데이터 설정
        val originalBanners = listOf(
            BannerData("비건 라이프를 더 간편하게,", "채식탁으로 시작하세요!", R.drawable.banner_icon),
            BannerData("건강한 식단을 준비하세요!", "지금 시작해보세요.", R.drawable.banner_icon),
            BannerData("환경을 생각하는 선택!", "채식의 시작은 여기서.", R.drawable.banner_icon)
        )

        infiniteBanners = listOf(originalBanners.last()) + originalBanners + listOf(originalBanners.first())
        adapter = BannerAdapter(infiniteBanners)
        viewPager.adapter = adapter

        // 배너 이벤트 처리
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 0) {
                    viewPager.post { viewPager.setCurrentItem(infiniteBanners.size - 2, false) }
                } else if (position == infiniteBanners.size - 1) {
                    viewPager.post { viewPager.setCurrentItem(1, false) }
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

    override fun onPause() {
        super.onPause()
        stopAutoScroll()
    }

    override fun onResume() {
        super.onResume()
        startAutoScroll()
    }

    private fun startAutoScroll() {
        viewLifecycleOwner.lifecycleScope.launch {
            while (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                delay(2500)
                if (::viewPager.isInitialized) {
                    val nextPage = (viewPager.currentItem + 1) % infiniteBanners.size
                    viewPager.setCurrentItem(nextPage, true)
                }
            }
        }
    }

    private fun stopAutoScroll() {
        viewLifecycleOwner.lifecycleScope.coroutineContext.cancelChildren()
    }
}
