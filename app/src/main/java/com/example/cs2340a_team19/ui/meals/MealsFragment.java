package com.example.cs2340a_team19.ui.meals;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.SingleValueDataSet;
import com.anychart.charts.CircularGauge;
import com.anychart.core.Text;
import com.anychart.core.axes.Circular;
import com.anychart.core.gauge.pointers.Bar;
import com.anychart.enums.Anchor;
import com.anychart.graphics.vector.Fill;
import com.anychart.graphics.vector.SolidFill;
import com.anychart.graphics.vector.text.HAlign;
import com.anychart.graphics.vector.text.VAlign;
import com.example.cs2340a_team19.R;
import com.example.cs2340a_team19.databinding.FragmentMealsBinding;

import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;

import java.util.ArrayList;
import java.util.List;

public class MealsFragment extends Fragment {

    private FragmentMealsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MealsViewModel mealsViewModel =
                new ViewModelProvider(this).get(MealsViewModel.class);

        binding = FragmentMealsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        //TODO: get rid of this test
        setPersonalInfo(2000, 6, 89, false);

        return root;
    }

    @Override
    public void onViewCreated(View view, @NonNull Bundle savedInstanceState) {
        //createPieChart(view);
        createGaugeChart(view);
    }
    //TODO: use this function to set the personal info
    private void setPersonalInfo(int caloriesRec, int height, int weight, boolean gender) {
        final TextView userCalorieRec = binding.CalculatedCalories;
        final TextView userHeight = binding.displayHeight;
        final TextView userWeight = binding.displayWeight;
        final TextView userGender = binding.displayGender;

        userCalorieRec.setText(String.valueOf(caloriesRec));
        userHeight.setText(String.valueOf(height));
        userWeight.setText(String.valueOf(weight));
        if (gender) {
            userGender.setText("Male");
        } else {
            userGender.setText("female");
        }
    }

    private void createGaugeChart(View root){
        AnyChartView anyChartView = root.findViewById(R.id.anychart_viz1_temp);
        anyChartView.setProgressBar(getView().findViewById(R.id.anychart_progress_bar));

        CircularGauge circularGauge = AnyChart.circular();
        //TODO: set data to be the one form database
        circularGauge.data(new SingleValueDataSet(new String[] {"52"}));
        circularGauge.fill("#fff")
                .stroke(null)
                .padding(0d, 0d, 0d, 0d)
                .margin(100d, 100d, 100d, 100d);
        circularGauge.startAngle(0d);
        circularGauge.sweepAngle(360d);

        Circular xAxis = circularGauge.axis(0)
                .radius(100d)
                .width(10d)
                .fill((Fill) null);
        xAxis.scale()
                .minimum(0d)
                .maximum(100d);
        xAxis.ticks("{ interval: 1 }")
                .minorTicks("{ interval: 1 }");
        xAxis.labels().enabled(false);
        xAxis.ticks().enabled(false);
        xAxis.minorTicks().enabled(false);
        //TODO: change text to be dyanmic
        circularGauge.label(0d)
                .text("Calories Goal, <span style=\"\">32%</span>")
                .useHtml(true)
                .hAlign(HAlign.CENTER)
                .vAlign(VAlign.MIDDLE);
        circularGauge.label(0d)
                .anchor(Anchor.CENTER)
                .padding(0d, 10d, 0d, 0d)
                .offsetX(0d);
        Bar bar0 = circularGauge.bar(0d);
        bar0.dataIndex(0d);
        bar0.radius(100d);
        bar0.width(50d);

        bar0.fill(new SolidFill("#64b500", 10d));
        bar0.stroke(null);
        bar0.zIndex(5d);
        Bar bar100 = circularGauge.bar(100d);
        bar100.dataIndex(5d);
        bar100.radius(100d);
        bar100.width(50d);
        bar100.fill(new SolidFill("#F5F4F4", 10d));
        bar100.stroke("1 #e5e4e4");
        bar100.zIndex(4d);

        circularGauge.margin(50d, 50d, 50d, 50d);

        circularGauge.title().enabled(false);
        circularGauge.title().hAlign(HAlign.CENTER);
        circularGauge.title()
                .padding(0d, 0d, 0d, 0d)
                .margin(0d, 0d, 20d, 0d);

        anyChartView.setChart(circularGauge);
    }
    private void createPieChart(View root) {
//TODO: other chart goes here

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}