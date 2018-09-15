package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;

/**
 * This class offers the implementation of a custom layout manager called
 * 'CalcLayout' used later in our own implementation of a calculator.
 * Constraints used in this layout are modeled by RCPosition model which
 * contains information about row and column of component's position. Layout
 * offers 5x7 positions total for storing components and this property cannot be
 * altered.
 * 
 * @author Marko Mesarić
 *
 */
public class CalcLayout implements LayoutManager2 {

	/**
	 * Constant which defines the first position where components can be stored
	 */
	private static final int MIN_ROW_COLUMN_NUMBER = 1;
	/**
	 * Constant which defines number of rows
	 */
	private static final int MAX_ROW_NUMBER = 5;
	/**
	 * Constant which defines number of columns
	 */
	private static final int MAX_COLUMN_NUMBER = 7;
	/**
	 * Constant which defines first component in this layout which differs from
	 * other components based on size
	 */
	private static final RCPosition FIRST_COMPONENT = new RCPosition(1, 1);

	/**
	 * Map used for storing components and their positions
	 */
	private Map<Component, RCPosition> components;
	/**
	 * Gap between different components in layout
	 */
	private int gap;

	/**
	 * Custom constructor which sets the gap to zero.
	 */
	public CalcLayout() {
		this(0);
	}

	/**
	 * Constructor used for setting gap and initializing this layout.
	 * 
	 * @throws CalcLayoutException
	 *             in case of invalid gap value.
	 * @param gap
	 *            desired gap between components
	 */
	public CalcLayout(int gap) {
		if (gap < 0) {
			throw new CalcLayoutException("Gap can't be less than zero.");
		}
		this.gap = gap;
		this.components = new HashMap<>();
	}

