package us.aaron_johnson.gameengine.GameEngine.Images;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;
import java.util.Map;

import us.aaron_johnson.gameengine.GameEngine.Base.Controller;
import us.aaron_johnson.gameengine.GameEngine.Base.GameView;
import us.aaron_johnson.gameengine.R;

/**
 * Created by combu on 12/20/2017.
 */

public class ImageController extends Controller {

    public static Resources resources = null;

    public ImageController(GameView gameView) {
        super(gameView);
    }

    public ImageController(GameView gameView, Resources resources) {
        super(gameView);
        this.resources = resources;
    }

    protected static class _map_key{
        int resource = Integer.MIN_VALUE;
        String filename = null;

        public _map_key(int resource){
            this.resource = resource;
        }

        public _map_key(String filename){
            this.filename = filename;
        }

        @Override
        public int hashCode() {
            if(filename == null){
                return resource;
            }else{
                return filename.hashCode();
            }
        }

        public boolean otherEquals(_map_key other){
            return filename == other.filename && resource == other.resource;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj == this){
                return true;
            }
            if(obj == null || getClass() != obj.getClass()){
                return false;
            }
            return otherEquals((_map_key)obj);
        }
    }

    protected static Map<_map_key, Bitmap> bitmapMap = new HashMap<>();

    public static Bitmap loadBitmap(int resourceId){
        if(resources == null){
            return null;
        }

        _map_key key = new _map_key(resourceId);
        if(bitmapMap.containsKey(key)){
            return bitmapMap.get(key);
        }

        Bitmap btmp = BitmapFactory.decodeResource(resources, resourceId);
        bitmapMap.put(key, btmp);
        return btmp;
    }

    public static Bitmap loadBitmap(String filename){
        if(resources == null){
            return null;
        }

        _map_key key = new _map_key(filename);
        if(bitmapMap.containsKey(key)){
            return bitmapMap.get(key);
        }

        Bitmap btmp = BitmapFactory.decodeFile(filename);
        bitmapMap.put(key, btmp);
        return btmp;
    }
}
