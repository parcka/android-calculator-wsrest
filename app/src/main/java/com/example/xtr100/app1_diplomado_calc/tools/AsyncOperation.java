package com.example.xtr100.app1_diplomado_calc.tools;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.xtr100.app1_diplomado_calc.entities.Operation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by XTR100 on 19/07/2016.
 */
public class AsyncOperation extends AsyncTask<String, String, String> {

    Context context;
    String url;
    AsyncFunctionResult asyncFunctionResult;
    Operation operation;
    public static String CONSULT_URL = "http://10.0.3.2:8080/rest/sum";

    public AsyncOperation() {
    }

    public AsyncOperation(Context context, String url, AsyncFunctionResult asyncFunctionResult,Operation operation) {
        this.context = context;
        this.url = url;
        this.asyncFunctionResult = asyncFunctionResult;
        this.operation = operation;
    }


    @Override
    protected String doInBackground(String[] params) {

        HttpURLConnection urlConnection = null;
        StringBuilder response = new StringBuilder();
        DataOutputStream dataOutputStream = null;

        try {
            URL url = new URL(this.url);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");


            /*Operation operation = new Operation();
            operation.setNumberA(100);
            operation.setNumberB(200);
            operation.setOperatorSimbol("+");*/

            JSONObject jsonObject = new JSONObject(operation.toString());
            /*jsonObject.put("numberA", operation.getNumberA());
            jsonObject.put("numberB", operation.getNumberB());
            jsonObject.put("operatorSimbol", operation.getOperatorSimbol());
            jsonObject.put("result", operation.getResult());
*/
//            OutputStream os = urlConnection.getOutputStream();
            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write(jsonObject.toString());// here i sent the parameter
            out.close();
/*

            // Send POST output.
            dataOutputStream = new DataOutputStream(urlConnection.getOutputStream());
            dataOutputStream.writeBytes(URLEncoder.encode(jsonObject.toString(), "UTF-8"));
            dataOutputStream.flush();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonObject.toString());
            writer.flush();
*/

            int httpResult = urlConnection.getResponseCode();

            if (httpResult == HttpURLConnection.HTTP_OK) {


                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }


                reader.close();

            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        publishProgress(response.toString());

        return response.toString();


    }

    @Override
    protected void onProgressUpdate(String... values) {
        for (String obj : values) {
            Toast.makeText(context, obj, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onPostExecute(String response) {
//        super.onPostExecute(response);
        System.out.println("onpost");
        Toast.makeText(context, "post Execeute", Toast.LENGTH_LONG).show();
        try {
            JSONObject jsonObject = new JSONObject(response);
           /* Operation user = jsonObject.get("result");
            String pass = jsonObject.get("numberB").toString();
            String level = jsonObject.get("operatorSimbol").toString();*/
            String result = jsonObject.get("result").toString();
            Toast.makeText(context, "Respuesta: " + result, Toast.LENGTH_LONG).show();
            asyncFunctionResult.postProcess(result);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Error Parsing JSON", e.toString());
        }
    }
}
