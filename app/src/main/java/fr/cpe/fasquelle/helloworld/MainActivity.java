package fr.cpe.fasquelle.helloworld;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button bouton;
    TextView display1;
    TextView display2;
    TextView display3;
    SensorManager mSensorManager;

    private SensorEventListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        setContentView(R.layout.activity_main);
        bouton = findViewById(R.id.bouton);
        display1 = findViewById(R.id.texte1);
        display2 = findViewById(R.id.texte2);
        display3 = findViewById(R.id.texte3);
        final String resetText = "Cliquez pour réinitialiser";
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            String defaultText = getString(R.string.deuxiemeBouton);
            String successText = getString(R.string.success);
            String defaultButton = getString(R.string.bouton);
            bouton.setOnClickListener(view -> {
                String currentText = display2.getText().toString();
                boolean hasDefaultText = currentText.contains(defaultText);
                if (hasDefaultText) {
                    display2.setText(successText);
                    bouton.setText(resetText);
                }
                else{
                    display2.setText(defaultText);
                    bouton.setText(defaultButton);

                }
            });
            return insets;
        });
        listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
                    float x = sensorEvent.values[0];

                    String lightDisplay = "Lumière: " + x;
                    display1.setText(lightDisplay);
                }

                if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

                    float x = sensorEvent.values[0];
                    float y = sensorEvent.values[1];
                    float z = sensorEvent.values[2];

                    String accelDisplay = String.format("Accélération X,Y,Z : %.2f; %.2f: %.2f", x, y, z);

                    display3.setText(accelDisplay);
                }
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {}
        };
    }
    @Override
    protected void onResume() {
        super.onResume();
        SensorManager manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor lightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        Sensor accelSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Register Light
        if (lightSensor != null) {
            mSensorManager.registerListener(listener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        // Register Accelerometer
        if (accelSensor != null) {
            mSensorManager.registerListener(listener, accelSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        SensorManager manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        manager.unregisterListener(listener);
    }
}