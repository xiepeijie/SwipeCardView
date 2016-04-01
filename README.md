# SwipeCardView
SwipeCard效果是基于Diolor的[Swipecards](https://github.com/Diolor/Swipecards)控件改进实现，通过添加了滑动渐变层叠动画，达到更佳的滑动刷脸体验。  
thanks Diolor Swipecards  

# Screenshot
![](https://github.com/xiepeijie/SwipeCardView/blob/master/ezgif.com.gif "Optional title")

# Usage
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
核心用法（即AdapterView的用法）
```
swipeView = (SwipeFlingAdapterView) findViewById(R.id.swipe_view);
        //swipeView.setIsNeedSwipe(true);// 是否开启swipe滑动效果，当不调用此方法设置时，默认开启。
        swipeView.setFlingListener(this);
        swipeView.setOnItemClickListener(this);
```
onFlingListener的回调方法
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
OnItemClickListener的回调方法
```
    @Override
    public void onItemClicked(MotionEvent event, View v, Object dataObject) {
        if (v.getTag() instanceof ViewHolder) {
            int x = (int) event.getRawX();
            int y = (int) event.getRawY();
            ViewHolder vh = (ViewHolder) v.getTag();
            View child = vh.portraitView;
            Rect outRect = new Rect();
            child.getGlobalVisibleRect(outRect);
            if (outRect.contains(x, y)) {
                AppToast.show(this, "click 大图");
            } else {
                outRect.setEmpty();
                child = vh.collectView;
                child.getGlobalVisibleRect(outRect);
                if (outRect.contains(x, y)) {
                    AppToast.show(this, "click 关注");
                }
            }
        }
    }
```
也可参考[Swipecards](https://github.com/Diolor/Swipecards)，两者用法基本一致。  
# About me
微博：[@萧雾宇](http://weibo.com/payge)  
Gmail：xiepeijieapp@gmail.com

