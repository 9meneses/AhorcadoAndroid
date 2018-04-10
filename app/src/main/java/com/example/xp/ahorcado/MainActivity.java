package com.example.xp.ahorcado;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    String palabraOculta = "CETYS";
    int numeroDeFallos = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null){
            getSupportFragmentManager() //todos los fragments
                    .beginTransaction() //ordena que se haga la transicion
                    .add(R.id.ventaJuego //añade el fragment
                            , new VentanaAhorcado()).commit();
        }

    }
    @Override //sobrescribir un metodo que ya existe
    protected void onStart(){
        super.onStart();
        palabraOculta = escogePalabra();
        String barras = "";
        for(int i = 0; i<palabraOculta.length(); i++){
            barras += "_ ";
        }
        ((TextView) findViewById(R.id.palabraConGuiones)).setText(barras);
    }

    public void botonPulsado(View vista){
        Button boton = (Button) findViewById(vista.getId());
        boton.setVisibility(View.INVISIBLE);
        chequeaLetra(boton.getText().toString());

    }
    private void chequeaLetra (String letra) {

        if (numeroDeFallos < 6) {
            letra = letra.toUpperCase();
            ImageView imagenAhorcado = ((ImageView) findViewById(R.id.imagenAhorcado));
            TextView textoGuiones = ((TextView) findViewById(R.id.palabraConGuiones));
            String palabraConGuiones = textoGuiones.getText().toString();
            boolean acierto = false;
            for (int i = 0; i < palabraOculta.length(); i++) {
                if (palabraOculta.charAt(i) == letra.charAt(0)) {
                    //quita el guión bajo de la letra correspondiente
                    palabraConGuiones = palabraConGuiones.substring(0, 2 * i)
                            + letra
                            + palabraConGuiones.substring(2 * i + 1);
                    acierto = true;
                }
            }
            //chequeo si se ha terminado la partida porque ha acertado todas las letras
            if (!palabraConGuiones.contains("_")) {
                imagenAhorcado.setImageResource(R.drawable.acertastetodo);

                String nivel = "0";
                nivel += "1";
                ((TextView) findViewById(R.id.buttonVacio)).setText(nivel);


                Intent i = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage( getBaseContext().getPackageName() );

                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(i);





            }


            textoGuiones.setText(palabraConGuiones);


            if (!acierto) {
                numeroDeFallos++;

                switch (numeroDeFallos) {
                    case 0:
                        imagenAhorcado.setImageResource(R.drawable.ahorcado_0);
                        break;
                    case 1:
                        imagenAhorcado.setImageResource(R.drawable.ahorcado_1);
                        break;
                    case 2:
                        imagenAhorcado.setImageResource(R.drawable.ahorcado_2);
                        break;
                    case 3:
                        imagenAhorcado.setImageResource(R.drawable.ahorcado_3);
                        break;
                    case 4:
                        imagenAhorcado.setImageResource(R.drawable.ahorcado_4);
                        break;
                    case 5:
                        imagenAhorcado.setImageResource(R.drawable.ahorcado_5);
                        break;
                    default:
                        imagenAhorcado.setImageResource(R.drawable.ahorcado_fin);
                        Intent i = getBaseContext().getPackageManager()
                                .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);

                        break;
                }

            }

        }

    }
    private String escogePalabra() {
        String auxiliar = "";
        String[] listaPalabras = {"hola", "adios", "cono", "desconozco", "icono", "diacono", "conocida", "conocimiento"};
        Random r = new Random();
        auxiliar = listaPalabras[ r.nextInt(listaPalabras.length)];
        auxiliar = auxiliar.toUpperCase();
        return auxiliar;

    }







}
