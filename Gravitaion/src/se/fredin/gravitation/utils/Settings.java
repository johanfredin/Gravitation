package se.fredin.gravitation.utils;

public class Settings {
	
	// finals
	public static final float DEFAULT_SPEED = 9000f;
	public static final float DEFAULT_BULLET_SPEED = 2f;
	public static final float FAST_BULLET_SPEED = 7f;
	public static final float SLOW_BULLET_SPEED = 0.3f;
	public static final int MEDIUM_TIME_LIMIT = 600;
	public static final int SHORT_TIME_LIMIT = 300;
	public static final int LONG_TIME_LIMIT = 1200;
	public static final int MEDIUM_SCORE_LIMIT = 10;
	public static final int LOW_SCORE_LIMIT = 5;
	public static final int HIGH_SCORE_LIMIT = 20;
	
	// Adaptable
	public static int defaultTimeLimit = MEDIUM_TIME_LIMIT;
	public static int defaultScoreLimit = MEDIUM_SCORE_LIMIT;
	public static int currentLevel = 0;
	public static boolean isPaused = false;
	public static boolean isUnlimitedTime = false;
	public static boolean isUnlimitedcore = false;
	public static boolean bouncingBullets = false;
	public static boolean player1wins = false;
	public static boolean player2wins = false;
	
	
}
