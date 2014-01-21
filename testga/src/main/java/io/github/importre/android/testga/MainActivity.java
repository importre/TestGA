package io.github.importre.android.testga;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.StandardExceptionParser;

import java.util.Map;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EasyTracker.getInstance(this).activityStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EasyTracker.getInstance(this).activityStop(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            initView(rootView);
            return rootView;
        }

        private void initView(View rootView) {
            Button[] buttons = {
                    (Button) rootView.findViewById(R.id.btn_start_activity),
                    (Button) rootView.findViewById(R.id.btn_crash),
                    (Button) rootView.findViewById(R.id.btn_exception),
                    (Button) rootView.findViewById(R.id.btn_cat1_event1),
                    (Button) rootView.findViewById(R.id.btn_cat1_event2),
                    (Button) rootView.findViewById(R.id.btn_cat2_event1),
                    (Button) rootView.findViewById(R.id.btn_cat2_event2),
            };

            for (Button button : buttons) {
                button.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View v) {
            final int id = v.getId();
            switch (id) {
                case R.id.btn_start_activity:
                    startSubActivity();
                    break;
                case R.id.btn_crash:
                    crash();
                    break;
                case R.id.btn_exception:
                    exception();
                    break;
                case R.id.btn_cat1_event1:
                case R.id.btn_cat1_event2:
                case R.id.btn_cat2_event1:
                case R.id.btn_cat2_event2:
                    event(id);
                    break;
                default:
                    break;
            }
        }

        /**
         * 버튼 이벤트
         *
         * @param id
         */
        private void event(int id) {
            EasyTracker easyTracker = EasyTracker.getInstance(getActivity());
            Map<String, String> event = null;

            switch (id) {
                case R.id.btn_cat1_event1:
                    event = MapBuilder.createEvent(
                            "ui_action_1",   // Event category (required)
                            "button_press",  // Event action (required)
                            "cat1_event1",   // Event label
                            null)            // Event value
                            .build();
                    break;
                case R.id.btn_cat1_event2:
                    event = MapBuilder.createEvent(
                            "ui_action_1",
                            "button_press",
                            "cat1_event2",
                            null)
                            .build();
                    break;
                case R.id.btn_cat2_event1:
                    event = MapBuilder.createEvent(
                            "ui_action_2",
                            "button_press",
                            "cat2_event1",
                            null)
                            .build();
                    break;
                case R.id.btn_cat2_event2:
                    event = MapBuilder.createEvent(
                            "ui_action_2",
                            "button_press",
                            "cat2_event2",
                            null)
                            .build();
                    break;
                default:
                    break;
            }

            if (event != null) {
                easyTracker.send(event);
            }
        }

        /**
         * 예외 처리
         */
        private void exception() {
            try {
                Button button = null;
                button.setVisibility(View.GONE);
            } catch (Exception e) {
                FragmentActivity activity = getActivity();
                EasyTracker easyTracker = EasyTracker.getInstance(activity);

                // 예외 데이터 생성 후 보냄
                easyTracker.send(MapBuilder.createException(
                        new StandardExceptionParser(activity, null).getDescription(
                                Thread.currentThread().getName(), // 예외가 발생한 스레드의 이름
                                e),                               // 예외
                        false)                                    // 치명적 에러인 경우 true
                        .build()
                );
            }
        }

        /**
         * 강제 크래쉬
         */
        private void crash() {
            int a = 10 / 0;
            Toast.makeText(getActivity(), "10 / 0 = " + a, Toast.LENGTH_SHORT).show();
        }

        /**
         * 다른 액티비티 실행
         */
        private void startSubActivity() {
            Intent intent = new Intent(getActivity(), SubActivity.class);
            startActivity(intent);
        }
    }

}
