/**
 * 
 */
package resources;


/**
 * @author NikitaNB
 *
 */
public class AllSounds {
	public static final Sound downSnd = new Sound("/down.WAV");
	public static final Sound lineSnd = new Sound("/line.WAV");
	public static final Sound rotateSnd = new Sound("/rotate.WAV");
	public static final Sound GOverSnd = new Sound("/Game_Over.WAV");
	public static final Sound winner = new Sound("/Winner.wav");
	public static final Sound hrum = new Sound("/Hrum.wav");
	public static final Sound shoroh = new Sound("/Shoroh.wav");
	public static final Sound shot = new Sound("/Shot.wav");
	public static final Sound rocketsBonus = new Sound("/BonusRocket.wav");
	public static final Sound rocketStart = new Sound("/RocketStart.wav");
	public static final Sound rocketBoom = new Sound("/RocketBoom.wav");
	public static final Sound ricochet = new Sound("/ricochet.wav");
	public static final Sound stoneDestroy = new Sound("/RockImpact.wav");
	
	public static void mute(boolean b){
		float volum;
		if(!b){
			volum=0.0f;
		}
		else{
			volum=1.0f;
		}
		downSnd.setVolume(volum);
		lineSnd.setVolume(volum);
		rotateSnd.setVolume(volum);
		GOverSnd.setVolume(volum);
		hrum.setVolume(volum);
		shoroh.setVolume(volum);
		shot.setVolume(volum);
		rocketsBonus.setVolume(volum);
		rocketStart.setVolume(volum);
		rocketBoom.setVolume(volum);
		ricochet.setVolume(volum);
		stoneDestroy.setVolume(volum);
	}
	public static void mute(int vol){
		float volum = (float)vol/1000;
		downSnd.setVolume(volum);
		lineSnd.setVolume(volum);
		rotateSnd.setVolume(volum);
		GOverSnd.setVolume(volum);
		hrum.setVolume(volum);
		shoroh.setVolume(volum);
		shot.setVolume(volum);
		rocketsBonus.setVolume(volum);
		rocketStart.setVolume(volum);
		rocketBoom.setVolume(volum);
		ricochet.setVolume(volum);
		stoneDestroy.setVolume(volum);
	}
}
