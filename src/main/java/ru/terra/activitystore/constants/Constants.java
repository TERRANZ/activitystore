package ru.terra.activitystore.constants;

import java.util.HashMap;
import java.util.Map;

public class Constants
{
	private static Constants instance = new Constants();
	private HashMap<String, Integer> cellTypes = new HashMap<String, Integer>();

	public static Constants getConstants()
	{
		return instance;
	}

	private Constants()
	{
		cellTypes.put("Целые числа", 0);
		cellTypes.put("Дробные числа", 1);
		cellTypes.put("Строка", 2);
		cellTypes.put("Текст", 3);
		cellTypes.put("Список", 4);
		cellTypes.put("Дата", 5);
	}

	public Map<String, Integer> getCellTypes()
	{
		return cellTypes;
	}
}
