package loon.action.sprite;

import loon.canvas.LColor;
import loon.geom.Path;
import loon.geom.Polygon;
import loon.geom.RectBox;
import loon.geom.Shape;
import loon.opengl.GLEx;
import loon.utils.IntMap;
import loon.utils.MathUtils;
import loon.utils.TArray;
import loon.utils.timer.LTimer;

public class Cycle extends Entity {

	public final static Cycle getSample(int type, float srcWidth,
			float srcHeight, float width, float height, float offset,
			int padding) {

		Cycle cycle = new Cycle();
		float s = 1;
		if (srcWidth > srcHeight) {
			s = MathUtils.max(srcWidth / width, srcHeight / height);
		} else {
			s = MathUtils.min(srcWidth / width, srcHeight / height);
		}
		final float scale = s;
		switch (type) {
		case 0:
			cycle = new Cycle() {

				private Path path;

				public void step(GLEx g, float x, float y, float progress,
						int index, int frame, LColor color, float alpha) {
					float cx = this.padding + 50, cy = this.padding + 50, angle = (MathUtils.PI / 180)
							* (progress * 360), innerRadius = index == 1 ? 10
							: 25;
					if (path == null) {
						path = new Path(getX() + x * scale, getY() + y * scale);
					} else {
						path.clear();
						path.set(getX() + x * scale, getY() + y * scale);
					}
					path.lineTo(getX()
							+ ((MathUtils.cos(angle) * innerRadius) + cx)
							* scale, getY()
							+ ((MathUtils.sin(angle) * innerRadius) + cy)
							* scale);
					path.close();
					g.drawPolyline(path);
				}
			};
			cycle.setLineWidth(5);
			cycle.setDelay(45);
			cycle.setColor(0xFF2E82);
			cycle.setStepType(4);
			cycle.setStepsPerFrame(1);
			cycle.setTrailLength(1);
			cycle.setPointDistance(0.05f);
			cycle.addPath(Cycle.ARC, 50, 50, 40, 0, 360);
			break;
		case 1:
			cycle.setColor(0xFF7B24);
			cycle.setStepsPerFrame(1);
			cycle.setTrailLength(1);
			cycle.setPointDistance(0.10f);
			cycle.setMultiplier(2);
			cycle.addPath(Cycle.ARC, 10 * scale, 10 * scale, 10 * scale, -270,
					-90);
			cycle.addPath(Cycle.BEZIER, 10 * scale, 0 * scale, 40 * scale,
					20 * scale, 20 * scale, 0, 30 * scale, 20 * scale);
			cycle.addPath(Cycle.ARC, 40 * scale, 10 * scale, 10 * scale, 90,
					-90);
			cycle.addPath(Cycle.BEZIER, 40 * scale, 0 * scale, 10 * scale,
					20 * scale, 30 * scale, 0, 20 * scale, 20 * scale);
			break;
		case 2:
			cycle.setColor(0xD4FF00);
			cycle.setStepType(1);
			cycle.setDelay(55);
			cycle.setStepsPerFrame(2);
			cycle.setTrailLength(0.3f);
			cycle.setPointDistance(0.1f);
			cycle.addPath(Cycle.LINE, 0, 0, 30 * scale, 0);
			cycle.addPath(Cycle.LINE, 30 * scale, 0 * scale, 30 * scale,
					30 * scale);
			cycle.addPath(Cycle.LINE, 30 * scale, 30 * scale, 0, 30 * scale);
			cycle.addPath(Cycle.LINE, 0, 30 * scale, 0, 0);
			break;
		case 3:

			cycle = new Cycle() {

				private Path path;

				@Override
				public void step(GLEx g, float x, float y, float progress,
						int index, int frame, LColor color, float alpha) {

					float tmp = g.alpha();
					float cx = this.padding + 50, cy = this.padding + 50, angle = (MathUtils.PI / 180)
							* (progress * 360);
					alpha = MathUtils.max(0.5f, alpha);
					g.setAlpha(alpha);
					if (path == null) {
						path = new Path(getX() + x * scale, getY() + y * scale);
					} else {
						path.clear();
						path.set(getX() + x * scale, getY() + y * scale);
					}
					path.lineTo(getX() + ((MathUtils.cos(angle) * 35) + cx)
							* scale, getY()
							+ ((MathUtils.sin(angle) * 35) + cy) * scale);
					path.close();
					g.drawPolyline(path);
					if (path == null) {
						path = new Path(getX()
								+ ((MathUtils.cos(-angle) * 32) + cx) * scale,
								getY() + ((MathUtils.sin(-angle) * 32) + cy)
										* scale);
					} else {
						path.clear();
						path.set(getX() + ((MathUtils.cos(-angle) * 32) + cx)
								* scale, getY()
								+ ((MathUtils.sin(-angle) * 32) + cy) * scale);
					}
					path.lineTo(getX() + ((MathUtils.cos(-angle) * 27) + cx)
							* scale, getY()
							+ ((MathUtils.sin(-angle) * 27) + cy) * scale);
					path.close();
					g.drawPolyline(path);
					g.setAlpha(tmp);
				}
			};
			cycle.setColor(0x05E2FF);
			cycle.setLineWidth(2);
			cycle.setStepType(4);
			cycle.setStepsPerFrame(1);
			cycle.setTrailLength(1);
			cycle.setPointDistance(0.025f);
			cycle.addPath(Cycle.ARC, 50, 50, 40, 0, 360);
			break;
		case 4:
			cycle.setColor(0xFFA50000);
			cycle.setStepsPerFrame(1);
			cycle.setTrailLength(1);
			cycle.setPointDistance(0.025f);
			cycle.addPath(Cycle.ARC, 50 * scale, 50 * scale, 40 * scale, 0, 360);
			break;
		case 5:
			cycle.setColor(0xFF2E82);
			cycle.setDelay(60);
			cycle.setStepType(1);
			cycle.setStepsPerFrame(1);
			cycle.setTrailLength(1);
			cycle.setPointDistance(0.1f);
			cycle.addPath(Cycle.LINE, 0, 20 * scale, 100 * scale, 20 * scale);
			cycle.addPath(Cycle.LINE, 100 * scale, 20 * scale, 0, 20 * scale);
			break;
		case 6:
			cycle.setStepsPerFrame(7);
			cycle.setTrailLength(0.7f);
			cycle.setPointDistance(0.01f);
			cycle.setDelay(35);
			cycle.setLineWidth(10);
			cycle.addPath(Cycle.LINE, 20 * scale, 70 * scale, 50 * scale,
					20 * scale);
			cycle.addPath(Cycle.LINE, 50 * scale, 20 * scale, 80 * scale,
					70 * scale);
			cycle.addPath(Cycle.LINE, 80 * scale, 70 * scale, 20 * scale,
					70 * scale);
			break;
		case 7:
			cycle.setColor(0xD4FF00);
			cycle.setStepsPerFrame(3);
			cycle.setTrailLength(1);
			cycle.setPointDistance(0.01f);
			cycle.setLineWidth(6);
			cycle.setPadding(0);
			cycle.addPath(Cycle.ARC, 50 * scale, 50 * scale, 20 * scale, 360, 0);
			break;
		case 8:
			cycle.setColor(0x05E2FF);
			cycle.setStepsPerFrame(1);
			cycle.setTrailLength(1);
			cycle.setPointDistance(0.02f);
			cycle.addPath(Cycle.ARC, 50 * scale, 50 * scale, 30 * scale, 0, 360);
			break;
		case 9:
			cycle.setStepType(1);
			cycle.setColor(LColor.yellow);
			cycle.addPath(Cycle.LINE, 10 * scale, 10 * scale, 90 * scale,
					10 * scale);
			cycle.addPath(Cycle.LINE, 90 * scale, 10 * scale, 90 * scale,
					90 * scale);
			cycle.addPath(Cycle.LINE, 90 * scale, 90 * scale, 10 * scale,
					90 * scale);
			cycle.addPath(Cycle.LINE, 10 * scale, 90 * scale, 10 * scale,
					10 * scale);
			break;
		}
		float size = MathUtils.min(srcWidth / (1 / cycle.getPointDistance()),
				srcHeight / (1 / cycle.getPointDistance()));
		cycle.setPadding(padding);
		cycle.setBlockWidth(size + offset);
		cycle.setBlockHeight(size + offset);
		cycle.setWidth(width * scale);
		cycle.setHeight(height * scale);
		return cycle;
	}

