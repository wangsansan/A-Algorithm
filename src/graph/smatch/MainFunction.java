package graph.smatch;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class MainFunction {
	

	@SuppressWarnings("unchecked")
	public void process(State start) {
		if (start == null || start.getValues() == null)
			return;
		State state = null;
		List<State> open = new LinkedList<>();
		// List<State> closed = new LinkedList<>();
		Stack<State> closed = new Stack<>();//
		open.add(start);
		while (open.size() != 0) {

			Collections.sort(open);
			state = open.get(0);
			if (!closed.isEmpty()) {
				while (state.getGn() != closed.get(closed.size() - 1).getGn() + 1) {
					closed.pop();
				}
			}
			closed.add(state);
			if (state.getHn() == 0) {
				Stack<State> finalPath = new Stack<>();
				while (state.getFather() != null) {
					finalPath.push(state);
					state = state.getFather();
				}
				finalPath.push(state);

				while (finalPath.size() != 0)
					finalPath.pop().showState();
				return;
			}
			open.remove(0);

			int posIofBlank = 0;
			int posJofBlank = 0;
			for (int i = 0; i < state.getValues().length; i++)
				for (int j = 0; j < state.getValues()[0].length; j++) {
					if (state.getValues()[i][j] == 0) {
						posIofBlank = i;
						posJofBlank = j;
						break;
					}
				}

			State downState = null;
			State upState = null;
			State leftState = null;
			State rightState = null;
			if (posJofBlank > 0) {
				rightState = state.clone();
				rightState.getValues()[posIofBlank][posJofBlank] = rightState
						.getValues()[posIofBlank][posJofBlank - 1];
				rightState.getValues()[posIofBlank][posJofBlank - 1] = 0;
				rightState.setGn(state.getGn() + 1);
				rightState.setFather(state);
			}
			if (posIofBlank > 0) {
				downState = state.clone();
				downState.getValues()[posIofBlank][posJofBlank] = downState
						.getValues()[posIofBlank - 1][posJofBlank];
				downState.getValues()[posIofBlank - 1][posJofBlank] = 0;
				//
				downState.setGn(state.getGn() + 1);
				downState.setFather(state);
			}

			if (posJofBlank < state.getValues()[0].length - 1) {
				leftState = state.clone();
				leftState.getValues()[posIofBlank][posJofBlank] = leftState
						.getValues()[posIofBlank][posJofBlank + 1];
				leftState.getValues()[posIofBlank][posJofBlank + 1] = 0;
				leftState.setGn(state.getGn() + 1);
				leftState.setFather(state);
			}
			if (posIofBlank < state.getValues().length - 1) {
				upState = state.clone();
				upState.getValues()[posIofBlank][posJofBlank] = upState
						.getValues()[posIofBlank + 1][posJofBlank];
				upState.getValues()[posIofBlank + 1][posJofBlank] = 0;
				upState.setGn(state.getGn() + 1);
				upState.setFather(state);
			}
			if (upState != null && !open.contains(upState)
					&& !closed.contains(upState))
				open.add(upState);
			if (downState != null && !open.contains(downState)
					&& !closed.contains(downState))
				open.add(downState);
			if (leftState != null && !open.contains(leftState)
					&& !closed.contains(leftState))
				open.add(leftState);
			if (rightState != null && !open.contains(rightState)
					&& !closed.contains(rightState))
				open.add(rightState);
		}
	}

	public static void main(String[] args) {
		MainFunction main = new MainFunction();
		State start = new State();
		int[][] values = { { 2, 1, 6 }, { 4, 0, 8 }, { 7, 5, 3 } };
		start.setValues(values);
		start.setGn(0);
		main.process(start);
	}

}
