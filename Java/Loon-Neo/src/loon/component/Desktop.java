/**
 * 
 * Copyright 2008 - 2009
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * @project loon
 * @author cping
 * @email：javachenpeng@yahoo.com
 * @version 0.1
 */
package loon.component;

import loon.LRelease;
import loon.Screen;
import loon.action.sprite.ISprite;
import loon.component.layout.LayoutConstraints;
import loon.component.layout.LayoutManager;
import loon.component.layout.LayoutPort;
import loon.event.SysInput;
import loon.event.SysTouch;
import loon.geom.RectBox;
import loon.opengl.GLEx;
import loon.utils.TArray;

/**
 * 桌面组件总父类，用来注册，控制，以及渲染所有桌面组件（所有默认支持触屏的组件，被置于此）
 * 
 */
public class Desktop implements LRelease {

	private final static TArray<Desktop> DESKTOP_CACHE = new TArray<Desktop>(8);

	public final static int allDesktopCount() {
		int size = 0;
		for (int i = 0, len = DESKTOP_CACHE.size; i < len; i++) {
			size += DESKTOP_CACHE.get(i).size();
		}
		return size;
	}

	// 空桌面布局
	public static final Desktop EMPTY_DESKTOP = new Desktop();

	// 输入设备监听
	protected final Screen input;

	private LContainer contentPane;

	private LComponent modal;

	private LComponent hoverComponent;

	private LComponent selectedComponent;

	private LComponent[] clickComponent = new LComponent[1];

	/**
	 * 构造一个可用桌面
	 * 
	 * @param input
	 * @param width
	 * @param height
	 */
	public Desktop(Screen screen, int width, int height) {
		this.contentPane = new LPanel(0, 0, width, height);
		this.input = screen;
		this.setDesktop(this.contentPane);
		DESKTOP_CACHE.add(this);
	}

	public Desktop(Screen screen, float width, float height) {
		this(screen, (int) width, (int) height);
	}

	/**
	 * 空桌面布局
	 * 
	 */
	private Desktop() {
		this.contentPane = new LPanel(0, 0, 1, 1);
		this.input = null;
		this.setDesktop(this.contentPane);
	}

	public int size() {
		return contentPane.getComponentCount();
	}

	public void addAt(LComponent comp, float x, float y) {
		if (comp != null) {
			comp.setLocation(x, y);
			add(comp);
		}
	}

	public void addSprite(ISprite sprite) {
		add(new LSpriteUI(sprite));
	}

	public void addSpriteAt(ISprite sprite, float x, float y) {
		addAt(new LSpriteUI(sprite), x, y);
	}

	public void add(LComponent comp) {
		if (comp == null) {
			return;
		}
		if (comp.isFull) {
			this.input.setRepaintMode(Screen.SCREEN_NOT_REPAINT);
		}
		this.contentPane.add(comp);
		this.processTouchMotionEvent();
	}

	public int remove(LComponent comp) {
		int removed = this.removeComponent(this.contentPane, comp);
		if (removed != -1) {
			this.processTouchMotionEvent();
		}
		return removed;
	}

	public boolean removeTag(Object tag) {
		boolean removed = this.removeComponentTag(this.contentPane, tag);
		if (removed) {
			this.processTouchMotionEvent();
		}
		return removed;
	}

	public boolean removeUIName(String uiName) {
		boolean removed = this.removeComponentUIName(this.contentPane, uiName);
		if (removed) {
			this.processTouchMotionEvent();
		}
		return removed;
	}

	public boolean removeName(String name) {
		boolean removed = this.removeComponentName(this.contentPane, name);
		if (removed) {
			this.processTouchMotionEvent();
		}
		return removed;
	}

	public boolean removeNotTag(Object tag) {
		boolean removed = this.removeComponentNotTag(this.contentPane, tag);
		if (removed) {
			this.processTouchMotionEvent();
		}
		return removed;
	}

	public boolean removeNotUIName(String uiName) {
		boolean removed = this.removeComponentNotUIName(this.contentPane,
				uiName);
		if (removed) {
			this.processTouchMotionEvent();
		}
		return removed;
	}

	public boolean removeNotName(String name) {
		boolean removed = this.removeComponentNotName(this.contentPane, name);
		if (removed) {
			this.processTouchMotionEvent();
		}
		return removed;
	}

	private boolean removeComponentUIName(LContainer container, String name) {
		return container.removeUIName(name);
	}

	private boolean removeComponentName(LContainer container, String name) {
		return container.removeName(name);
	}

	private boolean removeComponentTag(LContainer container, Object tag) {
		return container.removeTag(tag);
	}

	private boolean removeComponentNotName(LContainer container, String name) {
		return container.removeNotName(name);
	}