	public static final int OTHER = 0, DIM = 1, DEGREE = 2, RADIUS = 3;

	public static final int BEZIER = 0, ARC = 1, LINE = 2;

	protected float pointDistance;

	protected float multiplier;

	protected int frame, padding;

	protected int stepType, lineWidth;

	protected float trailLength, stepsPerFrame;

	protected boolean isUpdate, stopped;

	protected TArray<Object[]> data;

	protected static IntMap<float[]> signatures;

	protected TArray<Progress> points;

	private LTimer timer;

	private Polygon poly;

	private Progress last;

	protected float blockWidth, blockHeight, blockHalfWidth, blockHalfHeight;


	class Progress {

		float x;

		float y;

		float progress;

		public Progress(float x, float y, float p) {
			this.x = x;
			this.y = y;
			this.progress = p;
		}
	}

	public Cycle() {
		this(0, 0);
	}

	public Cycle(int x, int y) {
		this(x, y, 6, 6);
	}

	public Cycle(int x, int y, int w, int h) {
		this(null, x, y, w, h);
	}

	public Cycle(TArray<Object[]> path, int x, int y, int w, int h) {

		if (path != null) {
			data.add(path.toArray());
			isUpdate = true;
		} else {
			data = new TArray<Object[]>(10);
		}

		this.setRepaint(true);
		this.setSize(w, h);
		this.setLocation(x, y);
		this.timer = new LTimer(25);
		this.setColor(LColor.white);
		this.points = new TArray<Progress>();
		this.multiplier = 1;
		this.pointDistance = 0.05f;
		this.padding = 0;
		this.stepType = 0;
		this.stepsPerFrame = 1;
		this.trailLength = 1;
		this.blockWidth = w;
		this.blockHeight = h;
		this.blockHalfWidth = w / 2;
		this.blockHalfHeight = h / 2;
		if (signatures == null) {
			signatures = new IntMap<float[]>(3);
			signatures.put(ARC, new float[] { 1, 1, 3, 2, 2, 0 });
			signatures.put(BEZIER, new float[] { 1, 1, 1, 1, 1, 1, 1, 1 });
			signatures.put(LINE, new float[] { 1, 1, 1, 1 });
		}
		this.setup();

	}

