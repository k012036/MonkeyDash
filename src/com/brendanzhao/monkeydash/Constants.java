package com.brendanzhao.monkeydash;

public final class Constants {
	private Constants() {
		throw new UnsupportedOperationException();
	}
	
	public static final int CLIENT_WIDTH = 800;
	public static final int CLIENT_HEIGHT = 600;
	public static final int GRAVITY = 10;
	public static final int PIXEL_SPEED_PER_TICK = 8;
	public static final int BLOCK_LEVITATION_HEIGHT = 400;
	public static final int SPACE_BETWEEN_BLOCKS = 300;
	public static final int INITIAL_NUMBER_BLOCKS = 3;
	public static final int TIMER_TICK_MILLISECONDS = 20;
	public static final int INITIAL_TIMER_DELAY = 1000;
	
	public static final String GAME_NAME = "Monkey Dash";
	public static final String BACKGROUND_IMAGE_URL = "img/background.png";
	public static final String MONKEY_RUN_ONE_IMAGE_URL = "img/monkey_run1.png";
	public static final String MONKEY_RUN_TWO_IMAGE_URL = "img/monkey_run2.png";
	public static final String MONKEY_JUMP_IMAGE_URL = "img/monkey_jump.png";
	public static final String BLOCK_IMAGE_URL = "img/block.png";
}
