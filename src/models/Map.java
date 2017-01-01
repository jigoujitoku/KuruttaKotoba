package models;

import java.util.ArrayList;
import java.util.List;

import logic.RandomNum;

public class Map {
	public ArrayList<Field> fields = new ArrayList<Field>();
	int fieldNum;
	int position;
	public Character character;
	String word;
	List<Integer> usedDices = new ArrayList<Integer>();

	public Map(int fieldNum, List<String> dictionary) {
		this.fieldNum = fieldNum;
		for (int i = 0; i < fieldNum; i++) {
			for (int j = 0; j < fieldNum; j++) {
				do {
					position = RandomNum.getRandomInt(fieldNum * fieldNum);
					word = dictionary.get(position);
				} while (usedDices.contains(position));
				usedDices.add(position);
				Field field = new Field(i, j, false, word, 0);
				fields.add(field);
			}
		}

	}

	public Field fieldFind(int row, int column) {
		for (Field field : fields) {
			if (field.getRow() == row && field.getColumn() == column)
				return field;
		}
		return null;
	}

	public ArrayList<Field> getFields() {
		return fields;
	}

	public int getFieldNum() {
		return fieldNum;
	}

	public Character getCharacter() {
		return character;
	}

}
