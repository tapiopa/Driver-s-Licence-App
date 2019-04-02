package tapiopalonemi.fi.driversapp;

//import android.view.GestureDetector;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by Jerry on 4/18/2018.
 */

public class DetectSwipeGestureListener extends GestureDetector.SimpleOnGestureListener {

    // Minimal x and y axis swipe distance.
    private static int MIN_SWIPE_DISTANCE_X = 100;
    private static int MIN_SWIPE_DISTANCE_Y = 100;

    // Maximal x and y axis swipe distance.
    private static int MAX_SWIPE_DISTANCE_X = 1000;
    private static int MAX_SWIPE_DISTANCE_Y = 1000;

    // Source activity that display message in text view.
    private ExamActivity activity = null;

    public ExamActivity getActivity() {
        return activity;
    }

    public void setActivity(ExamActivity activity) {

        this.activity = activity;
    }

    /* This method is invoked when a swipe gesture happened. */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        // Get swipe delta value in x axis.
        float deltaX = e1.getX() - e2.getX();

        // Get swipe delta value in y axis.
        float deltaY = e1.getY() - e2.getY();
//        Log.i("SWIPE", "deltaX: " + deltaX);
//        Log.i("SWIFT", "X1: " + e1.getX() + ", X2: " + e2.getX());
//        Log.i("SWIFT", "rawX1: " + e1.getRawX() + ", rawX2: " + e2.getRawX());
//        Log.i("SWIPE", "deltaY: " + deltaY);
//        Log.i("SWIFT", "Y1: " + e1.getY() + ", Y2: " + e2.getY());
//        Log.i("SWIFT", "rawY1: " + e1.getRawY() + ", rawY2: " + e2.getRawY());
//        Log.i("SWIFT", "velocityX: " + velocityX + ", velocityY: " + velocityY);

        // Get absolute value.
        float deltaXAbs = Math.abs(deltaX);
        float deltaYAbs = Math.abs(deltaY);

        // Only when swipe distance between minimal and maximal distance value then we treat it as effective swipe
        if((deltaXAbs >= MIN_SWIPE_DISTANCE_X) && (deltaXAbs <= MAX_SWIPE_DISTANCE_X))
        {
            if(deltaX > 0)
            {
                Log.i("SWIPE", "Left");
                this.activity.previousQuestion(null);
            }else
            {
                Log.i("SWIPE", "Right");
                this.activity.nextQuestion(null);
            }
        }

        if((deltaYAbs >= MIN_SWIPE_DISTANCE_Y) && (deltaYAbs <= MAX_SWIPE_DISTANCE_Y))
        {
            if(deltaY > 0)
            {
                Log.i("SWIPE", "Up");
                this.activity.previousQuestion(null);
            }else
            {
                Log.i("SWIPE", "Down");
                this.activity.nextQuestion(null);
            }
        }


        return true;
    }

    // Invoked when single tap screen.
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Log.i("SWIPE", "Single Tap");
//        this.activity.nextQuestion(null);
        return true;
    }

    // Invoked when double tap screen.
    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.i("SWIPE", "Double Tap");
//        this.activity.previousQuestion(null);
        return true;
    }
}