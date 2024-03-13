package com.example.cs2340a_team19.ui.meals;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.SingleValueDataSet;
import com.anychart.charts.CircularGauge;
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
        super.onCreate(savedInstanceState);
        MealsViewModel mealsViewModel =
                new ViewModelProvider(this).get(MealsViewModel.class);

        binding = FragmentMealsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //createAnyChart(root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @NonNull Bundle savedInstanceState) {
        createPieChart(view);
        createAnyChart(view);
    }

    private void createAnyChart(View root){
        AnyChartView anyChartView = root.findViewById(R.id.anychart_viz1_temp);
        anyChartView.setProgressBar(getView().findViewById(R.id.anychart_progress_bar));

        CircularGauge circularGauge = AnyChart.circular();
        circularGauge.data(new SingleValueDataSet(new String[] { "23", "34", "67", "93", "56", "100"}));
        circularGauge.fill("#fff")
                .stroke(null)
                .padding(0d, 0d, 0d, 0d)
                .margin(100d, 100d, 100d, 100d);
        circularGauge.startAngle(0d);
        circularGauge.sweepAngle(360d);

        Circular xAxis = circularGauge.axis(0)
                .radius(100d)
                .width(4d)
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
                .text("Calories, <span style=\"\">32%</span>")
                .useHtml(true)
                .hAlign(HAlign.CENTER)
                .vAlign(VAlign.MIDDLE);
        circularGauge.label(0d)
                .anchor(Anchor.CENTER)
                .padding(0d, 10d, 0d, 0d)
                .offsetX(0d);
        Bar bar0 = circularGauge.bar(0d);
        bar0.dataIndex(0d);
        bar0.radius(150d);
        bar0.width(17d);

        bar0.fill(new SolidFill("#64b5f6", 1d));
        bar0.stroke(null);
        bar0.zIndex(5d);
        Bar bar100 = circularGauge.bar(100d);
        bar100.dataIndex(5d);
        bar100.radius(150d);
        bar100.width(17d);
        bar100.fill(new SolidFill("#F5F4F4", 1d));
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

        AnyChartView anyChartView = root.findViewById(R.id.anychart_pie_viz);
        anyChartView.setProgressBar(root.findViewById(R.id.anychart_pie_progress_bar));

        Pie pie = AnyChart.pie();


        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Your Calorie Intake", 500));
        data.add(new ValueDataEntry("What you need to reach your Goal",100));


        pie.data(data);

        pie.title("Calorie Intake vs Calories Left Until Goal Reached");

        pie.labels().position("outside");

        pie.legend().title().enabled(true);
        pie.legend().title()
                .text("Retail channels")
                .padding(0d, 0d, 10d, 0d);

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        anyChartView.setChart(pie);

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}