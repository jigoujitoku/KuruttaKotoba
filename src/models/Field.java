package models;

import logic.RandomNum;

public class Field {
	boolean occupied;
	int row;
	int column;
	public String word;
	int counter;

	public Field(int row, int column, boolean occupied, String word, int counter) {
		this.occupied = occupied;
		this.row = row;
		this.column = column;
		this.word = word;
	}

	public boolean isOccupied() {
		return occupied;
	}

	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public String getWord() {
		return word;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public String getRandomLetter() {
		int position = RandomNum.getRandomInt(6);
		char character = word.charAt(position);
		String letter = "" + character;
		return letter;
	}

	public void setWord(String word) {
		this.word = word;
	}

}
