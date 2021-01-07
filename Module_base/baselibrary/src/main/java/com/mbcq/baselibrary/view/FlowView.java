package com.mbcq.baselibrary.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.mbcq.baselibrary.R;

import java.util.ArrayList;
import java.util.List;

public class FlowView  extends ViewGroup {
    protected List<Rect> viewRects;//子view布局
    protected int allChildWidth = 0, allChildHeight = 0;//当前布局包括padding的最大宽高
    int maxLine;//最大行数
    boolean noRightMargin;//每行最右边子view是否需要右边距

    public FlowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        @SuppressLint("Recycle")
        TypedArray ary = this.getContext().obtainStyledAttributes(attrs, R.styleable.FlowView);
        maxLine = ary.getInt(R.styleable.FlowView_maxLine, 0);
        noRightMargin = ary.getBoolean(R.styleable.FlowView_noRightMargin,false);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < viewRects.size(); i++) {
            View child = getChildAt(i);
            if (child == null) {
                return;
            }
            Rect rect = viewRects.get(i);
            child.layout(rect.left, rect.top, rect.right, rect.bottom);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        viewRects = new ArrayList<>();
        /*测量子view尺寸*/
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int measureWidth, measureHeight;

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //预布局
        compute(widthSize);
        //给定大小和match_parent
        if (widthMode == MeasureSpec.EXACTLY) {
            measureWidth = widthSize;
            //未规定大小
        } else {
            measureWidth = allChildWidth;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            measureHeight = heightSize;
        } else {
            measureHeight = allChildHeight;
        }
        //设置布局宽高
        setMeasuredDimension(measureWidth, measureHeight);
    }

    private void compute(int widthSize) {
        boolean aRow = false;//是否换行
        int line = 1;
        int paddingLeft = getPaddingLeft();
        int maxWidth = widthSize - getPaddingRight();//最大宽度
        if (maxWidth <= 0) {
            return;
        }
        int nextRowWidth = paddingLeft;//预计下一步起点已经占宽度

        int nextColumnTopHeight = getPaddingTop();//预计下一步行顶部所占高度
        int upRowHeight = 0;//上一步最大行高

        MarginLayoutParams margin;
        int left,top,right,bottom;

        for (int i = 0; i < getChildCount(); i++) {

            View child = getChildAt(i);
            int meausredWidth = child.getMeasuredWidth();//view测量宽度
            int measuredHeight = child.getMeasuredHeight();//view测量高度

            margin = (MarginLayoutParams) child.getLayoutParams();//view边距,必须重写generateDefaultLayoutParams不然会报错
            int childMarginLeft = margin.leftMargin;
            int childMarginRight = margin.rightMargin;
            int childMarginTop = margin.topMargin;
            int childMarginBottom = margin.bottomMargin;
            int childWidth = childMarginLeft + meausredWidth + childMarginRight;//view实际宽度
            int childHeight = childMarginTop + measuredHeight + childMarginBottom;//view实际高度

            //预计本次可能需要布局在下一行
            if (nextRowWidth + childWidth > maxWidth) {
                //布局在本行
                if (noRightMargin && (nextRowWidth + childWidth - childMarginRight <= maxWidth)) {
                    left = nextRowWidth + childMarginLeft;
                    top = nextColumnTopHeight + childMarginTop;
                    right = nextRowWidth + childWidth - childMarginRight;
                    bottom = nextColumnTopHeight + childHeight - childMarginBottom;
                    viewRects.add(new Rect(left, top, right, bottom));

                    //重置数据
                    int s = Math.max(childHeight, upRowHeight);//比较高度
                    nextRowWidth = paddingLeft;
                    nextColumnTopHeight += s;
                    allChildHeight = nextColumnTopHeight + getPaddingBottom();
                    upRowHeight = 0;

                    aRow = true;
                    line++;
                    if (line == maxLine) {
                        break;
                    }
                } else if (!noRightMargin || (noRightMargin && (nextRowWidth + childWidth - childMarginRight > maxWidth))) {
                    //布局在下一行
                    if (line == maxLine) {
                        break;
                    }
                    left = paddingLeft + childMarginLeft;
                    top = nextColumnTopHeight + upRowHeight + childMarginTop;
                    right = paddingLeft + childWidth - childMarginRight;
                    bottom = nextColumnTopHeight + upRowHeight + childHeight - childMarginBottom;
                    viewRects.add(new Rect(left, top, right, bottom));

                    //重置数据
                    nextColumnTopHeight+= upRowHeight;
                    nextRowWidth = right + childMarginRight;
                    upRowHeight = childHeight;
                    aRow = true;
                    line++;
                    allChildHeight = nextColumnTopHeight + upRowHeight + getPaddingBottom();
                }

            } else {
                //在当前行
                left = nextRowWidth + childMarginLeft;
                top = nextColumnTopHeight + childMarginTop;
                right = nextRowWidth + childWidth - childMarginRight;
                bottom = nextColumnTopHeight + childHeight - childMarginBottom;
                viewRects.add(new Rect(left, top, right, bottom));

                //重置数据
                nextRowWidth += childWidth;
                upRowHeight = Math.max(childHeight, upRowHeight);
                allChildHeight = nextColumnTopHeight + upRowHeight + getPaddingBottom();
            }


        }

        if (aRow) {
            allChildWidth = widthSize;
        } else {
            allChildWidth = nextRowWidth;
        }
    }

    //inflater 的时候调用
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    //addView的时候
    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    //child没有设置LayoutParams的时候调用
    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    //addView的时候
    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof MarginLayoutParams;
    }

}
