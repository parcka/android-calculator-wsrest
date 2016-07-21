package com.example.xtr100.app1_diplomado_calc;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xtr100.app1_diplomado_calc.entities.Operation;
import com.example.xtr100.app1_diplomado_calc.tools.AsyncFunctionResult;
import com.example.xtr100.app1_diplomado_calc.tools.AsyncOperation;
import com.example.xtr100.app1_diplomado_calc.tools.ClientWebService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity implements AsyncFunctionResult {


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
    Switch retroFitSwitch;

    Button buttonDivide;
    Button buttonSum;
    Button buttonMulty;
    Button buttonSub;

    Operation operation;

    String operatorRegex;
    String operatortoShow;
    String displayTemp;
    static final String regexOperations = "\\+-x/";

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
        retroFitSwitch = (Switch) findViewById(R.id.retroFitSwitch);


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
        operatortoShow = button.getText().toString();
        operatorRegex = "\\" + operatortoShow;

        if (textInDisplay()) {
            Log.d("TEXT IN DISPLAY ", display.getText() + operatorRegex);
            if (!isLastItemOperator() && !operatorInDisplay()) {
                inDisplay += operatortoShow;
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

    private boolean operatorInDisplay() {
        String inDisplay = display.getText().toString();
//        String[] elements = inDisplay.split(Pattern.quote(regexOperations));
        String[] elements = inDisplay.split(operatorRegex);
        if (elements.length >= 2) {
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

    private void setOperationEntity() {
        String inDisplay = display.getText().toString();
//        String[] elements = inDisplay.split(Pattern.quote(regexOperations));
        String[] elements = inDisplay.split(operatorRegex);
        operation.setOperatorSimbol(operatortoShow);
        for (String number : elements)
            if (operation.getNumberA() == null)
                operation.setNumberA(Integer.valueOf(number));
            else if (operation.getNumberB() == null)
                operation.setNumberB(Integer.valueOf(number));

    }


    public void onClickDeleteDisplay(View view) {
        this.operation = new Operation();
        display.setText("");
    }

    public void getResultWS(View view) {
        if (!isOperationFullSeted()) {
            setOperationEntity();
        }
        consultWs();
    }

    private void consultWs() {
        if (retroFitSwitch.isChecked()) {
            //Codigo de consumo WS mediante RetroFit
            retroFitConsult();
        } else {
            AsyncOperation asyncOperation = new AsyncOperation(getBaseContext(), AsyncOperation.CONSULT_URL, this, operation);
            asyncOperation.execute();
        }
    }

    @Override
    public void postProcess(String result) {
//        this.operation = operationResult;
        display.setText(result);
    }

    private void retroFitConsult() {
        JSONObject jsonObject = null;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.3.2:8080/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ClientWebService service = retrofit.create(ClientWebService.class);

        try {
            jsonObject = new JSONObject(operation.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Call<Operation> operationCall = service.getOperation(operation);

        operationCall.enqueue(new Callback<Operation>() {
                                  @Override
                                  public void onResponse(Call<Operation> call, Response<Operation> response) {
                                      Toast.makeText(getBaseContext(), "1", Toast.LENGTH_LONG).show();
                                      if (response.isSuccessful()) {
                                          Gson gson = new Gson();
                                          Operation operation =   gson.fromJson(response.body().toString(),Operation.class);
                                          display.setText(operation.getResult().toString());
                                      } else {
                                          int statusCode = response.code();
                                          Toast.makeText(getBaseContext(), "Mal", Toast.LENGTH_LONG).show();


                                      }
                                  }

                                  @Override
                                  public void onFailure(Call<Operation> call, Throwable t) {
                                      Toast.makeText(getBaseContext(), "2", Toast.LENGTH_LONG).show();
                                  }
                              }

        );

    }
}
