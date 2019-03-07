package fi.tamk.tiko;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Utils {
	public static TextureRegion[] toTextureArray(TextureRegion[][]region,int cols, int rows) {
		TextureRegion [] frames = new TextureRegion[cols*rows];
		int index = 0;
		for (int loop = 0;loop < rows;loop++) {
			for (int loop2 = 0;loop2 < cols;loop2++) {
				frames[index++] = region[loop][loop2];
			}
		}
		return frames;
	}
	public static void flip(Animation<TextureRegion>animation) {
		TextureRegion[] regions = animation.getKeyFrames();
		for(TextureRegion r : regions) {
			r.flip(true,false);
		}
	}
}