	public void play() {
		this.stopped = false;
	}

	public void iterateFrame() {
		this.frame += this.stepsPerFrame;

		if (this.frame >= this.points.size) {
			this.frame = 0;
		}
	}

	public void stop() {
		this.stopped = true;
	}

	public void setDelay(long delay) {
		timer.setDelay(delay);
	}

	public long getDelay() {
		return timer.getDelay();
	}

	public void addPath(int type, float... f) {
		Object[] o = new Object[2];
		o[0] = type;
		o[1] = f;
		data.add(o);
		isUpdate = true;
	}

	private void setup() {

		if (!isUpdate) {
			return;
		}

		float[] args;
		float value;
		int index;
		for (Object[] o : data) {
			Integer type = (Integer) o[0];
			args = (float[]) o[1];

			for (int a = -1, al = args.length; ++a < al;) {

				index = (int) signatures.get(type)[a];
				value = args[a];
				switch (index) {
				case RADIUS:
					value *= this.multiplier;
					break;
				case DIM:
					value *= this.multiplier;
					value += this.padding;
					break;
				case DEGREE:
					value *= MathUtils.PI / 180;
					break;
				}

				args[a] = value;
			}
			callMethod(type, args);

		}
		this.isUpdate = false;
	}

	private final void step(GLEx g, Progress e, int index, int frame,
			LColor color, float alpha, float offsetX, float offsetY) {
		switch (stepType) {
		case 0:
			g.fillOval(x() + e.x - blockHalfWidth + offsetX, y() + e.y
					- blockHalfHeight + offsetY, blockWidth, blockHeight);
			break;
		case 1:
			g.fillRect(x() + e.x - blockHalfWidth + offsetX, y() + e.y
					- blockHalfHeight + offsetY, blockWidth, blockHeight);
			break;
		case 2:
			if (last != null) {
				float[] xs = { x() + last.x + offsetX, x() + e.x + offsetX };
				float[] ys = { y() + last.y + offsetY, y() + e.y + offsetY };
				g.drawPolygon(xs, ys, 2);
			}
			last = e;
			break;
		case 3:
			if (last != null) {
				g.drawLine(x() + last.x + offsetX, y() + last.y + offsetY, x()
						+ e.x + offsetX, y() + e.y + offsetY);
			}
			last = e;
			break;
		case 4:
			step(g, e.x + offsetX, e.y + offsetY, e.progress, index, frame,
					color, alpha);
			break;
		}
	}

	public void step(GLEx g, float x, float y, float progress, int index,
			int frame, LColor color, float alpha) {

	}

	@Override
	public void onUpdate(long elapsedTime) {
		if (timer.action(elapsedTime)) {
			this.iterateFrame();
		}
	}

	private final void callMethod(int index, float... f) {

		float[] result;

		for (float pd = this.pointDistance, t = pd; t <= 1; t += pd) {

			t = MathUtils.round(t * 1f / pd) / (1f / pd);
			switch (index) {
			case BEZIER:
				result = bezier(t, f[0], f[1], f[2], f[3], f[4], f[5], f[6],
						f[7]);
				break;
			case ARC:
				result = arc(t, f[0], f[1], f[2], f[3], f[4]);
				break;
			case LINE:
				result = line(t, f[0], f[1], f[2], f[3]);
				break;
			default:
				result = new float[] { 0f, 0f };
			}

			points.add(new Progress(result[0], result[1], t));

		}

	}

