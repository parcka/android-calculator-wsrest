package com.example.xtr100.app1_diplomado_calc;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.xtr100.app1_diplomado_calc.entities.Operation;

import java.util.regex.Pattern;

public class MainActivity extends Activity {

    Button buttonOne;
    Button buttonTwo;
    Button buttonThree;
    Button buttonFour;
    Button buttonFive;
    Button buttonSix;
    Button buttonSeven;
    Button buttonEigth;
    Button buttonNine;
    TextView display;

    Button buttonDivide;
    Button buttonSum;
    Button buttonMulty;
    Button buttonSub;

    Operation operation;

    String displayTemp;
   static final String regexOperations = "+-x/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonOne = (Button) findViewById(R.id.buttonOne);
        buttonTwo = (Button) findViewById(R.id.buttonTwo);
        buttonThree = (Button) findViewById(R.id.buttonThree);
        buttonFour = (Button) findViewById(R.id.buttonFour);
        buttonFive = (Button) findViewById(R.id.buttonFive);
        buttonSix = (Button) findViewById(R.id.buttonSix);
        buttonSeven = (Button) findViewById(R.id.buttonSeven);
        buttonEigth = (Button) findViewById(R.id.buttonEigth);
        buttonNine = (Button) findViewById(R.id.buttonNine);
        display = (TextView) findViewById(R.id.textView);

        //Operation view
        buttonDivide = (Button) findViewById(R.id.buttonDivide);
        buttonSum = (Button) findViewById(R.id.buttonSum);
        buttonMulty = (Button) findViewById(R.id.buttonMulty);
        buttonSub = (Button) findViewById(R.id.buttonSub);

        operation = new Operation();
        displayTemp = "";


    }

    public void onClickNumber(View view) {
        Button button = (Button) view;
        String inDisplay = display.getText().toString();
        display.setText(String.format("%s", inDisplay + button.getText()));

    }

    public void onClickResult(View view) {
        //Enviar al ws-rest


    }

    public boolean textInDisplay() {
        CharSequence charSequence = display.getText();
        if (charSequence.length() > 0) {
            return true;
        }
        return false;
    }


    public void onClickOperationSimbol(View view) {
        Button button = (Button) view;
        String inDisplay = display.getText().toString();

        if (textInDisplay()) {
            Log.d("TEXT IN DISPLAY ", display.getText() + button.getText().toString());
            if (!isLastItemOperator() && !operatorInOperation()) {
                inDisplay += button.getText().toString();
                display.setText(inDisplay);
            }
        } else {
            Log.d("NO TEXT IN DISPLAY ", display.getText() + button.getText().toString());
        }


       /* if (operation.getNumberA() != null) {
            String messageToShow = "";
            Button button = (Button) view;
            String op = button.getText().toString();

            switch (op) {
                case "x":
                    op = "*";
            }

            operation.setOperatorSimbol(op);
            messageToShow = (operation.getNumberA() == null ? "" : operation.getNumberA().toString()) +
                    operation.getOperatorSimbol() +
                    (operation.getNumberB() == null ? "" : operation.getNumberB().toString());
            display.setText(messageToShow);


            Log.d("Debug", "Simbolo set: " + op);
            Log.d("Valor de operation: ", operation.toString());

        }*/
    }

    private boolean operatorInOperation(){
        String inDisplay = display.getText().toString();
           String[] elements = inDisplay.split(Pattern.quote(regexOperations));
        if (elements.length>2){
            return true;
        }

        return false;
    }
    private boolean isOperator(String operator) {
        switch (operator) {
            case "+":
            case "-":
            case "/":
            case "x":
                return true;
        }
        return false;
    }

    private boolean isLastItemOperator() {
        String textInDisplay = display.getText().toString();
        if (isOperator(
                String.valueOf(
                        textInDisplay.charAt(display.length() - 1)))) {
            return true;

        }
        return false;
    }

    private boolean isOperationFullSeted() {

        if (operation.getNumberA() != null &&
                operation.getNumberB() != null &&
                operation.getOperatorSimbol() != null) {
            return true;
        }

        return false;
    }

    public void onClickDeleteDisplay(View view) {
        display.setText("");
    }


}
