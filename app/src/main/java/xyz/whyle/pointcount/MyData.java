package xyz.whyle.pointcount;

public class MyData
{
	public static String group1;
	public static String group2;
	public static String group3;
	public static String time;
	public static String from;

	public MyData()
	{}

	public static void setGroup1(String group1)
	{
		MyData.group1 = String.valueOf(group1);
	}

	public static String getGroup1()
	{
		return group1;
	}

	public static void setGroup2(String group2)
	{
		MyData.group2 = String.valueOf(group2);
	}

	public static String getGroup2()
	{
		return group2;
	}

	public static void setGroup3(String group3)
	{
		MyData.group3 = String.valueOf(group3);
	}

	public static String getGroup3()
	{
		return group3;
	}

	public static void setTime(String time)
	{
		MyData.time = time;
	}

	public static String getTime()
	{
		return time;
	}

	public static void setFrom(String from)
	{
		MyData.from = from;
	}

	public static String getFrom()
	{
		return from;
	}}
