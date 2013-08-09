package com.tisa7.daysmatter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import android.content.Context;
import android.util.Log;

/**
 * save serializable data into file
 * 
 * @author alan
 * 
 */
public class SerializeUtil {

	public static final String TAG = "SerializeUtil";

	public static boolean saveIntoFile(Context ctx, String fileName, Serializable data) {
		try {
			FileOutputStream ops = ctx.openFileOutput(fileName, Context.MODE_PRIVATE);
			ObjectOutputStream os;
			os = new ObjectOutputStream(ops);
			os.writeObject(data);
			os.close();
			return true;
		} catch (FileNotFoundException e) {
			Log.d(TAG, e.toString());
		} catch (IOException e) {
			Log.d(TAG, e.toString());
		}
		return false;
	}

	public static Serializable readFile(Context ctx, String fileName) {
		try {
			FileInputStream fis = ctx.openFileInput(fileName);
			ObjectInputStream is = new ObjectInputStream(fis);
			Serializable data = (Serializable) is.readObject();
			is.close();
			return data;
		} catch (FileNotFoundException e) {
			Log.d(TAG, e.toString());
		} catch (IOException e) {
			Log.d(TAG, e.toString());
		} catch (ClassNotFoundException e) {
			Log.d(TAG, e.toString());
		}
		return null;
	}

	public static boolean deleteFile(Context ctx, String fileName) {
		String path = ctx.getFilesDir() + "/" + fileName;
		File file = new File(path);
		if (file.exists()) {
			if (file.delete()) {
				return true;
			}
		}
		return false;
	}
}
