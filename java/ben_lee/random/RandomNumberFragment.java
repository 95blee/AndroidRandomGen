package ben_lee.random;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.security.SecureRandom;

public class RandomNumberFragment extends Fragment {
    private static final int DEFAULT_UPPER_BOUND = 1000;
    private static final int DEFAULT_LOWER_BOUND = 0;
    private static final int UPPER_BOUND_LIMIT = 9999;
    private static final int LOWER_BOUND_LIMIT = -9999;

    private SecureRandom random = new SecureRandom();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.random_number_fragment, container, false);
        Button generateNew = (Button) view.findViewById(R.id.generateNew);
        generateNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateNew(view);
            }
        });
        return view;
    }

    private void generateNew(View view) {
        TextView numberView = (TextView) getView().findViewById(R.id.numberView);
        numberView.setTextSize(60);
        numberView.setText(String.valueOf(generateInt()));
    }

    private int generateInt() {
        EditText lowerBound = (EditText) getView().findViewById(R.id.num_lower_bound);
        EditText upperBound = (EditText) getView().findViewById(R.id.num_upper_bound);
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
        return random.nextInt(difference) + lowBound;
    }
}
