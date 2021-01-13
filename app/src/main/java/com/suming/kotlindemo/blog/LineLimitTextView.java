package com.suming.kotlindemo.blog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 日期:2018/8/30
 * <p>
 * 作者:
 * <p>
 * 描述:一个用于控制TextView显示行为而且超过指定行为时最后一行显示"......"的自定义TextView控件.
 */
@SuppressLint("AppCompatCustomView")
public class LineLimitTextView extends TextView {
    private static final String TAG = "LineLimitTextView";
    private CharSequence mText;
    //如果大于VALID_LINE_COUNT行的文本最后一行要显示6个点的省略号
    private static final String ELLIPSIS = "......";
    //文本有效显示行数,默认是3行,不括省略号那行.
    private int mValidLineCount = 3;
    //总行数要加1
    private int mTotalLineCount = mValidLineCount + 1;

    public LineLimitTextView(Context context) {
        super(context);
    }

    public LineLimitTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LineLimitTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Paint.FontMetrics fontMetrics = getPaint().getFontMetrics();
        float height = fontMetrics.descent - fontMetrics.ascent;
        Log.d(TAG, "setText:--> height: " + height);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //自已计算TextView的高度，高度是VALID_LINE_COUNT+1行文本的高度
        TextPaint paint = getPaint();
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float height = fontMetrics.descent - fontMetrics.ascent;
        Log.d(TAG, "onMeasure:--> height: " + height);
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), (int) (height * mTotalLineCount));
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        this.mText = text;
        //调用这代码会执行onDraw
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        List<String> listText = splitTextByLine();
        if (null == listText) {
            return;
        }

        TextPaint paint = getPaint();
        //设置画笔的颜色也就是设置给TextView的颜色
        paint.setColor(getCurrentTextColor());

        //计算行的高度
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        int lineHeight = (int) (fontMetrics.descent - fontMetrics.ascent);

        //是否需要画省略号，如果mLineStr.size()大于VALID_LINE_COUNT则为true
        boolean isNeedDrawableEllipsis = listText.size() > mValidLineCount;

        //有效文本行数必须在小于或等于mValueLineCount
        int drawLineCount = listText.size() <= mValidLineCount ? listText.size() : mValidLineCount;

        for (int i = 0; i < drawLineCount; i++) {
            //每一行的y坐标进行累加行的高度,x坐标总是0
            float y = lineHeight * (i + 1);
            canvas.drawText(listText.get(i), 0, y, getPaint());
        }

        //如果为true则需要画省略号
        if (isNeedDrawableEllipsis) {
            canvas.drawText(ELLIPSIS, 0, lineHeight * mTotalLineCount, paint);
        }

    }

    /**
     * 对文本进行切行，切行的原则是：1如果遇到换行算一行，如果一段字符串的宽度小于该TextView的宽度- 一个
     * 文字的宽度切一行。如 TextView宽度是100px "123456789abcdefg"这段字符串1到9的宽度等于90那么
     * 则把1到9切出来算一行。以此类推至到字符串遍历结束
     *
     * @return 返回切后的行的集合
     */
    private List<String> splitTextByLine() {
        String text = (String) mText;
        if (TextUtils.isEmpty(text)) {
            return null;
        }

        //获取TextView的宽
        int TextViewWidth = getMeasuredWidth();
        Paint paint = getPaint();
        List<String> lists = new ArrayList<>();
        Log.d(TAG, "splitTextByLine:--> text: " + text);
        //测量一个单个文字的宽度
        int singleTextWidth = (int) paint.measureText("测");
        Log.d(TAG, "splitTextByLine:--> singleTextWidth: " + singleTextWidth);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {

            //如果回车算一行
            char ch = text.charAt(i);
            float textWidth = paint.measureText(sb.toString());
            //如果添加的长度与TextView一样长那么又算一行
            //如果回车算一行
            if ((ch == '\n') || textWidth >= (TextViewWidth - singleTextWidth)) {
                String string = sb.toString();
                if (!TextUtils.isEmpty(string)) {
                    lists.add(sb.toString());
                }
                sb = new StringBuilder();
                if (ch != '\n') {
                    sb.append(ch);
                }
                continue;
            }

            sb.append(ch);
            Log.d(TAG, "splitTextByLine:--> textWidth: " + textWidth);
        }

        //退出循环后还要再添加一次
        String string = sb.toString();
        if (!TextUtils.isEmpty(string)) {
            lists.add(string);
        }

        for (int i = 0; i < lists.size(); i++) {
            Log.d(TAG, "splitTextByLine:--> list: " + lists.get(i));
        }

        return lists;
    }
}
