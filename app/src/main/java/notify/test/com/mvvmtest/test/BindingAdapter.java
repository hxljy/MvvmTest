package notify.test.com.mvvmtest.test;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by hxl on 2019/2/18
 */

public class BindingAdapter {

    @android.databinding.BindingAdapter({"url"})
    public static void setImageUrl(ImageView iv, String url) {
        Glide.with(iv.getContext())
                .load(url)
                .into(iv);
    }
}
