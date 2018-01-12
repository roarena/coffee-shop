package eu.rodrigocamara.myladybucks.listeners;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ImageView;

import eu.rodrigocamara.myladybucks.interfaces.QuantityHandler;

/**
 * Created by Rodrigo Câmara on 11/01/2018.
 */

public class TextChangeListeners {

    public static TextWatcher coffeeQuantityListener(final ImageView ivRemoveCoffee) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (Integer.valueOf(charSequence.toString()) > 1) {
                    ivRemoveCoffee.setEnabled(true);
                    ivRemoveCoffee.setColorFilter(null);
                } else {
                    ivRemoveCoffee.setEnabled(false);
                    ivRemoveCoffee.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }

    public static TextWatcher coffeeQuantityListener(final QuantityHandler quantityHandler) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                quantityHandler.handleUiChanges();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }
}
