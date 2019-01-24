package landicrop.imagedemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;

public class SunView extends View {

    private Bitmap bitmap;
    private Paint paint;
    private Context context;
    float w;
    float h;
    RectF rectF;
    public SunView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    private void initView(){
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
//        setBackgroundColor(Color.RED);





    }

    public void setBitmap( int drawableId) {
        this.bitmap = BitmapFactory.decodeResource(context.getResources(),
                drawableId);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        w = getMeasuredWidth();
        h = getMeasuredHeight();
        rectF = new RectF(0, 0, w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(bitmap == null)return;

        canvas.drawBitmap(bitmap,null,rectF,paint);
    }
}
