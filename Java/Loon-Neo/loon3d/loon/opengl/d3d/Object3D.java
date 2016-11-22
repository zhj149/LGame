package loon.opengl.d3d;

import loon.LObject;
import loon.LTexture;
import loon.action.map.Field2D;
import loon.action.sprite.ISprite;
import loon.canvas.LColor;
import loon.geom.RectBox;
import loon.opengl.GLEx;
import loon.opengl.PreBoxViewer3D;

public class Object3D extends LObject<ISprite> implements ISprite {

	private boolean visible;

	private boolean isPreview = false;

	// 轮廓预览模式
	private PreBoxViewer3D preview;

	public Object3D(boolean pre) {
		preview = null;
		isPreview = pre;
		visible = true;
	}

	public void load(String filename) {
		if (isPreview) {
			preview = PreBoxViewer3D.load(filename);
			if (null != preview) {
				preview.calculateFaceNormals();
				preview.rotate(1.0f, 1.0f, 5f, 0f, 0f, 0f);
			}
		}
	}

	public void rotate(float x, float y, float z, float ax, float ay, float az) {
		if (isPreview) {
			if (null != preview) {
				preview.rotate(x, y, z, ax, ay, az);
			}
		}
	}

	public void draw(GLEx g) {
		if (isPreview) {
			if (null != preview) {
				preview.draw(g);
			}
		}
	}

	public void setAmbientLight(float x, float y, float z, float f) {
		if (isPreview) {
			if (null != preview) {
				preview.setAmbientLight(x, y, z, f);
			}
		}
	}

	@Override
	public void setVisible(boolean v) {
		this.visible = v;
	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	@Override
	public void createUI(GLEx g) {
		draw(g);
	}

	@Override
	public void createUI(GLEx g, float offsetX, float offsetY) {
		draw(g);
	}

	@Override
	public RectBox getCollisionBox() {
		return getCollisionArea();
	}

	@Override
	public LTexture getBitmap() {
		return null;
	}

	@Override
	public void update(long elapsedTime) {

	}

	@Override
	public float getWidth() {
		return getContainerWidth();
	}

	@Override
	public float getHeight() {
		return getContainerHeight();
	}

	@Override
	public void close() {

	}

	@Override
	public void setColor(LColor c) {
		
	}

	@Override
	public LColor getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Field2D getField2D() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getScaleX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getScaleY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setScale(float sx, float sy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isBounded() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isContainer() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean inContains(float x, float y, float w, float h) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RectBox getRectBox() {
		// TODO Auto-generated method stub
		return null;
	}

}