	/**
	 * Adds the given component with specified name to the layout. Constraints are
	 * required in this implementation of layout so this method does nothing.
	 * {@inheritDoc}
	 */
	@Override
	public void addLayoutComponent(String name, Component component) {

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeLayoutComponent(Component component) {
		Objects.requireNonNull(component, "Component to be removed can't be null.");

		components.remove(component);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addLayoutComponent(Component component, Object constraint) {
		Objects.requireNonNull(component, "Component can't be null.");
		Objects.requireNonNull(constraint, "Constraint can't be null.");

		RCPosition position;
		if (constraint.getClass() == RCPosition.class) {
			position = (RCPosition) constraint;
		} else if (constraint.getClass() == String.class) {
			position = parsePositionFromString(constraint);
		} else {
			throw new CalcLayoutException("Constraint has to be either RCPosition or String.");
		}

		checkPositionValidity(position);

		components.put(component, position);
	}

	/**
	 * Auxiliary method used for checking the validity of the given position.
	 * 
	 * @param position
	 *            position to be checked
	 */
	public void checkPositionValidity(RCPosition position) {
		if (position.getRow() > MAX_ROW_NUMBER || position.getRow() < MIN_ROW_COLUMN_NUMBER
				|| position.getColumn() > MAX_COLUMN_NUMBER || position.getColumn() < MIN_ROW_COLUMN_NUMBER) {
			throw new CalcLayoutException(
					"Invalid row/column specification. Minimum number of columns and rows is " + MIN_ROW_COLUMN_NUMBER
							+ "Maximum number of rows is " + MAX_ROW_NUMBER + " and columns: " + MAX_COLUMN_NUMBER);
		}

		if (position.getRow() == 1 && (position.getColumn() == 2 || position.getColumn() == 3
				|| position.getColumn() == 4 || position.getColumn() == 5)) {
			throw new CalcLayoutException("Constraints with positions (1,2) to (1,5) can't be used.");
		}

		if (components.containsValue(position)) {
			throw new CalcLayoutException("Can't add multiple components with the same constraint.");
		}
	}

	/**
	 * Auxiliary method used for parsing position from given object.
	 * 
	 * @param constraint
	 *            object to be parsed
	 * @return RCPosition of passed string
	 */
	private RCPosition parsePositionFromString(Object constraint) {
		String constraintString = String.valueOf(constraint);

		String[] constraintSplit = constraintString.split(",");
		if (constraintSplit.length != 2) {
			throw new CalcLayoutException("Constraint String must be in 'row,column' format.");
		}
		int row;
		int column;
		try {
			row = Integer.parseInt(constraintSplit[0]);
			column = Integer.parseInt(constraintSplit[1]);
		} catch (NumberFormatException e) {
			throw new CalcLayoutException("Invalid row or column value for RCPosition.");
		}

		return new RCPosition(row, column);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getLayoutAlignmentX(Container container) {
		return 0.5f;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float getLayoutAlignmentY(Container container) {
		return 0.5f;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void invalidateLayout(Container container) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void layoutContainer(Container container) {
		synchronized (container.getTreeLock()) {

			Insets insets = container.getInsets();
			int maxWidth = container.getWidth() - (insets.left + insets.right);
			int maxHeight = container.getHeight() - (insets.top + insets.bottom);

			int widthScaled = (maxWidth - (MAX_COLUMN_NUMBER - 1) * gap) / (MAX_COLUMN_NUMBER) + 1;
			int heightScaled = (maxHeight - (MAX_ROW_NUMBER - 1) * gap) / (MAX_ROW_NUMBER) + 1;

			for (Map.Entry<Component, RCPosition> entry : components.entrySet()) {
				Component component = entry.getKey();
				RCPosition position = entry.getValue();

				if (position.equals(FIRST_COMPONENT)) {
					component.setBounds(0, 0, widthScaled * 5 + gap * 4, heightScaled);

				} else {
					component.setBounds((position.getColumn() - 1) * (widthScaled + gap),
							(position.getRow() - 1) * (heightScaled + gap), widthScaled, heightScaled);
				}

			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension preferredLayoutSize(Container container) {
		return calculateDimension(container, (component) -> component.getPreferredSize());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension minimumLayoutSize(Container container) {
		return calculateDimension(container, (component) -> component.getMinimumSize());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension maximumLayoutSize(Container container) {
		return calculateDimension(container, (component) -> component.getMaximumSize());
	}

	/**
	 * Auxiliary method used for calculating maximum of different kind of dimensions
	 * (maximum, minimum and preferred size) based on given strategy.
	 * 
	 * @param container
	 *            parent container
	 * @param type
	 *            type of size to calculate
	 * @return calculated dimension
	 */
	public Dimension calculateDimension(Container container, SizeType type) {

		OptionalInt maxHeightOptional = components.keySet().stream().mapToInt(key -> type.getTypeOfSize(key).height)
				.max();

		Map<Component, RCPosition> copiedComponents = new HashMap<>(components);
		copiedComponents.values().remove(FIRST_COMPONENT);

		OptionalInt maxWidthOptional = copiedComponents.keySet().stream().mapToInt(key -> type.getTypeOfSize(key).width)
				.max();

		Optional<Component> startingComponent = components.entrySet().stream()
				.filter(entry -> entry.getValue().equals(FIRST_COMPONENT)).map(Map.Entry::getKey).findFirst();

		int maxHeight = 0;
		int maxWidth = 0;
		if (maxHeightOptional.isPresent()) {
			maxHeight = maxHeightOptional.getAsInt() * MAX_ROW_NUMBER + gap * (MAX_ROW_NUMBER - 1);
		}
		if (maxWidthOptional.isPresent()) {
			if (startingComponent.isPresent()) {
				maxWidth = type.getTypeOfSize(startingComponent.get()).width + gap * (MAX_COLUMN_NUMBER - 1)
						+ 2 * maxWidthOptional.getAsInt();
			} else {
				maxWidth = maxWidthOptional.getAsInt() * MAX_COLUMN_NUMBER + gap * (MAX_COLUMN_NUMBER - 1);
			}
		}

		Insets insets = container.getInsets();

		return new Dimension(insets.left + maxWidth + insets.right, insets.top + maxHeight + insets.bottom);

	}
}
