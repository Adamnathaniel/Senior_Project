package data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Stat
{
	@JsonProperty("@category")
	public String category;
	@JsonProperty("@abbreviation")
	public String abbreviation;
	
	@JsonProperty("#text")
	public String text;
	
	private double a, b, c;
	public String toString()
	{
		return abbreviation + ": " + text + "\n";
	}
	
	public void setCategory(String str)
	{
		category = str;
	}
	public void setAbbreviation(String str)
	{
		abbreviation = str;
	}
	public void setText(String str)
	{
		text = str;
	}
	
	public void setCoefs(double one, double two, double three)
	{
		a = one;
		b = two;
		c = three;
	}
	
	public double getA()
	{
		return a;
	}
	
	public double getB()
	{
		return b;
	}
	public double getC()
	{
		return c;
	}
}
