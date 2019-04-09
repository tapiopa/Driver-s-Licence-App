package tapiopalonemi.fi.driversapp;

//import android.view.GestureDetector;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class DetectSwipeGestureListener extends GestureDetector.SimpleOnGestureListener {

    // Minimal x and y axis swipe distance.
    private static final int MIN_SWIPE_DISTANCE_X = 100;
    private static final int MIN_SWIPE_DISTANCE_Y = 100;

    // Maximal x and y axis swipe distance.
    private static final int MAX_SWIPE_DISTANCE_X = 1000;
    private static final int MAX_SWIPE_DISTANCE_Y = 1000;

    // Source activity that display message in text view.
    private AppCompatActivity activity = null;

    public AppCompatActivity getActivity() {
        return activity;
    }

    public void setActivity(AppCompatActivity activity) {

        this.activity = activity;
    }

    /* This method is invoked when a swipe gesture happened. */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        // Get swipe delta value in x axis.
        float deltaX = e1.getX() - e2.getX();

        // Get swipe delta value in y axis.
        float deltaY = e1.getY() - e2.getY();

        // Get absolute value.
        float deltaXAbs = Math.abs(deltaX);
        float deltaYAbs = Math.abs(deltaY);

        // Only when swipe distance between minimal and maximal distance value then we treat it as effective swipe
        if((deltaXAbs >= MIN_SWIPE_DISTANCE_X) && (deltaXAbs <= MAX_SWIPE_DISTANCE_X)) {
            if(deltaX > 0) {
                Log.i("SWIPE", "Left");
                if (activity.getClass().isAssignableFrom(ExamActivity.class)) {
                    ((ExamActivity) this.activity).previousQuestion(null);
                } else if (activity.getClass().isAssignableFrom(Informationactivity.class)) {
                    //TODO: Shyam add functionality
                }
            } else {
                Log.i("SWIPE", "Right");
                if (activity.getClass().isAssignableFrom(ExamActivity.class)) {
                    ((ExamActivity) this.activity).nextQuestion(null);
                } else if (activity.getClass().isAssignableFrom(Informationactivity.class)) {
                    //TODO: Shyam add functionality
                }
            }
        }

        if((deltaYAbs >= MIN_SWIPE_DISTANCE_Y) && (deltaYAbs <= MAX_SWIPE_DISTANCE_Y)) {
            if(deltaY > 0) {
                Log.i("SWIPE", "Up");
                if (activity.getClass().isAssignableFrom(ExamActivity.class)) {
                    ((ExamActivity) this.activity).previousQuestion(null);
                } else if (activity.getClass().isAssignableFrom(Informationactivity.class)) {
                    //TODO: Shyam add functionality
                }
            } else {
                Log.i("SWIPE", "Down");
                if (activity.getClass().isAssignableFrom(ExamActivity.class)) {
                    ((ExamActivity) this.activity).nextQuestion(null);
                } else if (activity.getClass().isAssignableFrom(Informationactivity.class)) {
                    //TODO: Shyam add functionality
                }
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