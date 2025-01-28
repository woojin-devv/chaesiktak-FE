package com.example.chaesiktak

import BannerAdapter
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2

class HomeActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: BannerAdapter
    private lateinit var infiniteBanners: List<BannerData>
    private val handler = Handler(Looper.getMainLooper()) // Main Thread Handler
    private var currentPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_home)

        // ViewPager2 초기화
        viewPager = findViewById(R.id.banner)

        // 배너 데이터 생성
        val originalBanners = listOf(
            BannerData("비건 라이프를 더 간편하게,", "채식탁으로 시작하세요!", R.drawable.banner_icon),
            BannerData("건강한 식단을 준비하세요!", "지금 시작해보세요.", R.drawable.banner_icon),
            BannerData("환경을 생각하는 선택!", "채식의 시작은 여기서.", R.drawable.banner_icon)
        )

        // 무한 스크롤용 데이터: 처음과 끝에 데이터를 추가
        infiniteBanners = listOf(originalBanners.last()) + originalBanners + listOf(originalBanners.first())

        // 어댑터 설정
        adapter = BannerAdapter(infiniteBanners)
        viewPager.adapter = adapter

        // 페이지 변경 이벤트 처리
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 0) { // 첫 번째 (가짜) 페이지
                    viewPager.setCurrentItem(infiniteBanners.size - 2, false) // 마지막 실제 페이지로 이동
                } else if (position == infiniteBanners.size - 1) { // 마지막 (가짜) 페이지
                    viewPager.setCurrentItem(1, false) // 첫 번째 실제 페이지로 이동
                }
            }
        })

        // 자동 스크롤 시작
        startAutoScroll()

        // 시스템 바 패딩 설정
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun startAutoScroll() {
        val runnable = object : Runnable {
            override fun run() {
                val nextPage = (viewPager.currentItem + 1) % infiniteBanners.size
                viewPager.setCurrentItem(nextPage, true)
                handler.postDelayed(this, 2500)
            }
        }
        handler.post(runnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null) // 핸들러 작업 중지
    }
}