	private boolean removeComponentNotUIName(LContainer container, String name) {
		return container.removeNotUIName(name);
	}

	private boolean removeComponentNotTag(LContainer container, Object tag) {
		return container.removeNotTag(tag);
	}

	private int removeComponent(LContainer container, LComponent comp) {
		int removed = container.remove(comp);
		LComponent[] components = container._childs;
		int i = 0;
		while (removed == -1 && i < components.length - 1) {
			if (components[i].isContainer()) {
				removed = this
						.removeComponent((LContainer) components[i], comp);
			}
			i++;
		}

		return removed;
	}

	boolean isClicked;

	/**
	 * 刷新当前桌面
	 * 
	 */
	public void update(long timer) {
		if (!this.contentPane.isVisible()) {
			return;
		}
		this.processEvents();
		// 刷新桌面中子容器组件
		this.contentPane.update(timer);
	}
	

	public LayoutConstraints getRootConstraints() {
		if (contentPane != null) {
			return contentPane.getRootConstraints();
		}
		return null;
	}

	public LayoutPort getLayoutPort() {
		if (contentPane != null) {
			return contentPane.getLayoutPort();
		}
		return null;
	}

	public LayoutPort getLayoutPort(final RectBox newBox,
			final LayoutConstraints newBoxConstraints) {
		if (contentPane != null) {
			return contentPane.getLayoutPort(newBox,
					newBoxConstraints);
		}
		return null;
	}

	public LayoutPort getLayoutPort(final LayoutPort src) {
		if (contentPane != null) {
			return contentPane.getLayoutPort(src);
		}
		return null;
	}

	public void layoutElements(final LayoutManager manager,
			final LComponent... comps) {
		if (contentPane != null) {
			contentPane.layoutElements(manager, comps);
		}
	}

	public void layoutElements(final LayoutManager manager,
			final LayoutPort... ports) {
		if (contentPane != null) {
			contentPane.layoutElements(manager, ports);
		}
	}

	public void packLayout(final LayoutManager manager) {
		if (contentPane != null) {
			contentPane.packLayout(manager);
		}
	}

	public void setAutoDestory(final boolean a) {
		if (contentPane != null) {
			contentPane.setAutoDestroy(a);
		}
	}

	public boolean isAutoDestory() {
		if (contentPane != null) {
			return contentPane.isAutoDestroy();
		}
		return false;
	}

	public void doClick(int x, int y) {
		if (!this.contentPane.isVisible()) {
			return;
		}
		LComponent[] components = contentPane._childs;
		for (int i = 0; i < components.length; i++) {
			LComponent component = components[i];
			if (component != null && component.intersects(x, y)) {
				component.update(0);
				component.processTouchPressed();
			}
		}
		isClicked = true;
	}

	public void doClicked(int x, int y) {
		if (!this.contentPane.isVisible()) {
			return;
		}
		LComponent[] components = contentPane._childs;
		for (int i = 0; i < components.length; i++) {
			LComponent component = components[i];
			if (component != null && component.intersects(x, y)) {
				component.update(0);
				component.processTouchReleased();
				component.processTouchClicked();
			}
		}
		isClicked = true;
	}

	public void createUI(GLEx g) {
		try {
			g.saveTx();
			this.contentPane.createUI(g);
		} finally {
			g.restoreTx();
		}
	}

	/**
	 * 事件监听
	 * 
	 */
	public void processEvents() {
		// 鼠标滑动
		this.processTouchMotionEvent();
		// 鼠标事件
		if (this.hoverComponent != null && !this.hoverComponent._touchLocked
				&& this.hoverComponent.isEnabled()) {
			this.processTouchEvent();
		}
		// 键盘事件
		if (this.selectedComponent != null
				&& !this.selectedComponent._keyLocked
				&& this.selectedComponent.isEnabled()) {
			this.processKeyEvent();
		}
	}

	/**
	 * 鼠标运动事件
	 * 
	 */
	private void processTouchMotionEvent() {
		if (this.hoverComponent != null && !this.hoverComponent._touchLocked
				&& this.hoverComponent.isEnabled() && this.input.isMoving()) {
			if (this.input.getTouchDX() != 0 || this.input.getTouchDY() != 0
					|| SysTouch.getDX() != 0 || SysTouch.getDY() != 0) {
				this.hoverComponent.processTouchDragged();
			}

		} else {
			// 获得当前窗体下鼠标坐标
			LComponent comp = this.findComponent(this.input.getTouchX(),
					this.input.getTouchY());

			if (comp != null && !comp._touchLocked) {

				if (this.input.getTouchDX() != 0
						|| this.input.getTouchDY() != 0
						|| SysTouch.getDX() != 0 || SysTouch.getDY() != 0) {
					comp.processTouchMoved();
				}
				if (this.hoverComponent == null) {
					comp.processTouchEntered();
				} else if (comp != this.hoverComponent
						&& !this.hoverComponent._touchLocked) {
					this.hoverComponent.processTouchExited();
					comp.processTouchEntered();
				}

			} else {
				if (this.hoverComponent != null
						&& !this.hoverComponent._touchLocked) {
					this.hoverComponent.processTouchExited();
				}
			}
			this.hoverComponent = comp;
		}
	}

