package se.fredin.gravitation.utils;

/**
 * Handles the game settings.
 * @author Johan Fredin
 *
 */
public class Settings {
	
	/**
	 * Fixed value for players ship speed.
	 */
	public static final float DEFAULT_SPEED = 10000f;
	/**
	 * Fixed value for players bullet speed.
	 */
	public static final float DEFAULT_BULLET_SPEED = 2f;
	/**
	 * Fixed value for fast bullet speed.
	 */
	public static final float FAST_BULLET_SPEED = 7f;
	/**
	 * Fixed value for slow bullet speed.
	 */
	public static final float SLOW_BULLET_SPEED = 0.3f;
	/**
	 * Fixed value for medium time multiplayer match limit in seconds.
	 */
	public static final int MEDIUM_TIME_LIMIT = 600;
	/**
	 * Fixed value for match short multiplayer time limit in seconds.
	 */
	public static final int SHORT_TIME_LIMIT = 300;
	/**
	 * Fixed value for multiplayer long time multiplayer match in seconds.
	 */
	public static final int LONG_TIME_LIMIT = 1200;
	/**
	 * Fixed value for multiplayer medium score limit. 
	 */
	public static final int MEDIUM_SCORE_LIMIT = 10;
	/**
	 * Fixed value for multiplayer low score limit. 
	 */
	public static final int LOW_SCORE_LIMIT = 5;
	/**
	 * Fixed value for multiplayer high score limit. 
	 */
	public static final int HIGH_SCORE_LIMIT = 20;
	
	/**
	 * Adaptable value for default time limit in seconds.
	 */
	public static int defaultTimeLimit = MEDIUM_TIME_LIMIT;
	/**
	 * Adaptable value for default score limit.
	 */
	public static int defaultScoreLimit = MEDIUM_SCORE_LIMIT;
	/**
	 * Adaptable value for current level index.
	 */
	public static int currentLevel = 0;
	/**
	 * Sets whether or not game is being paused.
	 */
	public static boolean isPaused = false;
	/**
	 * Sets whether or not game is being played with an unlimited time limit.
	 */
	public static boolean isUnlimitedTime = false;
	/**
	 * Sets whether or not game is being played with an unlimited score limit.
	 */
	public static boolean isUnlimitedcore = false;
	/**
	 * Sets whether or not bullets should be reversed.
	 */
	public static boolean reversedBullets = false;
	/**
	 * Sets whether or not player 1 won the multiplayer match.
	 */
	public static boolean player1wins = false;
	/**
	 * Sets whether or not player 2 won the multiplayer match.
	 */
	public static boolean player2wins = false;
	
	
}
