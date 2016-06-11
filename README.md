# SwipeCardView
SwipeCard效果是基于Diolor的[Swipecards](https://github.com/Diolor/Swipecards)控件改进实现，通过添加了滑动渐变层叠动画，达到更佳的滑动刷脸体验。  
thanks Diolor Swipecards  

## Screenshot
![screen](https://github.com/xiepeijie/SwipeCardView/blob/master/ezgif.com.gif)

## Relative Project
[SwipeAdapterView](https://github.com/xiepeijie/SwipeAdapterView)

## Usage
### XML配置：
```
<com.lorentzos.flingswipe.SwipeFlingAdapterView
        android:id="@+id/swipe_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        swipe:min_adapter_stack="4"
        swipe:max_visible="4"
        swipe:y_offset_step="28dp"/>
```
### Java Code：
```
swipeView = (SwipeFlingAdapterView) findViewById(R.id.swipe_view);
        //swipeView.setIsNeedSwipe(true);// 是否开启swipe滑动效果，当不调用此方法设置时，默认开启。
        swipeView.setFlingListener(this);
        swipeView.setOnItemClickListener(this);
```
onFlingListener
```
    @Override
    public void removeFirstObjectInAdapter() {
        adapter.remove(0);
    }

    @Override
    public void onLeftCardExit(Object dataObject) {
        AppToast.show(this, "swipe left card");
    }

    @Override
    public void onRightCardExit(Object dataObject) {
        AppToast.show(this, "swipe right card");
    }

    @Override
    public void onAdapterAboutToEmpty(int itemsInAdapter) {
        if (itemsInAdapter == 3) {
            loadData();
        }
    }
```
 
# About me
微博：[@萧雾宇](http://weibo.com/payge)  
