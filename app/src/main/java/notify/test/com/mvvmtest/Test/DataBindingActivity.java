package notify.test.com.mvvmtest.Test;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import notify.test.com.mvvmtest.R;
import notify.test.com.mvvmtest.databinding.ActivityDataBindingBinding;

public class DataBindingActivity extends AppCompatActivity {

    private ActivityDataBindingBinding binding;
    People people = new People();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding);


        people.setName("大卫");
        people.setAge(22);
        people.setIcon("https://www.baidu.com/img/bd_logo1.png");

        binding.setPeople(people);
        binding.setActivity(this);
    }


    public void onClick(View view) {
        binding.getPeople().setName("9999");

        Toast.makeText(this,"333",Toast.LENGTH_SHORT).show();
    }
}