	/**
	 * 鼠标按下事件
	 * 
	 */
	private void processTouchEvent() {
		int pressed = this.input.getTouchPressed(), released = this.input
				.getTouchReleased();
		if (pressed > SysInput.NO_BUTTON) {
			if (!isClicked && this.hoverComponent != null
					&& !this.hoverComponent._touchLocked) {
				this.hoverComponent.processTouchPressed();
			}
			this.clickComponent[0] = this.hoverComponent;
			if (this.hoverComponent != null
					&& !this.hoverComponent._touchLocked
					&& this.hoverComponent.isFocusable()) {
				if ((pressed == SysTouch.TOUCH_DOWN || pressed == SysTouch.TOUCH_UP)
						&& this.hoverComponent != this.selectedComponent) {
					this.selectComponent(this.hoverComponent);
				}
			}
		}
		if (released > SysInput.NO_BUTTON) {
			if (!isClicked && this.hoverComponent != null
					&& !this.hoverComponent._touchLocked) {
				this.hoverComponent.processTouchReleased();
				// 当释放鼠标时，点击事件生效
				if (this.clickComponent[0] == this.hoverComponent
						&& this.hoverComponent != null
						&& !this.hoverComponent._touchLocked) {
					this.hoverComponent.processTouchClicked();
				}
			}
		}
		this.isClicked = false;
	}

	/**
	 * 触发键盘事件
	 * 
	 */
	private void processKeyEvent() {
		if (this.selectedComponent != null
				&& !this.selectedComponent._keyLocked
				&& this.input.getKeyPressed() != SysInput.NO_KEY) {
			this.selectedComponent.keyPressed();
		}
		if (this.selectedComponent != null
				&& !this.selectedComponent._keyLocked
				&& this.input.getKeyReleased() != SysInput.NO_KEY
				&& this.selectedComponent != null) {
			this.selectedComponent.processKeyReleased();
		}
	}

	/**
	 * 查找指定坐标点成员
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private LComponent findComponent(int x, int y) {
		if (this.modal != null && !this.modal.isContainer()) {
			return null;
		}
		// 返回子容器
		LContainer panel = (this.modal == null) ? this.contentPane
				: ((LContainer) this.modal);
		LComponent comp = panel.findComponent(x, y);
		return comp;
	}

	/**
	 * 清除容器焦点
	 */
	public void clearFocus() {
		this.deselectComponent();
	}

	void deselectComponent() {
		if (this.selectedComponent == null) {
			return;
		}
		this.selectedComponent.setSelected(false);
		this.selectedComponent = null;
	}

	/**
	 * 查找指定容器
	 * 
	 * @param comp
	 * @return
	 */
	boolean selectComponent(LComponent comp) {
		if (!comp.isVisible() || !comp.isEnabled() || !comp.isFocusable()) {
			return false;
		}

		// 清除最后部分
		this.deselectComponent();

		// 设定选中状态
		comp.setSelected(true);
		this.selectedComponent = comp;

		return true;
	}

	void setDesktop(LComponent comp) {
		if (comp.isContainer()) {
			LComponent[] child = ((LContainer) comp)._childs;
			for (int i = 0; i < child.length; i++) {
				this.setDesktop(child[i]);
			}
		}
		comp.setDesktop(this);
	}

	void setComponentStat(LComponent comp, boolean active) {
		if (this == Desktop.EMPTY_DESKTOP) {
			return;
		}

		if (!active) {
			if (this.hoverComponent == comp) {
				this.processTouchMotionEvent();
			}

			if (this.selectedComponent == comp) {
				this.deselectComponent();
			}

			this.clickComponent[0] = null;

			if (this.modal == comp) {
				this.modal = null;
			}

		} else {
			this.processTouchMotionEvent();
		}

		if (comp.isContainer()) {
			LComponent[] components = ((LContainer) comp)._childs;
			int size = ((LContainer) comp).getComponentCount();
			for (int i = 0; i < size; i++) {
				this.setComponentStat(components[i], active);
			}
		}
	}

	void clearComponentsStat(LComponent[] comp) {
		if (this == Desktop.EMPTY_DESKTOP) {
			return;
		}

		boolean checkTouchMotion = false;
		for (int i = 0; i < comp.length; i++) {
			if (this.hoverComponent == comp[i]) {
				checkTouchMotion = true;
			}

			if (this.selectedComponent == comp[i]) {
				this.deselectComponent();
			}

			this.clickComponent[0] = null;

		}

		if (checkTouchMotion) {
			this.processTouchMotionEvent();
		}
	}

