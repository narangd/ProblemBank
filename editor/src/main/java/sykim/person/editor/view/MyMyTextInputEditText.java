package sykim.person.editor.view;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MyMyTextInputEditText extends TextInputEditText {
    public MyMyTextInputEditText(Context context) {
        super(context);
    }

    public MyMyTextInputEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyMyTextInputEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private Rect parentRect = new Rect();

    @Override
    public void getFocusedRect(Rect r) {
        super.getFocusedRect(r);
        if (r != null) {
            View parent = getEditParent();
            if (parent != null) {
                parent.getFocusedRect(parentRect);
            }
            r.bottom = parentRect.bottom;
        }
    }

    private View getEditParent() {
        ViewParent parent = getParent();
        while (!(parent instanceof TextInputLayout) && parent != null) {
            parent = parent.getParent();
        }
        return parent == null ? this : (View) parent;
    }

    @Override
    public boolean getGlobalVisibleRect(Rect r, Point globalOffset) {
        boolean result = super.getGlobalVisibleRect(r, globalOffset);
        if (r != null) {
            View parent = getEditParent();
            if (parent != null) {
                parent.getGlobalVisibleRect(parentRect, globalOffset);
            }
            r.bottom = parentRect.bottom;
        }
        return result;
    }

    @Override
    public boolean requestRectangleOnScreen(Rect rectangle) {
        boolean result = super.requestRectangleOnScreen(rectangle);
        View parent = getEditParent();
        // 10 is a random magic number to define a rectangle height.
        parentRect.set(0, parent.getHeight()-10, parent.getRight(), parent.getHeight());
        parent.requestRectangleOnScreen(parentRect, true);
        return result;
    }
}
