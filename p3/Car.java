//Author: Matt Stropkey
//car class for project 3

public class Car
{
	private String Vin;
	private String Brand;
	private String Model;
	private double Price;
	private int Mileage;
	private String Color;
	private int MileageIndex;
	private int PriceIndex;
	private int MakeModMilesIndex; 
	private int MakeModPriceIndex;

	public Car()
	{
		Vin = "";
		Brand = "";
		Model = "";
		Price = 0;
		Mileage = 0;
		Color = "";
	}
	
	public Car(String num, String make, String model, double price, int miles, String color)
	{

		setVIN(num);
		setMake(make);
		setModel(model);
		setPrice(price);
		setMiles(miles);
		setColor(color);
		
		Vin = getVIN();
		Brand = getMake();
		Model = getModel();
		Price = getPrice();
		Mileage = getMiles();
		Color = getColor();
		MileageIndex = 0;
		PriceIndex = 0;
	}
	
	//sets a cars Vin number
	public void setVIN(String num)
	{
		Vin = num;
	}
	
	//returns a cars Vin number
	public String getVIN()
	{
		return Vin;
	}
	
	//Sets a cars make
	public void setMake(String make)
	{
		Brand = make;
	}
	
	//returns a cars make
	public String getMake()
	{
		return Brand;
	}

	//sets a cars model
	public void setModel(String model)
	{
		Model = model;
	}
	
	//returns a cars model
	public String getModel()
	{
		return Model;
	}
	
	//sets a cars price
	public void setPrice(double value)
	{
		Price = value;
	}
	
	//returns a cars price
	public double getPrice()
	{
		return Price;
	}
	
	//sets a cars mileage
	public void setMiles(int miles)
	{
		Mileage = miles;
	}
	
	//returns a cars mileage
	public int getMiles()
	{
		return Mileage;
	}
	
	//sets a cars color
	public void setColor(String color)
	{
		Color = color;
	} 
	
	//returns a cars color
	public String getColor()
	{
		return Color;
	}
	
	//sets the index value of the car in the mileage heap
	public void setMilesIndex(int i)
	{
		MileageIndex = i;
	}
	 
	//sets the index value of the car in the price heap
	public void setPriceIndex(int i)
	{
		PriceIndex = i;
	}
	
	//returns the index value of the car in the mileage heap
	public int getMilesIndex()
	{
		return MileageIndex; 
	} 
	
	//returns the index value of the car in the price heap
	public int getPriceIndex() 
	{
		return PriceIndex;
	}
	
	//sets the index value of the car in the make/model Price heap
	public void setMakeModPriceIndex(int i)
	{
		MakeModPriceIndex = i;
	}
	
	//returns the index value of the car in the make/model price heap
	public int getMakeModPriceIndex()
	{
		return MakeModPriceIndex;
	}
	
	//sets the index value of the car in the make/model miles heap
	public void setMakeModMilesIndex(int i)
	{
		MakeModMilesIndex = i;
	}
	
	//returns the index value of the car in the make/model price heap
	public int getMakeModMilesIndex()
	{
		return MakeModMilesIndex;
	}
	
}
	