	public void validateUI() {
		this.validateContainer(this.contentPane);
	}

	final void validateContainer(LContainer container) {
		LComponent[] components = container._childs;
		int size = container.getComponentCount();
		for (int i = 0; i < size; i++) {
			if (components[i].isContainer()) {
				this.validateContainer((LContainer) components[i]);
			}
		}
	}

	public LComponent getTopComponent() {
		LComponent[] components = contentPane._childs;
		int size = components.length;
		if (size > 1) {
			return components[1];
		}
		return null;
	}

	public LComponent getBottomComponent() {
		LComponent[] components = contentPane._childs;
		int size = components.length;
		if (size > 0) {
			return components[size - 1];
		}
		return null;
	}

	public LLayer getTopLayer() {
		LComponent[] components = contentPane._childs;
		int size = components.length;
		for (int i = 0; i < size; i++) {
			LComponent comp = components[i];
			if (comp instanceof LLayer) {
				return (LLayer) comp;
			}
		}
		return null;
	}

	public LLayer getBottomLayer() {
		LComponent[] components = contentPane._childs;
		int size = components.length;
		for (int i = size; i > 0; i--) {
			LComponent comp = components[i - 1];
			if (comp instanceof LLayer) {
				return (LLayer) comp;
			}
		}
		return null;
	}

	public UIControls createUIControls() {
		UIControls controls = null;
		if (contentPane != null && contentPane._childs != null) {
			controls = new UIControls(contentPane._childs);
		}
		return controls;
	}

	public UIControls findUINamesToUIControls(String... uiName) {
		UIControls controls = null;
		if (contentPane != null && contentPane._childs != null) {
			TArray<LComponent> comps = contentPane.findUINames(uiName);
			controls = new UIControls(comps);
		}
		return controls;
	}

	public UIControls findNotUINamesToUIControls(String... uiName) {
		UIControls controls = null;
		if (contentPane != null && contentPane._childs != null) {
			TArray<LComponent> comps = contentPane.findNotUINames(uiName);
			controls = new UIControls(comps);
		}
		return controls;
	}

	public UIControls findNamesToUIControls(String... name) {
		UIControls controls = null;
		if (contentPane != null && contentPane._childs != null) {
			TArray<LComponent> comps = contentPane.findNames(name);
			controls = new UIControls(comps);
		}
		return controls;
	}

	public UIControls findNotNamesToUIControls(String... name) {
		UIControls controls = null;
		if (contentPane != null && contentPane._childs != null) {
			TArray<LComponent> comps = contentPane.findNotNames(name);
			controls = new UIControls(comps);
		}
		return controls;
	}

	public UIControls findTagsToUIControls(Object... o) {
		UIControls controls = null;
		if (contentPane != null && contentPane._childs != null) {
			TArray<LComponent> comps = contentPane.findTags(o);
			controls = new UIControls(comps);
		}
		return controls;
	}

	public UIControls findNotTagsToUIControls(Object... o) {
		UIControls controls = null;
		if (contentPane != null && contentPane._childs != null) {
			TArray<LComponent> comps = contentPane.findNotTags(o);
			controls = new UIControls(comps);
		}
		return controls;
	}

	public float getWidth() {
		return this.contentPane.getWidth();
	}

	public float getHeight() {
		return this.contentPane.getHeight();
	}

	public void setSize(int w, int h) {
		this.contentPane.setSize(w, h);
	}

	public LContainer getContentPane() {
		return this.contentPane;
	}

	public void setContentPane(LContainer pane) {
		pane.setBounds(0, 0, (int) this.getWidth(), (int) this.getHeight());
		this.contentPane = pane;
		this.setDesktop(this.contentPane);
	}

	public LComponent getHoverComponent() {
		return this.hoverComponent;
	}

	public LComponent getSelectedComponent() {
		return this.selectedComponent;
	}

	public LComponent getModal() {
		return this.modal;
	}

	public void setModal(LComponent comp) {
		if (comp != null && !comp.isVisible()) {
			throw new RuntimeException(
					"Can't set invisible component as modal component!");
		}
		this.modal = comp;
	}

	public boolean contains(LComponent comp) {
		return contentPane.contains(comp);
	}

	public LComponent get() {
		return this.contentPane.get();
	}

	public void removeAll() {
		clear();
	}

	public void clear() {
		if (contentPane != null) {
			contentPane.clear();
		}
	}

	public Screen getInput() {
		return input;
	}

	@Override
	public void close() {
		if (contentPane != null) {
			contentPane.close();
		}
		DESKTOP_CACHE.remove(this);
	}

}
