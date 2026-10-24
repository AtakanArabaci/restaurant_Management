package view_components;

import javax.swing.text.*;

public class NumericLimitFilter extends DocumentFilter {

    private final int maxLength;

    public NumericLimitFilter(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
            throws BadLocationException {

        if (string == null) return;
        if (!string.matches("\\d+")) return;
        if (fb.getDocument().getLength() + string.length() > maxLength) return;
 
        super.insertString(fb, offset, string, attr);
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
            throws BadLocationException {

        if (text == null) return;
        if (!text.matches("\\d+")) return;
        if (fb.getDocument().getLength() - length + text.length() > maxLength) return;

        super.replace(fb, offset, length, text, attrs);
    }
}
