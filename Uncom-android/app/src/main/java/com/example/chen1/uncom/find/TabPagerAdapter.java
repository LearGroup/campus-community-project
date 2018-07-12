package com.example.chen1.uncom.find;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.chen1.uncom.R;
import com.example.chen1.uncom.utils.DisplayUtils;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by chen1 on 2017/11/10.
 */

public class TabPagerAdapter extends PagerAdapter {
    String[] date = {"一周","二周","三周","四周","五周","六周","七周"};//X轴的标注
    int[] score= {2,4,2,5,6,7,10};//图表的数据点
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();



    private LineChartView lineChart;
    private Context context;
    private GridView gridView;
    private ArrayList<View> views=new ArrayList<>();
    public TabPagerAdapter(Context context){
        this.context=context;
        View view_1 = View.inflate(context, R.layout.fragment_tab_page_for_abs, null);
        View view_2 = View.inflate(context, R.layout.fragment_tab_page_for_detail, null);
        View view_3= View.inflate(context, R.layout.fragment_tab_page_for_data, null);
        views.add(view_1);
        views.add(view_2);
        views.add(view_3);
        lineChart= (LineChartView) views.get(2).findViewById(R.id.chart);
        getAxisXLables();//获取x轴的标注
        getAxisPoints();//获取坐标点
        initLineChart();
    }



    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = views.get(position);
        switch (position){
            case 0:
                break;
            case 1:
                gridView= (GridView) view.findViewById(R.id.data_grid);
                int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
                int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
                gridView.measure(w, h); Log.v("creating。。。。。。.......................。 ICON","SUCCESS");
                int height =gridView.getMeasuredHeight();
                int width =gridView.getMeasuredWidth();
                gridView.setNumColumns(7);
                gridView.setHorizontalSpacing(0);
                gridView.setVerticalSpacing(13);
                gridView.setAdapter(new ActiveTimeAdapterForWeek());
                break;
            case 2:

                break;
        }
        if(container.getChildAt(position)==views.get(position)){
            container.removeView(views.get(position));
        }
        container.addView(views.get(position));
        return views.get(position);
    }



    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    /**
     * 设置X轴显示
     */
    private void    getAxisXLables(){
        for (int i = 0; i <date.length ; i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(date[i]));
        }
    }

    /**
     * 图表每个点的显示
     */
    private void getAxisPoints(){
        for (int i = 0; i <score.length ; i++) {
            mPointValues.add(new PointValue(i,score[i]));
        }
    }


    private void initLineChart(){
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) lineChart.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20

        linearParams.width = (int) (DisplayUtils.getWindowWidth(context)*0.5*((float)mPointValues.size()/(float)7));// 控件的宽强制设成30

        Log.v("float",""+(((float)3/((float)6))));
        lineChart.setLayoutParams(linearParams); //使设置好的布局参数应用到控件



        Line line = new Line(mPointValues).setColor(Color.parseColor("#037D66"));  //折线的颜色（橙色）
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
        line.setCubic(true);//曲线是否平滑，即是曲线还是折线
        line.setFilled(false);//是否填充曲线的面积
        line.setHasLabels(false);//曲线的数据坐标是否加上备注
//      line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
        line.setPointRadius(2);
        line.setStrokeWidth(1);
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(false);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
        axisX.setTextColor(Color.BLACK);  //设置字体颜色
        //axisX.setName("最近6周期活跃度");  //表格名称
        axisX.setTextSize(9);//设置字体大小

        axisX.setMaxLabelChars(8); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部
        //data.setAxisXTop(axisX);  //x 轴在顶部
        axisX.setHasLines(false); //x 轴分割线
        // Y轴是根据数据的大小自动设置Y轴上限(在下面我会给出固定Y轴数据个数的解决方案)
        Axis axisY = new Axis();  //Y轴
        axisY.setName("");//y轴标注
        axisY.setTextSize(8);//设置字体大小
        axisY.setTextColor(Color.BLACK);
        data.setAxisYLeft(axisY);  //Y轴设置在左边
        //data.setAxisYRight(axisY);  //y轴设置在右边
        data.setValueLabelTextSize(5);
        //设置行为属性，支持缩放、滑动以及平移
        lineChart.setInteractive(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL);
        lineChart.setZoomType(ZoomType.VERTICAL);
        lineChart.setMaxZoom((float) 1);//最大方法比例
        lineChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChart.setLineChartData(data);
        lineChart.setVisibility(View.VISIBLE);
        /**注：下面的7，10只是代表一个数字去类比而已
         * 当时是为了解决X轴固定数据个数。见（http://forum.xda-developers.com/tools/programming/library-hellocharts-charting-library-t2904456/page2）;
         */
        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.top= 10;
        v.bottom=0;
        lineChart.setMaximumViewport(v);
        v.right= 7;
        v.left = 0;
        lineChart.setCurrentViewport(v);
    }
}


