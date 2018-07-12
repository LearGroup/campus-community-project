package com.example.chen1.uncom.me;

import android.app.AlertDialog;
import android.content.ClipData;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chen1.uncom.R;

/**
 * Created by chen1 on 2018/2/5.
 */

public class PhotoSelectorDialog extends DialogFragment implements View.OnClickListener{


    private View view;
    private ItemOnclickListener itemOnclickListener;
    public PhotoSelectorDialog() {
        super();
    }

    public interface ItemOnclickListener{
        void onClick(String type);
    }
    public void setItemOnclickListener(ItemOnclickListener itemOnclickListener){
        this.itemOnclickListener=itemOnclickListener;
    }
    public static PhotoSelectorDialog newInstance(){
        PhotoSelectorDialog photoSelectorDialog=new PhotoSelectorDialog();
        return  photoSelectorDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        this.view=inflater.inflate(R.layout.dialog_photo_selector, null);
        AppCompatTextView camera= (AppCompatTextView) view.findViewById(R.id.camera);
        AppCompatTextView photo= (AppCompatTextView) view.findViewById(R.id.photo);
        camera.setTag(R.id.imageid,"camera");
        photo.setTag(R.id.imageid,"photo");
        camera.setOnClickListener(this);
        photo.setOnClickListener(this);
        builder.setView(view);
        return builder.create();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View v) {
        if(itemOnclickListener!=null){
            itemOnclickListener.onClick((String) v.getTag(R.id.imageid));
        }
    }
}
