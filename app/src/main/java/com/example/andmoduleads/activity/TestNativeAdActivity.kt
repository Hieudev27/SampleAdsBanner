package com.example.andmoduleads.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ads.control.ads.AperoAdCallback
import com.ads.control.ads.wrapper.ApAdError
import com.ads.control.ads.wrapper.ApNativeAd
import com.ads.control.helper.adnative.NativeAdConfig
import com.ads.control.helper.adnative.NativeAdHelper
import com.ads.control.helper.adnative.highfloor.NativeAdHighFloorConfig
import com.ads.control.helper.adnative.params.AdNativeMediation
import com.ads.control.helper.adnative.params.NativeAdParam
import com.ads.control.helper.adnative.params.NativeLayoutMediation
import com.ads.control.helper.adnative.preload.NativeAdPreloadClientOption
import com.example.andmoduleads.BuildConfig
import com.example.andmoduleads.R
import com.example.andmoduleads.databinding.ActivityTestNativeAdBinding
import com.example.andmoduleads.utils.PreloadAdsUtils

class TestNativeAdActivity : AppCompatActivity() {
    private var binding: ActivityTestNativeAdBinding? = null
    private val nativeAdHelper by lazy { initNativeAd() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestNativeAdBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)
        //        showPreNative();

        nativeAdHelper.registerAdListener(object : AperoAdCallback() {
            override fun onAdImpression() {
                super.onAdImpression()
                Log.d("NativeAdPreloadCallback", "onAdImpression")
            }

            override fun onNativeAdLoaded(nativeAd: ApNativeAd) {
                super.onNativeAdLoaded(nativeAd)
                Log.d("NativeAdPreloadCallback", "onNativeAdLoaded")
            }

            override fun onAdFailedToLoad(adError: ApAdError?) {
                super.onAdFailedToLoad(adError)
                Log.d("NativeAdPreloadCallback", "onAdFailedToLoad")
            }
        })
        nativeAdHelper
            .setNativeContentView(binding!!.frAds)
            .setShimmerLayoutView( binding!!.includeNative.shimmerContainerNative)
            .setEnablePreload(true)
            .setPreloadAdOption(
                NativeAdPreloadClientOption.builder()
                    .setPreloadAfterShow(true)
                    .setPreloadBuffer(2)
                    .setPreloadOnResume(false)
                    .build()
            )
        nativeAdHelper.requestAds(NativeAdParam.Request.create()) //start request native

    }

    // old implementation
    private fun showPreNative() {
        PreloadAdsUtils.getInstance().showPreNativeSametime(
            this@TestNativeAdActivity,
            binding!!.frAds,
            binding!!.includeNative.shimmerContainerNative
        )
    }

    private fun initNativeAd(): NativeAdHelper {
        val config = getNativeConfig()
        config.setLayoutMediation(
            NativeLayoutMediation(
                AdNativeMediation.FACEBOOK,
                R.layout.custom_native_admod_medium_rate
            )
        )
        return NativeAdHelper(this, this, config).apply {
            setEnablePreload(true)
            registerAdListener(object : AperoAdCallback() {
                override fun onAdImpression() {
                    super.onAdImpression()
                }
            })
        }
    }

    companion object {
        fun getNativeConfig(): NativeAdConfig {
            return NativeAdConfig(
                idAds = BuildConfig.ad_native,
                canShowAds = true,
                canReloadAds = true,
                layoutId = R.layout.custom_native_admod_medium
            )
        }
    }
}