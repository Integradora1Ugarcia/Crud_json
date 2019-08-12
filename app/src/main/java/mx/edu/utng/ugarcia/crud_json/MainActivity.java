package mx.edu.utng.ugarcia.crud_json;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //Defining views
    private EditText txt_name;
    private EditText txt_position;
    private EditText txt_salary;

    private Button btnAdd;
    private Button btnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing views
        txt_name = (EditText) findViewById(R.id.txt_name);
        txt_position = (EditText) findViewById(R.id.txt_position);
        txt_salary = (EditText) findViewById(R.id.txt_salary);

        btnAdd = (Button) findViewById(R.id.btn_add);
        btnView = (Button) findViewById(R.id.btn_view);

        //Setting listeners to button
        btnAdd.setOnClickListener(this);
        btnView.setOnClickListener(this);
    }


    //Adding an employee
    private void addEmployee(){

        final String nombre = txt_name.getText().toString().trim();
        final String puesto = txt_position.getText().toString().trim();
        final String salario = txt_salary.getText().toString().trim();

        class AddEmployee extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this,"Adding...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(MainActivity.this,s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Config.KEY_EMP_NAME,nombre);
                params.put(Config.KEY_EMP_POSITION,puesto);
                params.put(Config.KEY_EMP_SALARY,salario);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD, params);
                return res;
            }
        }

        AddEmployee ae = new AddEmployee();
        ae.execute();
    }

    @Override
    public void onClick(View v) {
        if(v == btnAdd){
            addEmployee();
        }

        if(v == btnView) startActivity(new Intent(this, ViewAllEmployeeActivity.class));
    }
}
