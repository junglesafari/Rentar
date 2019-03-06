package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.example.myapplication.activities.MainActivity;
import com.example.myapplication.modelclass.Imageclass;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class homeFrag extends Fragment {



    private ViewPager viewPager;
    private LinearLayout layout_dots;
    private Runnable runnable = null;
    private Handler handler = new Handler();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

     private String mParam1;
    private String mParam2;
    AdapterImageSlider adapterImageSlider;

    private OnFragmentInteractionListener mListener;

    public homeFrag() {
        // Required empty public constructor
    }

    public static homeFrag newInstance(String param1, String param2) {
        homeFrag fragment = new homeFrag();
        Bundle args = new Bundle();
        args.putString( ARG_PARAM1, param1 );
        args.putString( ARG_PARAM2, param2 );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        if (getArguments() != null) {
            mParam1 = getArguments().getString( ARG_PARAM1 );
            mParam2 = getArguments().getString( ARG_PARAM2 );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
View output = inflater.inflate( R.layout.fragment_home, container, false );

        initComponent(output);
        return output;
    }

     public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction( uri );
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach( context );
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException( context.toString() + " must implement OnFragmentInteractionListener" );
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

       public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



    private void initComponent(final View output) {

        layout_dots = (LinearLayout) output.findViewById( R.id.layout_dots );
        viewPager = (ViewPager) output.findViewById( R.id.developerpager );

        int[] array_image_place = {
                R.drawable.dp1,
                R.drawable.dp2,
                R.drawable.dp3,
                R.drawable.dp4,
        };

        String[] array_title_place = {
                "Rentar",
                "Rentar",
                "Rentar",
                "Rentar"

        };

        String[] array_brief_place = {
                "Bundelkhand Institute of Engineering and Technology",
                "Bundelkhand Institute of Engineering and Technology",
                "Bundelkhand Institute of Engineering and Technology",
                "Bundelkhand Institute of Engineering and Technology"
        };
        final ArrayList<Imageclass> items = new ArrayList<>();

        for (int i = 0; i < array_image_place.length; i++) {
            Imageclass obj = new Imageclass();
            obj.image = array_image_place[i];
            obj.imageDrw = getResources().getDrawable(obj.image);
            obj.name = array_title_place[i];
            obj.brief = array_brief_place[i];
            items.add(obj);
        }

        adapterImageSlider = new AdapterImageSlider( getActivity(), items );

        adapterImageSlider.setItems( items );
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());

        viewPager.setAdapter( adapterImageSlider );


        // displaying selected image first
        viewPager.setCurrentItem( 0 );
//        addBottomDots( layout_dots, adapterImageSlider.getCount(), 0 );
        ((TextView) output.findViewById( R.id.title )).setText( items.get( 0 ).name );
        ((TextView) output.findViewById( R.id.brief )).setText( items.get( 0 ).brief );
        viewPager.addOnPageChangeListener( new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int pos, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int pos) {
                ((TextView) output.findViewById( R.id.title )).setText( items.get( pos ).name );
                ((TextView) output.findViewById( R.id.brief )).setText( items.get( pos ).brief );
//                addBottomDots( layout_dots, adapterImageSlider.getCount(), pos );
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        } );

        startAutoSlider( adapterImageSlider.getCount() );




    }
//    private void addBottomDots(LinearLayout layout_dots, int size, int current) {
//        ImageView[] dots = new ImageView[size];
//
//        layout_dots.removeAllViews();
//        for (int i = 0; i < dots.length; i++) {
//            dots[i] = new ImageView( getContext() );
//            int width_height = 15;
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( new ViewGroup.LayoutParams( width_height, width_height ) );
//            params.setMargins( 10, 10, 10, 10 );
//            dots[i].setLayoutParams( params );
//            dots[i].setImageResource( R.drawable.shape_circle_outline );
//            layout_dots.addView( dots[i] );
//        }
//
//        if (dots.length > 0) {
//            dots[current].setImageResource( R.drawable.shape_circle );
//        }
//    }

    private void startAutoSlider(final int count) {
        runnable = new Runnable() {
            @Override
            public void run() {
                int pos = viewPager.getCurrentItem();
                pos = pos + 1;
                if (pos >= count) pos = 0;
                viewPager.setCurrentItem( pos );
                handler.postDelayed( runnable, 3000 );
            }
        };
        handler.postDelayed( runnable, 3000 );
    }

    private static class AdapterImageSlider extends PagerAdapter {

        private Activity act;
        private List<Imageclass> items;

        private AdapterImageSlider.OnItemClickListener onItemClickListener;

        private interface OnItemClickListener {
            void onItemClick(View view, Imageclass obj);
        }

        public void setOnItemClickListener(AdapterImageSlider.OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        // constructor
        private AdapterImageSlider(Activity activity, List<Imageclass> items) {
            this.act = activity;
            this.items = items;
        }

        @Override
        public int getCount() {
            return this.items.size();
        }

        public Imageclass getItem(int pos) {
            return items.get(pos);
        }

        public void setItems(List<Imageclass> items) {
            this.items = items;
            notifyDataSetChanged();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final Imageclass o = items.get(position);
            LayoutInflater inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            View v = inflater.inflate(R.layout.item_slider_image, container, false);

            final ImageView image = (ImageView) v.findViewById(R.id.image);
            MaterialRippleLayout lyt_parent = (MaterialRippleLayout) v.findViewById(R.id.lyt_parent);
            //  Tools.displayImageOriginal(act, image, o.image);
            lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, o);
                    }
                }
            });
            Picasso
                    .with( act )
                    .load(  o.image  )
                    .fit()
                    .centerCrop()
                    .placeholder( R.drawable.ic_sentiment_satisfied_black_24dp )
                    .into(image  );


            ((ViewPager) container).addView(v);

            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((RelativeLayout) object);

        }

    }

    public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0f);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0f);
            }
        }
    }

}
