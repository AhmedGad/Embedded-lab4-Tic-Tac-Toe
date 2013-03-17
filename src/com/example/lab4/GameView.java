package com.example.lab4;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

@SuppressLint("ViewConstructor")
public class GameView extends View implements OnTouchListener {
	Game game = Game.getInstance();
	private Paint[] playerColors = new Paint[3];
	private int backgroundColor;
	private Paint gridLinesColor = new Paint();
	private int w;
	private int h;
	private int hMargin;
	int[] pwc = new int[3];
	boolean gameEnd = false;

	void init(DisplayMetrics displaymetrics) {
		setOnTouchListener(this);
		h = displaymetrics.heightPixels;
		w = displaymetrics.widthPixels;
		hMargin = h / 3;
		h -= hMargin;
		invalidate();
	}

	Context c;

	public GameView(Context context, DisplayMetrics displaymetrics) {
		super(context);
		c = context;
		playerColors[1] = new Paint();
		playerColors[2] = new Paint();
		playerColors[1].setColor(Color.WHITE);
		playerColors[2].setColor(Color.BLUE);
		backgroundColor = Color.BLACK;
		gridLinesColor.setColor(Color.GREEN);
		init(displaymetrics);
	}

	public void setPlayerOneColor(int color) {
		playerColors[1].setColor(color);
		invalidate();
	}

	public void setPlayerTwoColor(int color) {
		playerColors[2].setColor(color);
		invalidate();
	}

	public void setBackGroundColor(int color) {
		backgroundColor = color;
		invalidate();
	}

	public void setGridLinesColor(int color) {
		gridLinesColor.setColor(color);
		invalidate();
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas g) {
		setBackgroundColor(backgroundColor);

		g.drawLine(w / 3, 0, w / 3, h, gridLinesColor);
		g.drawLine(2 * w / 3, 0, 2 * w / 3, h, gridLinesColor);
		g.drawLine(0, h / 3, w, h / 3, gridLinesColor);
		g.drawLine(0, 2 * h / 3, w, 2 * h / 3, gridLinesColor);

		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		// turn antialiasing on
		paint.setAntiAlias(true);
		paint.setTextSize(w / 20);
		if (backgroundColor != Color.WHITE)
			paint.setColor(Color.WHITE);
		g.drawText("Player X: " + pwc[1] + "  Player O: " + pwc[2] + "  Tie: "
				+ pwc[0] + "  Turn: " + game.getTurn(), 10, h + 30, paint);

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				int player = game.getPlayer(i, j);
				if (player != 0) {
					int sy = i * h / 3;
					int sx = j * w / 3;
					if (player == 1) {
						g.drawLine(sx, sy, sx + w / 3, sy + h / 3,
								playerColors[player]);
						g.drawLine(sx + w / 3, sy, sx, sy + h / 3,
								playerColors[player]);
					} else {
						g.drawCircle(sx + (w / 6), sy + h / 6,
								Math.min(w / 6, h / 6), playerColors[player]);
					}
				}
			}
		g.drawLine(margin, margin, w - margin, margin, gridLinesColor);
		g.drawLine(margin, 0, margin, h, gridLinesColor);
		g.drawLine(w - margin, 0, w - margin, h, gridLinesColor);
		g.drawLine(margin, h, w - margin, h, gridLinesColor);
	}

	private int margin = 1;

	@Override
	public boolean onTouch(View v, MotionEvent a) {
		if (gameEnd) {
			CharSequence text = "game ended";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(c, text, duration);
			toast.show();
			return false;
		}
		if (a.getY() > h)
			return false;

		int j = (int) a.getX() / (w / 3);
		int i = (int) a.getY() / (h / 3);

		if (game.isFree(i, j)) {
			game.play(i, j);
			int winner = game.winner();
			if (winner != 0) {

				CharSequence text = "player " + (winner == 2 ? 'O' : 'X')
						+ " Wins";
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(c, text, duration);
				toast.show();

				pwc[winner]++;
				gameEnd = true;
			}
			if (game.isTie()) {
				CharSequence text = "game ended as tie";
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(c, text, duration);
				toast.show();
				pwc[0]++;
				gameEnd = true;
			}
		}
		invalidate();
		return false;
	}
}