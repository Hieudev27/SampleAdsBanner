package com.example.andmoduleads.activity

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.ads.control.funtion.AdCallback
import com.ads.control.helper.banner.BannerAdConfig
import com.ads.control.helper.banner.BannerAdHelper
import com.ads.control.helper.banner.params.BannerAdParam.Request.create
import com.ads.control.util.AppConstant
import com.example.andmoduleads.BuildConfig
import com.example.andmoduleads.R

class SampleAdBannerActivity : AppCompatActivity() {
    private lateinit var frBanner : FrameLayout
    private lateinit var adCallback : AdCallback
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample_ads_banner)
        frBanner = findViewById(R.id.bannerAdView)
        adCallback = object : AdCallback(){
            override fun onAdClicked() {
                super.onAdClicked()
            }

            override fun onAdClosed() {
                super.onAdClosed()
            }

            override fun onAdLoaded() {
                super.onAdLoaded()
            }

            override fun onAdImpression() {
                super.onAdImpression()
            }
        }
        showBanner()
    }

    private fun showBanner() {
        val bannerAdConfig = BannerAdConfig(BuildConfig.banner_normal, true, true).apply {
            enableAutoReload = true
            collapsibleGravity = AppConstant.CollapsibleGravity.BOTTOM
        }
        val bannerAdHelper = BannerAdHelper(
            this,
            this,
            bannerAdConfig
        )
        bannerAdHelper.apply {
            setBannerContentView(frBanner)
            registerAdListener(adCallback)
            requestAds(create())
        }
    }

}