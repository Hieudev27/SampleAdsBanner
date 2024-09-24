
# Ad Banner
## New version (upper 6.0.0)
###Create class NativeFactory to management Native Ads
~~~
object NativeFactory {
    @JvmStatic
    fun createNativeConfig(
        adUnitId: String,
        adUnitIdHighFloor: String,
        adUnitIdAllPrice: String,
        isHighFloor: Boolean,
        canShowAd: Boolean,
        canReloadAd: Boolean,
        @LayoutRes layoutId: Int
    ): NativeAdConfig {
        return if (isHighFloor) {
            NativeAdHighFloorConfig(
                idAdHighFloor = adUnitIdHighFloor,
                idAdAllPrice = adUnitIdAllPrice,
                canShowAds = canShowAd,
                canReloadAds = canReloadAd,
                layoutId = layoutId
            )
        } else {
            NativeAdConfig(
                idAds = adUnitId,
                canShowAds = canShowAd,
                canReloadAds = canReloadAd,
                layoutId = layoutId
            )
        }
    }

    @JvmStatic
    fun createNativeHelper(
        activity: Activity,
        lifecycle: LifecycleOwner,
        nativeAdConfig: NativeAdConfig,
        trackingTag: String
    ): NativeAdHelper {
        val nativeAdHelper = when (nativeAdConfig) {
            is NativeAdHighFloorConfig -> {
                NativeAdHighFloorHelper(activity, lifecycle, nativeAdConfig)
            }

            else -> {
                NativeAdHelper(activity, lifecycle, nativeAdConfig)
            }
        }
        trackingNativeHelper(nativeAdHelper, nativeAdConfig, trackingTag)
        return nativeAdHelper
    }

    private fun trackingNativeHelper(
        nativeAdHelper: NativeAdHelper,
        nativeAdConfig: NativeAdConfig,
        trackingTag: String
    ) {
        var adUnitId: String? = null
        var adUnitIdHighFloor: String? = null
        var adUnitIdAllPrice: String? = null
        when (nativeAdConfig) {
            is NativeAdHighFloorConfig -> {
                adUnitIdHighFloor = nativeAdConfig.idAdHighFloor
                adUnitIdAllPrice = nativeAdConfig.idAdAllPrice
            }

            else -> {
                adUnitId = nativeAdConfig.idAds
            }
        }
        val paramLoadingTime = {
            "time_loading" to nativeAdHelper.timeLoadedAd
        }
        nativeAdHelper.registerAdListener(object : AperoAdCallback() {
            override fun onAdImpression() {
                super.onAdImpression()
                when (nativeAdHelper.nativeAd?.adUnitId) {
                    null -> Unit
                    adUnitId -> track("${trackingTag}_view")
                    adUnitIdHighFloor -> track("${trackingTag}_high_floor_view", paramLoadingTime())
                    adUnitIdAllPrice -> track("${trackingTag}_all_price_view", paramLoadingTime())
                }
            }

            override fun onAdClicked() {
                super.onAdClicked()
                when (nativeAdHelper.nativeAd?.adUnitId) {
                    null -> Unit
                    adUnitId -> track("${trackingTag}_click")
                    adUnitIdHighFloor -> track("${trackingTag}_high_floor_click")
                    adUnitIdAllPrice -> track("${trackingTag}_all_price_click")
                }
            }
        })
    }


}
~~~

###Implement in class
Create nativeAdHelper
~~~
    private val nativeAdHelper by lazy {
        val nativeAdConfig = NativeFactory.createNativeConfig(
            adUnitId = BuildConfig.native_tools,
            adUnitIdHighFloor = BuildConfig.native_tools_high_floor,
            adUnitIdAllPrice = BuildConfig.native_tools_all_price,
            canShowAd = SharePreferenceUtils.getRemoteNativeTools(this),
            canReloadAd = SharePreferenceUtils.isReloadNativeTools(this),
            isHighFloor = remoteAds.nativeToolsHighFloor,
            layoutId = R.layout.layout_native_tools,
        )
        NativeFactory.createNativeHelper(this, this, nativeAdConfig, "native_tools")
    }

~~~

## Old version
