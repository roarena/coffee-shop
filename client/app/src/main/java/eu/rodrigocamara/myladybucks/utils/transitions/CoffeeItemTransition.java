package eu.rodrigocamara.myladybucks.utils.transitions;

import android.support.transition.ChangeBounds;
import android.support.transition.ChangeImageTransform;
import android.support.transition.ChangeTransform;
import android.support.transition.TransitionSet;

/**
 * Created by Rodrigo Câmara on 15/01/2018.
 */

public class CoffeeItemTransition extends TransitionSet {
    public CoffeeItemTransition() {
        setOrdering(ORDERING_TOGETHER);
        addTransition(new ChangeBounds()).
                addTransition(new ChangeTransform()).
                addTransition(new ChangeImageTransform());
    }
}
