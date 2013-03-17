package com.example.lab4;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	GameView game;
	boolean init = true;

	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		game.init(displaymetrics);
	}

	int colors[] = { Color.WHITE, Color.BLACK, Color.GREEN, Color.BLUE,
			Color.RED };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

		game = new GameView(this, displaymetrics);

		setContentView(game);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.new_game:
			DisplayMetrics displaymetrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
			game.game.init();
			game.gameEnd = false;
			return true;
		case R.id.clear_score:
			game.pwc[1] = game.pwc[2] = 0;
			return true;
		case R.id.help:
			setContentView(R.layout.help);
			((Button) findViewById(R.id.goBack_button))
					.setOnClickListener(this);
			return true;
		case R.id.options:
			setContentView(R.layout.settings);
			((Button) findViewById(R.id.cancel_btn)).setOnClickListener(this);
			((Button) findViewById(R.id.ok_btn)).setOnClickListener(this);
			fillLayout();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void fillLayout() {
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.colors_array,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		((Spinner) findViewById(R.id.bg_color)).setAdapter(adapter);
		((Spinner) findViewById(R.id.line_color)).setAdapter(adapter);
		((Spinner) findViewById(R.id.x_color)).setAdapter(adapter);
		((Spinner) findViewById(R.id.o_color)).setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.game_menu, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		System.out.println("jaksndkajsndakjlsndkajldsnlk");
		if (v.getId() == R.id.goBack_button || v.getId() == R.id.cancel_btn) {
			setContentView(game);
		} else if (v.getId() == R.id.ok_btn) {
			Spinner s[] = new Spinner[4];
			s[0] = ((Spinner) findViewById(R.id.bg_color));
			s[1] = ((Spinner) findViewById(R.id.line_color));
			s[2] = ((Spinner) findViewById(R.id.x_color));
			s[3] = ((Spinner) findViewById(R.id.o_color));
			boolean vis[] = new boolean[s[0].getCount()];
			for (int i = 0; i < s.length; i++) {
				if (vis[s[i].getSelectedItemPosition()]) {
					CharSequence text = "You can't select the same color for 2 items";
					int duration = Toast.LENGTH_SHORT;
					Toast toast = Toast.makeText(getApplicationContext(), text,
							duration);
					toast.show();
				} else if (i == s.length - 1) {
					game.setBackGroundColor(colors[s[0]
							.getSelectedItemPosition()]);
					game.setGridLinesColor(colors[s[1]
							.getSelectedItemPosition()]);
					game.setPlayerOneColor(colors[s[2]
							.getSelectedItemPosition()]);
					game.setPlayerTwoColor(colors[s[3]
							.getSelectedItemPosition()]);
					setContentView(game);
				}
				vis[s[i].getSelectedItemPosition()] = true;
			}
		}
	}
}
