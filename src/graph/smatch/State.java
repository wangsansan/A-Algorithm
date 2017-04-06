package graph.smatch;

public class State implements Comparable {

	private int[][] values = new int[3][3];
	private int hn = 0;
	private int gn = 0;
	private int fn = 0;
	State father;
	
	public State getFather() {
		return father;
	}

	public void setFather(State father) {
		this.father = father;
	}

	public int getFn() {
		return fn;
	}

	public void setFn(int fn) {
		this.fn = fn;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj instanceof State) {
			State o = (State) obj;
			boolean equals = true;
			for (int i = 0; i < values.length; i++)
				for (int j = 0; j < values[0].length; j++)
					if (values[i][j] != o.getValues()[i][j]) {
						equals = false;
						break;
					}

			return equals;
		}

		return false;
	}

	/*
	 * public void computeHn1() { for (int i = 0; i < values.length; i++) for
	 * (int j = 0; j < values[0].length; j++) { int posValue = values[i][j];
	 * posValue--; int posI = posValue / 3; int posJ = posValue % 3; hn +=
	 * Math.abs(posI - i) + Math.abs(posJ - j); } }
	 */

	public void computeHn() {
		int[][] values1 = { { 1, 2, 3 }, { 8, 0, 4 }, { 7, 6, 5 } };
		for (int i = 0; i < values.length; i++)
			for (int j = 0; j < values[0].length; j++) {
				int posValue = values[i][j];
				if (posValue == 0)
					continue;
				boolean flag = false;
				for (int m = 0; m < values1.length; m++) {
					for (int n = 0; n < values1[0].length; n++) {
						if (posValue == values1[m][n]) {
							hn += Math.abs(i - m) + Math.abs(j - n);
							flag = true;
							break;
						}
					}
					if (flag)
						break;
				}
			}
	}

	public void computeHn1() {
		int sn = 0;
		computeHn();
		sn = computeSn();
		hn += 3 * sn;
	}

	public int computeSn() {
		int sn = 0;
		int mid = 0;
		int nonMid = 0;
		// 中心位置有将牌时估分为1，无将牌时估分为0
		if (values[values.length / 2][values[0].length / 2] != 0)
			mid = 1;
		// 顺某一方向检查，若某一将牌后面跟的后继者和目标状态相应将牌的顺序相比不一致时，则
		// 该将牌估分为2，一致时估分为0
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < values[0].length; j++) {
				int posValue = values[i][j];
				if (posValue == 0)
					continue;
				// 第一行非最后一个点
				if (i == 0) {
					if (j != values.length - 1) {
						if (values[i][j] == values.length * values[0].length
								- 1) {
							if (values[i][j + 1] != 1)
								nonMid += 2;
						} else {
							if (values[i][j + 1] != posValue + 1)
								nonMid += 2;
						}
					}
				}
				// 最后一列非最后一个
				if (j == values[0].length - 1) {
					if (i != values.length - 1) {
						if (values[i][j] == values.length * values[0].length
								- 1) {
							if (values[i + 1][j] != 1)
								nonMid += 2;
						} else {
							if (values[i + 1][j] != posValue + 1)
								nonMid += 2;
						}
					}
				}
				//最后一行非第一个
				if(i == values.length - 1){
					if(j != 0){
						if (values[i][j] == values.length * values[0].length
								- 1) {
							if(values[i][j - 1] != 1)
								nonMid += 2;
						}else{
							if(values[i][j - 1] != posValue + 1)
								nonMid += 2;
						}
					}
				}
				//第一列非第一个
				if(j == 0){
					if(i != 0){
						if (values[i][j] == values.length * values[0].length
								- 1){
							if(values[i - 1][j] != 1)
								nonMid += 2;
						}else{
							if(values[i - 1][j] != posValue + 1)
								nonMid += 2;
						}
					}
				}
			}
		}
		sn = mid + nonMid;
		return sn;
	}

	public int getHn() {
		return hn;
	}

	public void setHn(int hn) {
		this.hn = hn;
	}

	public int[][] getValues() {
		return values;
	}

	public void setValues(int[][] values) {
		this.values = values;
	}

	public int getGn() {
		return gn;
	}

	public void computeFn() {
		fn = gn + hn;
	}

	public void setGn(int gn) {
		this.gn = gn;
		computeHn1();
		computeFn();
	}

	public void showState() {
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < values[0].length; j++)
				System.out.print(values[i][j] + " ");
			System.out.println();
		}
		System.out.println("gn:" + gn + ";hn:" + hn + ";fn:" + fn);
		System.out.println("=======================");
	}

	@Override
	protected State clone() {
		// TODO Auto-generated method stub
		State state = new State();
		int[][] cloneValues = new int[values.length][values[0].length];
		for (int i = 0; i < values.length; i++)
			for (int j = 0; j < values[0].length; j++) {
				cloneValues[i][j] = values[i][j];
			}
		state.setValues(cloneValues);
		return state;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if (o instanceof State) {
			State state = (State) o;
			return this.getFn() - state.getFn();
		}
		return 0;
	}

	public static void main(String[] args) {
		State finalState = new State();
		int[][] values = { { 2, 0, 6 }, { 4, 1, 8 }, { 7, 5, 3 } };
		finalState.setValues(values);
		finalState.computeHn1();
		System.out.println(finalState.getHn());
	}

}
