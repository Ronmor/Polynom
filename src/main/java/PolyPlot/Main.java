package PolyPlot;

import myMath.Polynom;
import myMath.Polynom_able;
import org.knowm.xchart.*;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Main {

    private static final double MIN_VALUE = -2;
    private static final double MAX_VALUE = 6;
    private static final double EPSILON = 0.01;

    public static void main(String... args) {

        Polynom polynom = new Polynom("0.2X^4-1.5X^3+3X^2-X-5");
        Polynom x = new Polynom();
        int numOfValues = (int) ((MAX_VALUE - MIN_VALUE) / EPSILON);
        double[] fxData = new double[numOfValues];
        double[] fyData = new double[numOfValues];
        double[] xData = new double[numOfValues];
        double[] yData = new double[numOfValues];
        Polynom_able derivative = polynom.derivative();
        List<Double> extremePointsXValues = new LinkedList<>();

        for (int i = 0; i < numOfValues; i++) {
            fxData[i] = MIN_VALUE + i * EPSILON;
            fyData[i] = polynom.f(fxData[i]);
            // if der.f(xData[i]) closer to 0 than previous value - replace, else add
            if (extremePointsXValues.isEmpty()) {
                extremePointsXValues.add(fxData[i]);
            } else if (Math.abs(derivative.f(fxData[i])) < Math.abs(derivative.f(extremePointsXValues.get(extremePointsXValues.size() - 1)))) {
                extremePointsXValues.set(extremePointsXValues.size() - 1, fxData[i]);
            } else {
                extremePointsXValues.add(fxData[i]);
            }
        }
        // X , Y cordinate system.
        for (int i = 0; i < numOfValues ; i++){
            xData[i] = MIN_VALUE + i * EPSILON;
            yData[i] = polynom.f(xData[i]);
        }

        List<Double> actualExtremePointsXValues = new LinkedList<>();
        actualExtremePointsXValues.add(extremePointsXValues.get(0));
        for (int i = 0; i < extremePointsXValues.size() - 1; i++) {
            if (Math.abs(extremePointsXValues.get(i) - extremePointsXValues.get(i + 1)) > 2 * EPSILON) {
                actualExtremePointsXValues.add(extremePointsXValues.get(i + 1));
            }
        }

        XYChart chart = new XYChartBuilder().title(polynom.toString()).xAxisTitle("X").yAxisTitle("Y").build();
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        chart.getStyler().setLegendVisible(false);
        chart.addSeries("x", xData, yData).setMarkerColor(Color.green);
        chart.addSeries(" ", fxData, fyData).setMarkerColor(Color.BLUE);

        actualExtremePointsXValues.forEach(fx -> {
            XYSeries series = chart.addSeries(String.valueOf(fx),
                    new double[]{fx},
                    new double[]{polynom.f(fx)});
            series.setMarkerColor(Color.RED).setMarker(SeriesMarkers.CIRCLE);

        });
        // find extreme points



// Show it
        new SwingWrapper(chart).displayChart();
    }
}



