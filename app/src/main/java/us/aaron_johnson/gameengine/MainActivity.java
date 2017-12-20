package us.aaron_johnson.gameengine;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import us.aaron_johnson.gameengine.GameEngine.Audio.AudioController;
import us.aaron_johnson.gameengine.GameEngine.Base.Common;
import us.aaron_johnson.gameengine.GameEngine.Math.Euclidean.Vector2;
import us.aaron_johnson.gameengine.GameEngine.Physics.CircleCollider;
import us.aaron_johnson.gameengine.GameEngine.Physics.Collider;
import us.aaron_johnson.gameengine.GameEngine.Physics.ColliderController;
import us.aaron_johnson.gameengine.GameEngine.Shapes.Circle;
import us.aaron_johnson.gameengine.HelicopterGame.EndCircle;
import us.aaron_johnson.gameengine.HelicopterGame.HelicopterGameView;
import us.aaron_johnson.gameengine.HelicopterGame.Level;

public class MainActivity extends Activity {

    HelicopterGameView gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Point screenSize = new Point();
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(screenSize);

        gv = new HelicopterGameView(this, screenSize);
        gv.drawFPS = true;

        gv.addGameObject(Common.Layer.SKY, new StarField(gv,100,3000, gv.getScreenWidth(), gv.getScreenHeight()));



        setContentView(gv);
    }

    public void LOGD(String message){
        Log.d("MainActivity", message);
    }

    @Override
    protected void onStart() {
        LOGD("onStart");
        super.onStart();
        gv.start();
    }

    @Override
    protected void onPause() {
        LOGD("onPause");
        super.onPause();
        gv.pause();
    }

    @Override
    protected void onResume() {
        LOGD("onResume");
        super.onResume();
        gv.resume();
    }

    @Override
    protected void onStop() {
        LOGD("onStop");
        super.onStop();
        gv.stop();
    }

    protected void testFileWrite(){
        try {
            DataOutputStream dos = new DataOutputStream(openFileOutput("fake_file_name", Context.MODE_PRIVATE));
            dos.writeInt(123);
            dos.writeDouble(456.789);
            String toSave = "abcdefg";
            dos.writeInt(toSave.length());
            dos.writeChars(toSave);
            dos.write(toSave.getBytes());
            dos.flush();
            dos.close();
        }catch (Exception e){
            Log.d("_test_","Exception Writing To File"+e.toString());
        }
    }

    protected void testFileRead(){
        try{
            Log.d("_test_","Reading From File");
            DataInputStream dis = new DataInputStream(openFileInput("fake_file_name"));
            Log.d("_test_",Integer.toString(dis.readInt()));
            Log.d("_test_",Double.toString(dis.readDouble()));
            int strLen = dis.readInt();
            Log.d("_test_","String Length: "+strLen);
            String str = new String();
            for(int i = 0; i < strLen; ++i){
                str += dis.readChar();
            }
            Log.d("_test_","String: "+str);
            byte[] sbs = new byte[strLen];
            dis.read(sbs);
            Log.d("_test_", "From Bytes: "+new String(sbs));
            dis.close();
            Log.d("_test_","Delete File");
            deleteFile("fake_file_name");
        }catch (Exception e){
            Log.d("_test_","Exception Reading From File"+e.toString());
        }
    }
}
