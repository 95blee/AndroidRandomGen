package ben_lee.random;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.security.SecureRandom;
import java.util.Timer;
import java.util.TimerTask;

public class CoinFlipFragment extends Fragment {
    private static final int SLEEP_COUNT = 50;

    private String currResult = "H";
    private final SecureRandom random = new SecureRandom();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.coin_flip_fragment, container, false);
        Button generateNew = (Button) view.findViewById(R.id.coin_flip_button);
        generateNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flipCoin();
            }
        });
        return view;
    }

    private void flipCoin() {
        Thread newThread = new Thread(new Runnable() {
            @Override
            public void run() {
                View view = getView();
                final TextView result = (TextView) view.findViewById(R.id.coin_flip_result);
                Activity main = getActivity();
                int change = random.nextBoolean() ? 1 : 0;
                for (int i=0; i<25 + change; i++) {
                    main.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (currResult.equals("H")) {
                                result.setText("T");
                                currResult = "T";
                            } else {
                                result.setText("H");
                                currResult = "H";
                            }
                        }
                    });
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                    }
                }
            }
        });
        newThread.start();
    }
}
