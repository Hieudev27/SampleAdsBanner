# SampleAdsBanner
## Setup
**Importing Libraries**
+ Add the following Maven URLs to your project's `settings.gradle` or `settings.gradle.kts`:
    ```bash
    dependencyResolutionManagement {
        repositories {
            ...
            maven { url = uri("https://jitpack.io") }
            maven {
                url = uri("https://artifactory.apero.vn/artifactory/gradle-release/")
                credentials {
                    username = "YOUR_USERNAME"
                    password = "YOUR_PASSWORD"
                }
            }
        }
    }
    ```

**Adding Dependencies**

- If you're using version catalogs, declare the Apero library in `libs.versions.toml`:
    ```bash
    [versions]
    aperoAd = "7.4.1"
    
    [libraries]
    apero-ad = { group = "apero-inhouse", name = "apero-ads", version.ref = "aperoAd" }
    ```
- Add the library dependency to your `build.gradle` or `build.gradle.kts`:
    ```bash
    dependencies {
        implementation(libs.aperoAd)
    }
    ```

- In case you do not use version catalogs, add the library dependency to your `build.gradle`:
    ``` bash
    dependencies {
        implementation 'apero-inhouse:apero-ads:7.4.1'
    }
    ```
## Ad Banner Integration

1. Layout Container for Ad Banner
    ```bash 
    <FrameLayout
    android:id="@+id/fr_ads"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">
    <include layout="@layout/layout_banner_control" />
    </FrameLayout>  
    ```

2. Loading Normal Banner in Activity/Fragment
   For loading a normal banner in an Activity or Fragment:
    ```bash
    AperoAd.getInstance().loadBanner(this, idBanner)
    or
    AperoAd.getInstance().loadBannerFragment(mActivity, id, rootView)
    ```

3. Inline Banner in Activity/Fragment
    Styles:
      - Admob.BANNER_INLINE_SMALL_STYLE: Small inline banner
      - Admob.BANNER_INLINE_LARGE_STYLE: Large inline banner
    To load inline banners:
    ```
    Admob.getInstance().loadInlineBanner(activity, idBanner, inlineStyle, adCallback)
    or
    Admob.getInstance().loadInlineBannerFragment(activity, id, rootView, inlineStyle)
    ```
4. Collapsible Banner in Activity/Fragment
    Gravity Options:
      - AppConstant.TOP: Anchor the banner at the top
      - AppConstant.BOTTOM: Anchor the banner at the bottom
    To load collapsible banners:
    ```
    Admob.getInstance().loadCollapsibleBanner(mActivity, id, gravity, callback)
    or
    Admob.getInstance().loadCollapsibleBannerFragment(mActivity, id, rootView, gravity, callback)
    ```
5. Banner priority
    ```
        AperoAd.getInstance().loadBannerPriority(this,
            BuildConfig.banner_high_floor,
            BuildConfig.banner_medium,
            BuildConfig.banner_all_price,
            mViewDataBinding!!.frAds,
            AperoAd.REQUEST_TYPE.SAME_TIME,
            true,
            object : AperoAdCallback() {})
    ```








