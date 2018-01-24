package com.zxdmjr.myapp.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zxdmjr.myapp.R;
import com.zxdmjr.myapp.models.WelcomeMessage;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dutchman on 12/11/17.
 */

public class SlideAdapter extends PagerAdapter {

    private Context context;
    private List<WelcomeMessage> welcomeMessages;
    private LayoutInflater layoutInflater;

    @BindView(R.id.iv_image)
    ImageView ivImage;


    @BindView(R.id.tv_message)
    TextView tvMessage;

    public SlideAdapter(Context context, List<WelcomeMessage> welcomeMessages) {
        this.context = context;
        this.welcomeMessages = welcomeMessages;
    }

    @Override
    public int getCount() {
        return welcomeMessages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (RelativeLayout) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ButterKnife.bind(this, view);

        WelcomeMessage welcomeMessage = welcomeMessages.get(position);

        ivImage.setImageResource(welcomeMessage.getIcon());

        tvMessage.setText(welcomeMessage.getMessage());

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
