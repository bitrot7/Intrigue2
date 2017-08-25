package com.mk.intrigue.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * Quick util class for logging data to files.
 * @author wind2
 *
 */
public class Logger {

	private static FileHandle handle = Gdx.files.absolute("~/IntrigueLogs/velocities.log");
	private static FileHandle mt_velLogs = Gdx.files.absolute("~/IntrigueLogs/velocities.log");
	private static FileHandle mt_posLogs = Gdx.files.absolute("~/IntrigueLogs/positions.log");
	
	public static void WriteToGameLog(String atStr)
	{
		handle.writeString(atStr, true);
	}
	
	public static void WriteToVelocityLogs(String atStr)
	{
		mt_velLogs.writeString(atStr, true);
	}
	
	public static void WriteToPositionLogs(String atStr)
	{
		mt_posLogs.writeString(atStr, true);
	}
}
