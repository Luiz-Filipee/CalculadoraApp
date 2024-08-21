package br.luizfilipe.calculadoraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import br.luizfilipe.calculadoraapp.databinding.ActivityMainBinding;

public class Main extends Activity {

    private Double primeiroValor = Double.NaN;
    private String operador = "";
    private Double segundoValor;
    private final int[] botoesNumerosCalculadoras = {
            R.id.calculadora_number_0, R.id.calculadora_number_1,R.id.calculadora_number_2,
            R.id.calculadora_number_3,R.id.calculadora_number_4,R.id.calculadora_number_5,
            R.id.calculadora_number_6,R.id.calculadora_number_7,R.id.calculadora_number_8,
            R.id.calculadora_number_9
    };
    private final int[] operadores = {
            R.id.calculadora_number_menos, R.id.calculadora_number_mais,R.id.calculadora_number_multiplicacao,
            R.id.calculadora_number_rotate_tela
    };
    private EditText campoNumero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        br.luizfilipe.calculadoraapp.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        campoNumero = findViewById(R.id.calculadora_value);
        setBotoesNumerosEOperadores();
        RealizaOperacoes();
    }

    public void setBotoesNumerosEOperadores() {
        percorreNumeros();
        percorreOpereadores();
        limpaCampoNumero();
    }

    private void percorreNumeros() {
        for(int id : botoesNumerosCalculadoras){
            Button button = findViewById(id);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button b = (Button) v;
                    campoNumero.append(b.getText().toString());
                }
            });
        }
    }

    private void percorreOpereadores() {
        for (int id : operadores){
            Button button = findViewById(id);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button b = (Button) v;
                    operador = b.getText().toString();
                    primeiroValor = Double.parseDouble(campoNumero.getText().toString());
                    campoNumero.setText(" ");
                }
            });
        }
    }

    private void limpaCampoNumero() {
        Button botaoLimpaEditText = findViewById(R.id.button_apagar);
        botaoLimpaEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                campoNumero.setText(" ");
                primeiroValor = Double.NaN;
                operador = "";
            }
        });
    }

    private void RealizaOperacoes(){
        Button botaoIgual = findViewById(R.id.calculadora_result);
        botaoIgual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!operador.isEmpty() && !campoNumero.getText().toString().isEmpty()){
                    segundoValor = Double.parseDouble(campoNumero.getText().toString());
                    Double resultado = 0.0;

                    switch (operador){
                        case "+":
                            resultado = primeiroValor + segundoValor;
                            break;
                        case "--":
                            resultado = primeiroValor - segundoValor;
                            break;
                        case "x":
                            resultado = primeiroValor * segundoValor;
                            break;
                        case "/":
                            try{
                                resultado = primeiroValor / segundoValor;
                            }catch (ArithmeticException ex){
                                throw new RuntimeException(ex);
                            }
                            break;
                        case "%":
                            resultado = primeiroValor * (segundoValor * 100);
                            break;
                    }
                    campoNumero.setText(String.valueOf(resultado));
                    primeiroValor = resultado;
                    operador = "";
                }
            }
        });
    }
}