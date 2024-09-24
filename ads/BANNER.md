
# Ad Banner
## New version (upper 6.0.0)
InitBannerAdHelper
~~~

private fun initBannerAd(): BannerAdHelper {
        val config = BannerAdConfig(
            BuildConfig.ad_banner,
            true,
            true,
            // with native additional layout native ad
        )
        return BannerAdHelper(activity = this, lifecycleOwner = this, config = config)
    }
~~~
OnCreate()

Set layout view when init binding successfully
~~~

override fun onCreate(savedInstanceState: Bundle?) {
	bannerAdHelper.setBannerContentView(binding.frAds)
	    .apply { setTagForDebug("BANNER=>>>") }
	    
	// with native additional setShimmerLayoutView
}
~~~

RequestAd()

Request 1 ad banner new | banner visible
~~~
	bannerAdHelper.requestAds(BannerAdParam.Request)
~~~

Show 1 banner new (previously loaded) | banner visible
~~~
	bannerAdHelper.requestAds(BannerAdParam.Ready(adView))
~~~

Display ad (loaded) when clickable after milis | not working when call function cancel() | active khi call again Request or Ready
~~~
	bannerAdHelper.requestAds(BannerAdParam.Clickable(remoteAds.minimumTimeKeepAdsDisplay))
~~~

CancelAd()

Cancel progress request ad và hide banner | banner gone
~~~
	bannerAdHelper.cancel()
~~~

Ad Callback
~~~
val adCallback = object : AdCallback() {
                    override fun onAdClicked() {
                        super.onAdClicked()
                        Analytics.track("banner_click")
                    }

                    override fun onAdImpression() {
                        super.onAdImpression()
                        Analytics.track("banner_view")
                    }
                }
~~~
Register ad callback
~~~
	bannerAdHelper.registerAdListener(adCallback)
~~~

Unregister ad callback
~~~
	bannerAdHelper.unregisterAdListener(adCallback)
~~~

## Old version
Layout container ad banner
~~~
    <FrameLayout
        android:id="@+id/fr_ads"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <include layout="@layout/layout_banner_control" />
    </FrameLayout>
~~~

Normal banner in Activity/Fragment
~~~
AperoAd.getInstance().loadBanner(this, idBanner);
or
AperoAd.getInstance().loadBannerFragment(final Activity mActivity, String id, final View rootView);
~~~
Inline banner in Activity/Fragment
inlineStyle:
- Admob.BANNER_INLINE_SMALL_STYLE: for small inline banner
- Admob.BANNER_INLINE_LARGE_STYLE: for large inline banner
~~~
Admob.getInstance().loadInlineBanner(activity, idBanner, inlineStyle, adCallback);
or
Admob.getInstance().loadInlineBannerFragment(final Activity activity, String id, final View rootView, String inlineStyle);
~~~
Collapsible banner in Activity/Fragment
gravity:
* AppConstant.TOP: banner anchor at the top of layout
* AppConstant.BOTTOM: banner anchor at the bottom of layout
~~~
Admob.getInstance().loadCollapsibleBanner(final Activity mActivity, String id, String gravity, final AdCallback callback)
or
Admob.getInstance().loadCollapsibleBannerFragment(final Activity mActivity, String id, final View rootView, String gravity, final AdCallback callback);
~~~

### Banner priority
~~~
    AperoAd.getInstance().loadBannerPriority(this,
        BuildConfig.banner_high_floor,
        BuildConfig.banner_medium,
        BuildConfig.banner_all_price,
        mViewDataBinding!!.frAds,
        AperoAd.REQUEST_TYPE.SAME_TIME,
        true,
        object : AperoAdCallback() {})
~~~
