package com.mk.intrigue;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.Color;
import com.mk.intrigue.system.IntrigueGraphicSystem;
public final class IntrigueGraphicalDebugger {
	private static ShapeRenderer shapeRenderer;
	private IntrigueGraphicalDebugger() {
		
	}
	public static void enable() {
		shapeRenderer = new ShapeRenderer();
	}
	public static void drawDebugRay(Vector3 rayFrom, Vector3 rayTo) {
		shapeRenderer.setProjectionMatrix(IntrigueGraphicSystem.cam.combined);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.line(rayFrom, rayTo);
		shapeRenderer.end();
	}
	public static void drawDebugCube(Vector3 pos, float l) {
		shapeRenderer.setProjectionMatrix(IntrigueGraphicSystem.cam.combined);
		shapeRenderer.setColor(Color.YELLOW);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.box(pos.x, pos.y, pos.z,l, l, l);
		shapeRenderer.end();
	}
}