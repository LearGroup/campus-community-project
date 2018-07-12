package com.example.chen1.uncom.utils;

import android.util.Log;

import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;

/**
 * Created by chen1 on 2017/10/19.
 */

public class Test {
   public Test(){
       this.list=new ArrayList<>();
       this.list.add("Test");
   }

    private ArrayList<String> list;

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    public  static void main(){
        Test s=new Test();
        for (String str : s.getList()) {
            Log.v("str",str);
        }

    }
}
