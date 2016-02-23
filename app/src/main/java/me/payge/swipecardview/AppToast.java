package me.payge.swipecardview;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


public class AppToast {

	private static int BLUE = 0;
	private static int RED = 0;

	public static int getBLUE(Context context) {
		if (BLUE == 0)
			BLUE = Color.parseColor("#4a90e2");
		return BLUE;
	}

	public static int getRED(Context context) {
		if (RED == 0)
			RED = Color.RED;
		return RED;
	}

	public static void warning(Context context, int stringId) {
		warning(context, context.getString(stringId));
	}

	public static void warning(Context context, CharSequence string) {
		show(context, getRED(context), string, Toast.LENGTH_LONG);
	}

	public static void show(Context context, int stringId) {
		show(context, context.getString(stringId));
	}

	public static void show(Context context, CharSequence string) {
		show(context, getBLUE(context), string, Toast.LENGTH_SHORT);
	}

	public static void show(Context context, int color, CharSequence string, int duration) {
		if(context == null) return;

		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		float density = dm.density;
		Toast toast = new Toast(context);
		toast.setDuration(duration);
		toast.setGravity(Gravity.TOP, 0, (int) (48 * density));
		FrameLayout frame = new FrameLayout(context);
		frame.setPadding(0, 0, 0, 0);
		int width = dm.widthPixels;
		FrameLayout.LayoutParams params =
				new FrameLayout.LayoutParams(width, FrameLayout.LayoutParams.WRAP_CONTENT);
		TextView text = new TextView(context);
		text.setMinimumHeight((int) (28 * density));
		text.setGravity(Gravity.CENTER);
		text.setTextColor(Color.WHITE);
		text.setTextSize(14);
		frame.addView(text, params);
		toast.setView(frame);

		text.setBackgroundColor(color);
		text.setText(string);
		toast.show();
	}

}
