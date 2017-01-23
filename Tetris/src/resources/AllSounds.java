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
	public static final Sound Winner = new Sound("/Winner.wav");
	public static final Sound Hrum = new Sound("/Hrum.wav");
	public static final Sound Shoroh = new Sound("/Shoroh.wav");
	
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
		Hrum.setVolume(volum);
		Shoroh.setVolume(volum);
	}
	public static void mute(int vol){
		float volum = (float)vol/1000;
		downSnd.setVolume(volum);
		lineSnd.setVolume(volum);
		rotateSnd.setVolume(volum);
		GOverSnd.setVolume(volum);
		Hrum.setVolume(volum);
		Shoroh.setVolume(volum);
	}
}
