package ben_lee.random;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.security.SecureRandom;

public class RandomNumberFragment extends Fragment {
    private static final int DEFAULT_UPPER_BOUND = 1000;
    private static final int DEFAULT_LOWER_BOUND = 0;
    private static final int UPPER_BOUND_LIMIT = 9999;
    private static final int LOWER_BOUND_LIMIT = -9999;
    private static final String SAME_NUM_ERROR = "Entered numbers must be different";

    private SecureRandom random = new SecureRandom();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.random_number_fragment, container, false);
        Button generateNew = (Button) view.findViewById(R.id.generateNew);
        generateNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateNew();
            }
        });
        return view;
    }

    private void addEnterListener() {
        View view = getView();
        TextView.OnEditorActionListener listener = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if ((keyEvent != null) && (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE)) {
                    View keyboardCloseView = getView().getRootView();
                    textView.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(keyboardCloseView.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
                }
                return false;
            }
        };
        TextView bound = (TextView) view.findViewById(R.id.num_upper_bound);
        bound.setOnEditorActionListener(listener);
        bound = (TextView) view.findViewById(R.id.num_upper_bound);
        bound.setOnEditorActionListener(listener);
    }

    private void generateNew() {
        View view = getView();
        try {
            int newNum = generateInt();
        } catch (RuntimeException exception) {
            TextView error = (TextView) view.findViewById(R.id.rand_num_error_message);
            error.setText(SAME_NUM_ERROR);
            return;
        }
        TextView numberView = (TextView) view.findViewById(R.id.numberView);
        numberView.setTextSize(60);
        numberView.setText(String.valueOf(generateInt()));
        TextView error = (TextView) view.findViewById(R.id.rand_num_error_message);
        error.setText("");
    }

    private int generateInt() {
        View view = getView();
        EditText lowerBound = (EditText) view.findViewById(R.id.num_lower_bound);
        EditText upperBound = (EditText) view.findViewById(R.id.num_upper_bound);
        String lowBoundStr = lowerBound.getText().toString();
        String upBoundStr = upperBound.getText().toString();
        int lowBound = lowBoundStr.isEmpty() ? DEFAULT_LOWER_BOUND : Integer.parseInt(lowBoundStr);
        int upBound = upBoundStr.isEmpty() ? DEFAULT_UPPER_BOUND : Integer.parseInt(upBoundStr);
        if (lowBound > upBound) {
            int temp = lowBound;
            lowBound = upBound;
            upBound = temp;
        }
        if (upBound > UPPER_BOUND_LIMIT) {
            upBound = UPPER_BOUND_LIMIT;
            upperBound.setText(String.valueOf(upBound));
        }
        if (lowBound < LOWER_BOUND_LIMIT) {
            lowBound = LOWER_BOUND_LIMIT;
            lowerBound.setText(String.valueOf(lowBound));
        }
        int difference = upBound - lowBound;
        if (difference == 0) {
            throw new RuntimeException();
        }
        return random.nextInt(difference) + lowBound;
    }
}