	private final float[] bezier(float t, float p0x, float p0y, float p1x,
			float p1y, float c0x, float c0y, float c1x, float c1y) {

		t = 1 - t;

		float i = 1 - t, x = t * t, y = i * i, a = x * t, b = 3 * x * i, c = 3
				* t * y, d = y * i;

		return new float[] { a * p0x + b * c0x + c * c1x + d * p1x,
				a * p0y + b * c0y + c * c1y + d * p1y };
	}

	private final float[] arc(float t, float cx, float cy, float radius,
			float start, float end) {
		float point = (end - start) * t + start;

		return new float[] { (MathUtils.cos(point) * radius) + cx,
				(MathUtils.sin(point) * radius) + cy };

	}

	private final float[] line(float t, float sx, float sy, float ex, float ey) {
		return new float[] { (ex - sx) * t + sx, (ey - sy) * t + sy };
	}

	private int tmpColor;


	@Override
	public void repaint(GLEx g, float offsetX, float offsetY) {

		this.setup();

		int pointsLength = points.size;

		Progress point;
		int index;
		int frameD;
		int indexD;

		float size = (pointsLength * this.trailLength);

		for (float i = -1, l = size; ++i < l && !this.stopped;) {

			index = (int) (frame + i);
			if (index < pointsLength) {
				point = points.get(index);
			} else {
				point = points.get(index - pointsLength);
			}
			this._alpha = (i / (l - 1));
			frameD = frame / (pointsLength - 1);
			indexD = (int) _alpha;
			if (lineWidth > 0) {
				g.setLineWidth(lineWidth);
			}
			if (_scaleX != 1 || _scaleY != 1) {
				g.saveTx();
				g.scale(_scaleX, _scaleY);
			}
			tmpColor = g.color();
			if (_alpha > 0 && _alpha < 1) {
				g.setAlpha(_alpha);
			}
			g.setTint(_baseColor);
			step(g, point, indexD, frameD, _baseColor, _alpha, offsetX + _offset.x, offsetY + _offset.y);
			if (_alpha != 1f) {
				g.setAlpha(1f);
			}
			g.setTint(tmpColor);
			if (lineWidth > 0) {
				g.resetLineWidth();
			}
			if (_scaleX != 1 || _scaleY != 1) {
				g.restoreTx();
			}
		}

	
	}

	public TArray<Object[]> getData() {
		return data;
	}

	public void setData(TArray<Object[]> data) {
		this.data = data;
	}

	public int getFrame() {
		return frame;
	}

	public void setFrame(int frame) {
		this.frame = frame;
	}

	public boolean isUpdate() {
		return isUpdate;
	}

	public void setUpdate(boolean isUpdate) {
		this.isUpdate = isUpdate;
	}

	public Progress getLast() {
		return last;
	}

	public void setLast(Progress last) {
		this.last = last;
	}

	public int getLineWidth() {
		return lineWidth;
	}

	public void setLineWidth(int lineWidth) {
		this.lineWidth = lineWidth;
	}

	public float getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(float multiplier) {
		this.multiplier = multiplier;
	}

	public int getPadding() {
		return padding;
	}

	public void setPadding(int padding) {
		this.padding = padding;
	}

	public float getPointDistance() {
		return pointDistance;
	}

	public void setPointDistance(float pointDistance) {
		this.pointDistance = pointDistance;
	}

	public TArray<Progress> getPoints() {
		return points;
	}

	public void setPoints(TArray<Progress> points) {
		this.points = points;
	}

	public float getStepsPerFrame() {
		return stepsPerFrame;
	}

	public void setStepsPerFrame(float stepsPerFrame) {
		this.stepsPerFrame = stepsPerFrame;
	}

	public int getStepType() {
		return stepType;
	}

	public void setStepType(int stepType) {
		this.stepType = stepType;
	}

	public boolean isStopped() {
		return stopped;
	}

	public float getTrailLength() {
		return trailLength;
	}

	public void setTrailLength(float trailLength) {
		this.trailLength = trailLength;
	}

	public int getBlockHeight() {
		return (int) blockHeight;
	}

	public void setBlockHeight(float blockHeight) {
		this.blockHeight = blockHeight;
		this.blockHalfHeight = blockHeight / 2;
	}

	public int getBlockWidth() {
		return (int) blockWidth;
	}

	public void setBlockWidth(float blockWidth) {
		this.blockWidth = blockWidth;
		this.blockHalfWidth = blockWidth / 2;
	}

	public Shape getShape() {
		if (isUpdate) {
			setup();
			poly = new Polygon();
			for (Progress point : points) {
				poly.addPoint(point.x, point.y);
			}
		}
		return poly;
	}

	@Override
	public RectBox getCollisionBox() {
		Shape shape = getShape();
		return getRect(shape.getX(), shape.getY(), shape.getWidth(),
				shape.getHeight());
	}

}
