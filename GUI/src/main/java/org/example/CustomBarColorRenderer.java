package org.example;

import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;

public class CustomBarColorRenderer extends BarRenderer {
    private final DefaultCategoryDataset dataset;

    public CustomBarColorRenderer(DefaultCategoryDataset dataset){
        this.dataset = dataset;
    }

    @Override
    public Paint getItemPaint(int row, int column){
        var value = dataset.getValue(row, column);

        if(value.equals(5.0))
            return new Color(0,174,29);
        else if(value.equals(4.0))
            return new Color(0, 238, 40);
        else if(value.equals(3.0))
            return new Color(198, 238, 0);
        else if(value.equals(2.0))
            return new Color(238, 166, 0);
        else                                            //if(value.equals(1.0))
            return new Color(238, 71, 0);
    }
}
