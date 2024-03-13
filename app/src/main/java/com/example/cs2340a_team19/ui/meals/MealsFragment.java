package com.example.cs2340a_team19.ui.meals;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
//import android.widget.Toast;
//import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
//import androidx.lifecycle.ViewModelProvider;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.SingleValueDataSet;
import com.anychart.charts.Cartesian;
import com.anychart.charts.CircularGauge;
//import com.anychart.core.Text;
import com.anychart.core.axes.Circular;
import com.anychart.core.cartesian.series.Column;
import com.anychart.core.gauge.pointers.Bar;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Fill;
import com.anychart.graphics.vector.SolidFill;
import com.anychart.graphics.vector.text.HAlign;
import com.anychart.graphics.vector.text.VAlign;
import com.example.cs2340a_team19.R;
import com.example.cs2340a_team19.databinding.FragmentMealsBinding;

import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
//import com.anychart.chart.common.listener.Event;
//import com.anychart.chart.common.listener.ListenersInterface;
//import com.anychart.charts.Pie;
//import com.anychart.enums.Align;
//import com.anychart.enums.LegendLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

public class MealsFragment extends Fragment {

    private FragmentMealsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMealsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final Button barGraphGen = binding.barGraphButton;
        final Button gagueGraphGen = binding.gagueGraphButton;
        MealsViewModel mealsViewModel = new MealsViewModel(this);
        barGraphGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int[] weekCal = {2000, 2000, 2000, 2000, 2000, 2000, 2000};
                createBarChart(root, weekCal);
            }
        });

        gagueGraphGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createGaugeChart(root, mealsViewModel);
            }
        });


        return root;
    }

    @Override
    public void onViewCreated(View view, @NonNull Bundle savedInstanceState) {
        //createPieChart(view);
        MealsViewModel mealsViewModel = new MealsViewModel(this);

        Button addMealButton = view.findViewById(R.id.submit_meal_button);
        EditText mealName = view.findViewById(R.id.input_meal_name);
        EditText calorieCount = view.findViewById(R.id.input_meal_calorie);
        Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("EST"));
        int time = 10000 * calendar.get(Calendar.DAY_OF_YEAR) + 100
                * calendar.get(Calendar.HOUR_OF_DAY) + calendar.get(Calendar.MINUTE);
        addMealButton.setOnClickListener((View v) -> {
            mealsViewModel.createMeal(mealName.getText().toString(),
                    Integer.parseInt(calorieCount.getText().toString()), time);
            mealName.setText("");
            calorieCount.setText("");
        });
        //createGaugeChart(view);


        //mealsViewModel.getLastDays(weekCal, 7, this, view);
        //createBarChart(view, weekCal);

    }
    public void setPersonalInfo(int caloriesRec, int height, int weight, boolean gender) {
        final TextView userCalorieRec = binding.CalculatedCalories;
        final TextView userHeight = binding.displayHeight;
        final TextView userWeight = binding.displayWeight;
        final TextView userGender = binding.displayGender;

        userCalorieRec.setText(String.valueOf(caloriesRec));
        userHeight.setText(String.valueOf(height));
        userWeight.setText(String.valueOf(weight));
        if (gender) {
            userGender.setText("M");
        } else {
            userGender.setText("F");
        }
    }

    public void createGaugeChart(View root, MealsViewModel mealsViewModel) {

        AnyChartView anyChartGagueView = root.findViewById(R.id.anychart_viz1_temp);
        APIlib.getInstance().setActiveAnyChartView(anyChartGagueView);
        anyChartGagueView.setProgressBar(getView().findViewById(R.id.anychart_progress_bar));

        String calProgress = mealsViewModel.getCalorieProgress();


        CircularGauge circularGauge = AnyChart.circular();
        circularGauge.data(new SingleValueDataSet(new String[] {calProgress, "100"}));
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
        circularGauge.label(0d)
                .text("Calories Goal, <span style=\"\">" + calProgress + "%</span>")
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

        Bar bar100 = circularGauge.bar(1d);
        bar100.dataIndex(1d);
        bar100.radius(100d);
        bar100.width(50d);
        bar100.fill(new SolidFill("#F0F0F0", 10d));
        bar100.stroke("1 #e5e4e4");
        bar100.zIndex(4d);

        circularGauge.margin(50d, 50d, 50d, 50d);

        circularGauge.title().enabled(false);
        circularGauge.title().hAlign(HAlign.CENTER);
        circularGauge.title()
                .padding(0d, 0d, 0d, 0d)
                .margin(0d, 0d, 20d, 0d);

        anyChartGagueView.setChart(circularGauge);
    }
    public void createBarChart(View root, int[] weekCal) {
        AnyChartView anyChartBarView = root.findViewById(R.id.anychart_bar_graph);
        APIlib.getInstance().setActiveAnyChartView(anyChartBarView);
        anyChartBarView.setProgressBar(getView().findViewById(R.id.anychart_bar_progress_bar));


        Cartesian cartesian = AnyChart.column();

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Day 0", weekCal[0]));
        data.add(new ValueDataEntry("Day -1", weekCal[1]));
        data.add(new ValueDataEntry("Day -2", weekCal[2]));
        data.add(new ValueDataEntry("Day -3", weekCal[3]));
        data.add(new ValueDataEntry("Day -4", weekCal[4]));
        data.add(new ValueDataEntry("Day -5", weekCal[5]));
        data.add(new ValueDataEntry("Day -6", weekCal[6]));

        Column column = cartesian.column(data);

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("${%Value}{groupsSeparator: }");

        cartesian.animation(true);
        cartesian.title("Calories per day for the last week");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("${%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("Days");
        cartesian.yAxis(0).title("Calories");

        anyChartBarView.setChart(cartesian);


    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}