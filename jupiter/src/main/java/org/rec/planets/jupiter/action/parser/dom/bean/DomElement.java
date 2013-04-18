package org.rec.planets.jupiter.action.parser.dom.bean;

/**
 * Dom元素
 * 
 * @author rec
 * 
 */
public class DomElement {
	/**
	 * 选择器
	 */
	private String selector;
	/**
	 * 选完之后是一个数组,这里给出下标的集合,如果给出null表示取所有的元素
	 */
	private int[] indexes;
	/**
	 * 属性名,如果给出则表示取属性值,否则表示取value值
	 */
	private String attrabute;
	
	public DomElement(String selector, int[] indexes, String attrabute) {
		this.selector = selector;
		this.indexes = indexes;
		this.attrabute = attrabute;
	}
	
	public DomElement(String selector, String attrabute) {
		this(selector, null, attrabute);
	}

	public DomElement(String selector) {
		this(selector, null);
	}

	public String getSelector() {
		return selector;
	}

	public int[] getIndexes() {
		return indexes;
	}

	public String getAttrabute() {
		return attrabute;
	}
}
