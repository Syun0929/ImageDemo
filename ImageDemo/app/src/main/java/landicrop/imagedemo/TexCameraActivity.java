package landicrop.imagedemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.TextureView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TexCameraActivity extends AppCompatActivity {

    @BindView(R.id.textureView)
    TextureView textureView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texture);
        ButterKnife.bind(this);

    }


}
