package view_components;

import java.awt.*;
import javax.swing.*;

public class WrapLayout extends FlowLayout {

    public WrapLayout(int align, int hgap, int vgap) {
        super(align, hgap, vgap);
    }

    @Override
    public Dimension preferredLayoutSize(Container target) {
        return layoutSize(target, true);
    }

    private Dimension layoutSize(Container target, boolean preferred) {
        synchronized (target.getTreeLock()) {
            int width = target.getWidth();
            if (width == 0) width = Integer.MAX_VALUE;

            int hgap = getHgap();
            int vgap = getVgap();
            Insets insets = target.getInsets();

            int maxWidth = width - (insets.left + insets.right + hgap * 2);
            int x = 0;
            int y = insets.top + vgap;
            int rowHeight = 0;

            for (Component comp : target.getComponents()) {
                if (!comp.isVisible()) continue;

                Dimension d = preferred ? comp.getPreferredSize() : comp.getMinimumSize();

                if (x + d.width > maxWidth) {
                    x = 0;
                    y += rowHeight + vgap;
                    rowHeight = 0;
                }

                x += d.width + hgap;
                rowHeight = Math.max(rowHeight, d.height);
            }

            y += rowHeight + insets.bottom;
            return new Dimension(width, y);
        }
    }
}
