package com.example.chen1.uncom.utils;

import com.ashokvarma.bottomnavigation.ShapeBadgeItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.example.chen1.uncom.R;

/**
 * Created by chen1 on 2017/11/3.
 */

public class BadgeMessageUtil {

    public static Integer item_1=0;
    public static Integer item_2=0;
    public static Integer item_3=0;
    public static Integer item_4=0;
    public static boolean setPageIsVisible=true;
    public static boolean relationPageIsVisible=false;
    public static boolean findPageIsVisible=false;
    public static boolean myPageIsVisible=false;





    public static TextBadgeItem item1;
    public static TextBadgeItem item2;
    public static TextBadgeItem item3;
    public static TextBadgeItem item4;

    static {
        item1=new TextBadgeItem().setBorderWidth(4)
                .setAnimationDuration(200)
                .setBackgroundColorResource(R.color.colorTranslate)
                .setBorderColorResource(R.color.colorTranslate)
                .setTextColorResource(R.color.colorTranslate)
                .setHideOnSelect(false)
                .setText(item_1.toString());
        item2=new TextBadgeItem().setBorderWidth(4)
                .setAnimationDuration(200)
                .setBackgroundColorResource(R.color.colorTranslate)
                .setBorderColorResource(R.color.colorTranslate)
                .setTextColorResource(R.color.colorTranslate)
                .setHideOnSelect(false)
                .setText(item_2.toString());
        item3=new TextBadgeItem().setBorderWidth(4)
                .setAnimationDuration(200)
                .setBackgroundColorResource(R.color.colorTranslate)
                .setBorderColorResource(R.color.colorTranslate)
                .setTextColorResource(R.color.colorTranslate)
                .setHideOnSelect(false)
                .setText(item_3.toString());
        item4=new TextBadgeItem().setBorderWidth(4)
                .setAnimationDuration(200)
                .setBackgroundColorResource(R.color.colorTranslate)
                .setBorderColorResource(R.color.colorTranslate)
                .setTextColorResource(R.color.colorTranslate)
                .setHideOnSelect(false)
                .setText(item_4.toString());

    }

    public static TextBadgeItem getBadgeItemByPosition(int position){
        switch (position){
            case 0:
                return item1;
            case 1:
                return item2;
            case 2:
                return item3;
            case 3:
                return item4;

        }
        return item1;
    }

    public static Integer clearBadgeCountByPosition(int position){
        switch (position){
            case 0:
                item_1=0;
                break;
            case 1:
                item_2=0;
                break;
            case 2:
                item_3=0;
                break;
            case 3:
                item_4=0;
                break;
            default:
                item_1=0;
                break;
        }
        return null;
    }

    public static Integer getBadgeCountByPosition(int position){
        switch (position){
            case 0:
                return item_1;
            case 1:
                return item_2;
            case 2:
                return item_3;
            case 3:
                return item_4;
            default:
                return item_1;
        }

    }


    public static boolean isVisibleByPosition(int position){
        switch (position){
            case 0:
                return setPageIsVisible;
            case 1:
                return relationPageIsVisible;
            case 2:
                return findPageIsVisible;
            case 3:
                return myPageIsVisible;
            default:
                return  setPageIsVisible;
        }

    }

    /**
     * 转换透明与真实
     * @param position
     */
    public static void switchBadgeColor(int position){
        TextBadgeItem badgeItem;
        badgeItem=getBadgeItemByPosition(position);
        if(isVisibleByPosition(position)==true){
            clearBadgeCountByPosition(position);
            badgeItem.setBackgroundColorResource(R.color.colorTranslate)
                    .setBorderColorResource(R.color.colorTranslate)
                    .setTextColorResource(R.color.colorTranslate);

        }else if(getBadgeCountByPosition(position)!=0){
            badgeItem.setText(getBadgeCountByPosition(position).toString());
            badgeItem.setBackgroundColorResource(R.color.colorMain)
                    .setBorderColorResource(R.color.colorMain)
                    .setTextColorResource(R.color.colorPrimary);
        }

    }

    public static Integer getItem_1() {
        return item_1;
    }

    public static void setItem_1(Integer item_1) {
        BadgeMessageUtil.item_1 = item_1;
        switchBadgeColor(0);
    }

    public static Integer getItem_2() {
        return item_2;
    }

    public static void setItem_2(Integer item_2) {
        BadgeMessageUtil.item_2 = item_2;
    }

    public static Integer getItem_3() {
        return item_3;
    }

    public static void setItem_3(Integer item_3) {
        BadgeMessageUtil.item_3 = item_3;
    }

    public static Integer getItem_4() {
        return item_4;
    }

    public static void setItem_4(Integer item_4) {
        BadgeMessageUtil.item_4 = item_4;
    }

    public static boolean isSetPageIsVisible() {
        return setPageIsVisible;
    }

    public static void setSetPageIsVisible(boolean setPageIsVisible) {
        BadgeMessageUtil.setPageIsVisible = setPageIsVisible;
        switchBadgeColor(0);
    }

    public static boolean isRelationPageIsVisible() {
        return relationPageIsVisible;
    }

    public static void setRelationPageIsVisible(boolean relationPageIsVisible) {
        BadgeMessageUtil.relationPageIsVisible = relationPageIsVisible;
    }

    public static boolean isFindPageIsVisible() {
        return findPageIsVisible;
    }

    public static void setFindPageIsVisible(boolean findPageIsVisible) {
        BadgeMessageUtil.findPageIsVisible = findPageIsVisible;
    }

    public static boolean isMyPageIsVisible() {
        return myPageIsVisible;
    }

    public static void setMyPageIsVisible(boolean myPageIsVisible) {
        BadgeMessageUtil.myPageIsVisible = myPageIsVisible;
    }
}
