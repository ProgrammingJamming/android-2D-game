package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.myapplication.R;

import java.util.ArrayList;

/**
 * Created by asdfqwer on 7/22/2017.
 */

public class DrawScore {
    private Bitmap[] numbers;
    private ArrayList digitList = new ArrayList();
    private int width;
    private int height;
    private Rect destination = new Rect();
    private Paint paint;

    public DrawScore(int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        BitmapFactory bf = new BitmapFactory();
        Bitmap zero = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.n0, options);
        Bitmap one = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.n1, options);
        Bitmap two = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.n2, options);
        Bitmap three = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.n3, options);
        Bitmap four = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.n4, options);
        Bitmap five = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.n5, options);
        Bitmap six = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.n6, options);
        Bitmap seven = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.n7, options);
        Bitmap eight = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.n8, options);
        Bitmap nine = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.n9, options);

        numbers = new Bitmap[]{zero, one, two, three, four, five, six, seven, eight, nine};

        this.width = width;
        this.height = height;

        paint = new Paint();
    }

    public ArrayList retDigitList(int score){
        digitList.clear();

        int counter;
        counter = 1;

        while (true) {
            if (score == 0) {
                break;
            }

            if (Math.floor((double) score / (double) counter) > 0) {
                counter *= 10;
            }
            else {
                counter /= 10;
                break;
            }
        }
        while (true) {
            if (counter == 1) {
                int digit = (score - score%counter) / counter;
                digitList.add(digit);
                break;
            }
            else {
                int digit = (score - score%counter) / counter;
                digitList.add(digit);
                score -= counter*digit;
                counter /= 10;
            }
        }
        return digitList;
    }

    public void draw(Canvas canvas, int score, int boxSizeX, int boxSizeY, int x, int y, int gap) {
        int pos = x;
        ArrayList list = retDigitList(score);

        for (int i = list.size() - 1; i > -1; i--){
            destination.set(pos - boxSizeX, y - boxSizeY, pos + boxSizeX, y + boxSizeY);
            canvas.drawBitmap(numbers[(int)list.get(i)], null, destination, paint);
            pos -= gap;
        }
    }
}
