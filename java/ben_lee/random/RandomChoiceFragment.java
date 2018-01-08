package ben_lee.random;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class RandomChoiceFragment extends Fragment {
    private static final String NOT_ENOUGH_CHOICES = "Please enter at least one choice";

    private SecureRandom random = new SecureRandom();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.make_choice_fragment, container, false);
        Button generateNew = (Button) view.findViewById(R.id.make_choice_button);
        generateNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do stuff
                TextView errorMessage = (TextView) getView().findViewById(R.id.make_choice_error_message);
                errorMessage.setText("");
                makeChoice();
            }
        });
        return view;
    }

    private void makeChoice() {
        View view = getView();
        List<EditText> choiceInputBoxes = new ArrayList<>(5);
        List<String> choices = new ArrayList<>(5);
        choiceInputBoxes.add((EditText) view.findViewById(R.id.choice_one));
        choiceInputBoxes.add((EditText) view.findViewById(R.id.choice_two));
        choiceInputBoxes.add((EditText) view.findViewById(R.id.choice_three));
        choiceInputBoxes.add((EditText) view.findViewById(R.id.choice_four));
        choiceInputBoxes.add((EditText) view.findViewById(R.id.choice_five));
        for (EditText inputBox : choiceInputBoxes) {
            String choice = inputBox.getText().toString();
            if (!choice.isEmpty()) {
                choices.add(choice);
            }
        }
        if (choices.size() == 0) {
            TextView errorMessage = (TextView) view.findViewById(R.id.make_choice_error_message);
            errorMessage.setText(NOT_ENOUGH_CHOICES);
        } else {
            int choicePos = random.nextInt(choices.size());
            TextView result = (TextView) view.findViewById(R.id.make_choice_result);
            result.setText(choices.get(choicePos));
        }
    }
}
