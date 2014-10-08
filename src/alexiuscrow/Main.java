package alexiuscrow;

import java.io.Console;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.google.common.collect.Sets;

public class Main {

	public static void main(String[] args) {
		boolean goOut = true;
		while(goOut){
			System.out.println("Выберите действие:");
			System.out.println("1 - Найти координаты по адресу");
			System.out.println("2 - Найти адрес по координатам");
			System.out.println("3 - Найти ближайший адрес");
			System.out.println("или любой другой символ что бы выйти.");
			System.out.println();
			
			String inputStr = printAndWrite("");
			
			switch(Integer.parseInt(inputStr)){
			case 1: findLatLng(); break;
			case 2: findAddress(); break;
			case 3: findNearestPoint(); break;
			default: goOut = false;
			} 
		}
		System.out.println("Пока!");
	}

	private static void findLatLng(){
		String address = printAndWrite("Укажите адрес: ");
		StringBuilder result = new StringBuilder(); 
		System.out.println("Результат: ");
		try {
			Map<String, Double> latlng = GeoCoding.getLatLng(address);
			result.append("Широта: ");
			result.append(latlng.get("lat"));
			result.append(", Долгота: ");
			result.append(latlng.get("lng"));
			GeoCoding.getLatLng(address);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(result.toString());
		System.out.println();
	}
	
	private static void findAddress() {
		Double lat =  Double.parseDouble(printAndWrite("Введите широту: "));
		Double lng =  Double.parseDouble(printAndWrite("Введите долготу: "));
		StringBuilder result = new StringBuilder();
		result.append("Результат: ");
		try {
			result.append(GeoCoding.getAddress(lat, lng));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(result.toString());
		System.out.println();
	}

	private static void findNearestPoint() {
		String[] origin = new String[1];
		
		origin[0] = printAndWrite("Введите адрес отправной точки: ");
		
		Set<String> setDestionations = Sets.newHashSet();
		
		boolean moreDestionations = true;
		int i = 0;
		while(moreDestionations){
			String str = printAndWrite("Введите адрес точки назначения #" + ++i +": ");
			setDestionations.add(str);
			
			str = printAndWrite("Желаете ввести еще адрес точки назначения? (y/n) ");
			if(!str.equals("y")){
				moreDestionations = false;
			}
		}
		
		String[] distionations = setDestionations.toArray(new String[setDestionations.size()]);
		RemotePoint nearestPoint = null;
		try {
			nearestPoint = DistanceMatrix.findNearestPoint(origin, distionations);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Адрес ближайшей точки: " + nearestPoint.getAddress());
		System.out.println("Растояние до  ближайшей точки: " + nearestPoint.getDistanceToPoint());
		System.out.println("Время в пути до ближайшей точки: " + nearestPoint.getTimeDuration());
		System.out.println();
	}
	
	private static String printAndWrite(String message){
		String result;
		
		Console console = null;
		console = System.console();
		Scanner in = null;
		if(console != null){
			result = console.readLine(message);
		}else{
			in = new Scanner(System.in, "UTF-8");
			System.out.print(message);
			result = in.nextLine();
			in.close();
		}
		
		return result;
	}

}
