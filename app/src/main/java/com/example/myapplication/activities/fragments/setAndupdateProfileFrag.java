package com.example.myapplication.activities.fragments;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.myapplication.R;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link setAndupdateProfileFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link setAndupdateProfileFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class setAndupdateProfileFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private android.support.v7.widget.AppCompatSpinner collegeSpinner;
    private android.support.v7.widget.AppCompatSpinner hostle_name_spinner;

    private static final int PICK_IMAGE_REQUEST = 1;
    private  Uri mImageuri;
    private  android.support.v7.widget.AppCompatButton updateImageButton;
    private CircularImageView profileImage;

    private OnFragmentInteractionListener mListener;

    public setAndupdateProfileFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment setAndupdateProfileFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static setAndupdateProfileFrag newInstance(String param1, String param2) {
        setAndupdateProfileFrag fragment = new setAndupdateProfileFrag();
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
      View output =  inflater.inflate( R.layout.fragment_set_andupdate_profile, container, false );
   collegeSpinner = output.findViewById( R.id.College_name_spinner );
        String[] collegeName = {"B.I.E.T"};
        ArrayAdapter<String> college_spinner_adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,collegeName);
        college_spinner_adapter.setDropDownViewResource( android.R.layout.simple_dropdown_item_1line );
        collegeSpinner.setAdapter( college_spinner_adapter );
        collegeSpinner.setOnItemSelectedListener( (AdapterView.OnItemSelectedListener) getContext() );

        hostle_name_spinner = output.findViewById( R.id.hostle_name_spinner );
        String[] hostel_names = {"Vrindavan Hostel","Saket Hostel","Panchvati Hostel"
                ,"Jai bharat Bhavan","Yshodhara Bhavan","Kalpna chawla Hostel"};

        ArrayAdapter<String> Hostel_name_adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,hostel_names);
        hostle_name_spinner.setAdapter( Hostel_name_adapter );
        hostle_name_spinner.setOnItemSelectedListener( (AdapterView.OnItemSelectedListener) getContext() );



        profileImage = output.findViewById( R.id.profileImageUpdate );
        updateImageButton = output.findViewById( R.id.profileImageUpdateButton );
        updateImageButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openfilechooser();
            }
        } );



   return output;
    }

    // TODO: Rename method, update argument and hook method into UI event
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

    private void openfilechooser(){
        Intent intent=new Intent(  );
        intent.setType( "image/*" );
        intent.setAction(Intent.ACTION_GET_CONTENT  );
        startActivityForResult( intent,PICK_IMAGE_REQUEST );

    }

    @Override
   public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==PICK_IMAGE_REQUEST&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
            mImageuri=data.getData();
            Picasso.with( getContext() ).load( mImageuri ).into( profileImage );
        }
        super.onActivityResult( requestCode, resultCode, data );
    }

    private String getfileextension(Uri uri){
        ContentResolver cr= Objects.requireNonNull( getActivity() ).getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType( cr.getType( uri ) );
    }



}
