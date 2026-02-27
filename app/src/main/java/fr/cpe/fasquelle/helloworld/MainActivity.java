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

    String displayText;

    SensorManager mSensorManager;

    private SensorEventListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            //displayText = display.getText().toString();
            setContentView(R.layout.activity_main);
            bouton = findViewById(R.id.bouton);
            display1 = findViewById(R.id.texte1);
            display2 = findViewById(R.id.texte2);
            display3 = findViewById(R.id.texte3);
            bouton.setOnClickListener(
                    view -> display2.setText("Alors? c’est pas beau ça? :D")
            );
            return insets;
        });
        listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float light = sensorEvent.values[0];
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {}
        };
    }
    @Override
    protected void onResume() {
        super.onResume();
        SensorManager manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor = manager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (sensor != null) {
            manager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        SensorManager manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        manager.unregisterListener(listener);
    }
}