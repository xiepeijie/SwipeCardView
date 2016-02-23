package me.payge.swipecardview;

import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements SwipeFlingAdapterView.onFlingListener,
        SwipeFlingAdapterView.OnItemClickListener {

    String [] headerIcons = {"http://www.5djiaren.com/uploads/2015-04/17-115301_29.jpg",
                            "http://img1.dzwww.com:8080/tupian_pl/20160106/32/4152697013403556460.jpg",
                            "http://c.hiphotos.baidu.com/zhidao/pic/item/72f082025aafa40f191362cfad64034f79f019ce.jpg",
                            "http://img.article.pchome.net/new/w600/00/35/15/66/pic_lib/wm/122532981493137o3iegiyx.jpg",
                            "http://img0.imgtn.bdimg.com/it/u=3382799710,1639843170&fm=21&gp=0.jpg",
                            "http://i2.sinaimg.cn/travel/2014/0918/U7398P704DT20140918143217.jpg",
                            "http://photo.l99.com/bigger/21/1415193165405_4sg3ds.jpg",
                            "http://img.pconline.com.cn/images/upload/upc/tx/photoblog/1305/15/c2/20949108_20949108_1368599174341.jpg",
                            "http://pic29.nipic.com/20130501/12558275_114724775130_2.jpg",
                            "http://photo.l99.com/bigger/20/1415193157174_j2fa5b.jpg"};

    String [] names = {"张三","李四","王五","小明","小红","小花"};

    String [] citys = {"北京", "上海", "广州", "深圳"};

    String [] edus = {"大专", "本科", "硕士", "博士"};

    String [] years = {"1年", "2年", "3年", "4年", "5年"};

    Random ran = new Random();

    private int cardWidth;
    private int cardHeight;

    private SwipeFlingAdapterView swipeView;
    private InnerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        loadData();
    }

    private void initView() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        float density = dm.density;
        cardWidth = (int) (dm.widthPixels - (2 * 18 * density));
        cardHeight = (int) (dm.heightPixels - (338 * density));


        swipeView = (SwipeFlingAdapterView) findViewById(R.id.swipe_view);
        //swipeView.setIsNeedSwipe(true);
        swipeView.setFlingListener(this);
        swipeView.setOnItemClickListener(this);

        adapter = new InnerAdapter();
        swipeView.setAdapter(adapter);
    }


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

    @Override
    public void onScroll(float progress, float scrollXProgress) {
    }

    private void loadData() {
        AsyncTaskCompat.executeParallel(new AsyncTask<Void, Void, List<Talent>>() {
            @Override
            protected List<Talent> doInBackground(Void... params) {
                ArrayList<Talent> list = new ArrayList<>(10);
                Talent talent;
                for (int i = 0; i < 10; i++) {
                    talent = new Talent();
                    talent.headerIcon = headerIcons[i];
                    talent.nickname = names[ran.nextInt(names.length-1)];
                    talent.cityName = citys[ran.nextInt(citys.length-1)];
                    talent.educationName = edus[ran.nextInt(edus.length-1)];
                    talent.workYearName = years[ran.nextInt(years.length-1)];
                    list.add(talent);
                }
                return list;
            }

            @Override
            protected void onPostExecute(List<Talent> list) {
                super.onPostExecute(list);
                adapter.addAll(list);
            }
        });
    }


    private class InnerAdapter extends BaseAdapter {

        ArrayList<Talent> objs;

        public InnerAdapter() {
            objs = new ArrayList<>();
        }

        public void addAll(Collection<Talent> collection) {
            if (isEmpty()) {
                objs.addAll(collection);
                notifyDataSetChanged();
            } else {
                objs.addAll(collection);
            }
        }

        public void clear() {
            objs.clear();
            notifyDataSetChanged();
        }

        public boolean isEmpty() {
            return objs.isEmpty();
        }

        public void remove(int index) {
            if (index > -1 && index < objs.size()) {
                objs.remove(index);
                notifyDataSetChanged();
            }
        }


        @Override
        public int getCount() {
            return objs.size();
        }

        @Override
        public Talent getItem(int position) {
            if(objs==null ||objs.size()==0) return null;
            return objs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        // TODO: getView
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Talent talent = getItem(position);
            if (convertView == null)
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_new_item, parent, false);
            ViewHolder holder = new ViewHolder();
            convertView.setTag(holder);
            convertView.getLayoutParams().width = cardWidth;
            holder.portraitView = (SimpleDraweeView) convertView.findViewById(R.id.portrait);
            //holder.portraitView.getLayoutParams().width = cardWidth;
            holder.portraitView.getLayoutParams().height = cardHeight;
            holder.nameView = (TextView) convertView.findViewById(R.id.name);
            //parentView.getLayoutParams().width = cardWidth;
            //holder.jobView = (TextView) convertView.findViewById(R.id.job);
            //holder.companyView = (TextView) convertView.findViewById(R.id.company);
            holder.cityView = (TextView) convertView.findViewById(R.id.city);
            holder.eduView = (TextView) convertView.findViewById(R.id.education);
            holder.workView = (TextView) convertView.findViewById(R.id.work_year);
            holder.collectView = (CheckedTextView) convertView.findViewById(R.id.favorite);

            holder.portraitView.setImageURI(Uri.parse(talent.headerIcon));
            holder.nameView.setText(String.format("%s", talent.nickname));
            //holder.jobView.setText(talent.jobName);

            final CharSequence no = "暂无";

            holder.cityView.setHint(no);
            holder.cityView.setText(talent.cityName);
            holder.cityView.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.home01_icon_location,0,0);

            holder.eduView.setHint(no);
            holder.eduView.setText(talent.educationName);
            holder.eduView.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.home01_icon_edu,0,0);

            holder.workView.setHint(no);
            holder.workView.setText(talent.workYearName);
            holder.workView.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.home01_icon_work_year,0,0);


            return convertView;
        }

    }

    private static class ViewHolder {
        SimpleDraweeView portraitView;
        TextView nameView;
        TextView cityView;
        TextView eduView;
        TextView workView;
        CheckedTextView collectView;

    }

    public static class Talent {

        public String headerIcon;
        public String nickname;
        public String cityName;
        public String educationName;
        public String workYearName;
    }

}
