package com.example.myapplication5;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/*import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;*/

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LocalDeliverable#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocalDeliverable extends Fragment {

    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    LinearLayout demoVideosLayout;
    LinearLayout interactivesLayout;
    LinearLayout mentorshipLayout;
    LinearLayout eventsLayout;

    private ItemViewModel viewModel;




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LocalDeliverable() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LocalDeliverable.
     */
    // TODO: Rename and change types and number of parameters
    public static LocalDeliverable newInstance(String param1, String param2) {
        LocalDeliverable fragment = new LocalDeliverable();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

   /* @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);



        /*items.setOnClickListener(item -> {
            // Set a new item
            viewModel.select(item);
        });
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_local_deliverable, container, false);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();


        SliderView sliderView = view.findViewById(R.id.imageSlider);
        SliderAdapter adapter = new SliderAdapter(getContext());

        SliderItem sliderItem = new SliderItem();
        sliderItem.setDescription("First Image");
        try {
            sliderItem.setImageUrl(new URL("https://www.feedough.com/wp-content/uploads/2019/07/sales-promotion.png"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        SliderItem sliderItem1 = new SliderItem();
        sliderItem1.setDescription("Second Image");
        try {
            sliderItem1.setImageUrl(new URL("https://www.vir.com.vn/stores/news_dataimages/vananh/112018/11/20/in_article/biggest-sales-promotions-officially-kick-off.jpg"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        SliderItem sliderItem2 = new SliderItem();
        sliderItem2.setDescription("Third Image");
        try {
            sliderItem2.setImageUrl(new URL("https://1.bp.blogspot.com/-9E8wgLEDB_w/Xeh4896sDvI/AAAAAAAAGDk/UA8hxGGlStYVWT9DLsS_bXk8KwaF8KNqACLcBGAsYHQ/s640/lazada-12-12-sale-2019.png"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        SliderItem sliderItem3 = new SliderItem();
        sliderItem3.setDescription("Fourth Image");
        try {
            sliderItem3.setImageUrl(new URL("https://klgadgetguy.com/wp-content/uploads/2019/12/Lazada-Grand-year-End-Sale.jpg"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        SliderItem sliderItem4 = new SliderItem();
        sliderItem4.setDescription("Fifth Image");
        try {
            sliderItem4.setImageUrl(new URL("https://www.learndash.com/wp-content/uploads/special-deal-sign-1.jpg"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        adapter.addItem(sliderItem);
        adapter.addItem(sliderItem1);
        adapter.addItem(sliderItem2);
        adapter.addItem(sliderItem3);
        adapter.addItem(sliderItem4);

        sliderView.setSliderAdapter(adapter);


        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();

        final ImageView testImgView = view.findViewById(R.id.testImage);

        StorageReference testImageRef = firebaseStorage.getReferenceFromUrl("gs://mobi-linx.appspot.com/ProductImages/1613393758458.jpg");
        try {
            final File localFile = File.createTempFile("image","jpg");
            testImageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Picasso.with(getContext()).load(localFile).into(testImgView);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } {

        }

        demoVideosLayout = (LinearLayout)view.findViewById(R.id.demo_vids_layout);
        demoVideosLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),DemoVideosActivity.class);
                startActivity(intent);
            }
        });

        interactivesLayout = (LinearLayout)view.findViewById(R.id.interatives_vids_layout);
        interactivesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),InteractivesActivity.class);
                startActivity(intent);
            }
        });

        mentorshipLayout = (LinearLayout)view.findViewById(R.id.mentorship_vids_layout);
        mentorshipLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),MentorshipActivity.class);
                startActivity(intent);
            }
        });

        eventsLayout = (LinearLayout)view.findViewById(R.id.events_vids_layout);
        eventsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),EventsActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}

class SliderAdapter extends
        SliderViewAdapter<SliderAdapter.SliderAdapterVH> {

    private Context context;
    private List<SliderItem> mSliderItems = new ArrayList<>();

    public SliderAdapter(Context context) {
        this.context = context;
    }

    public void renewItems(List<SliderItem> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(SliderItem sliderItem) {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        SliderItem sliderItem = mSliderItems.get(position);

        viewHolder.textViewDescription.setText(sliderItem.getDescription());
        viewHolder.textViewDescription.setTextSize(16);
        viewHolder.textViewDescription.setTextColor(Color.WHITE);
        Glide.with(viewHolder.itemView)
                .load(sliderItem.getImageUrl())
                .fitCenter()
                .into(viewHolder.imageViewBackground);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderItems.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        ImageView imageGifContainer;
        TextView textViewDescription;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            imageGifContainer = itemView.findViewById(R.id.iv_gif_container);
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
            this.itemView = itemView;
        }
    }

}

class SliderItem{
    private URL imageUrl;
    private String description;

    public SliderItem(){}

    public void setImageUrl(URL imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public URL getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

}

class ItemViewModel extends ViewModel {
    private final MutableLiveData<ClipData.Item> selectedItem = new MutableLiveData<ClipData.Item>();
    public void selectItem(ClipData.Item item) {
        selectedItem.setValue(item);
    }
    public LiveData<ClipData.Item> getSelectedItem() {
        return selectedItem;
    }
}
