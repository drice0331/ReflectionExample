import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Main {

	public static void execute(String clazz, String method, String [] parameters, Object [] args) {
		try {
			Class<?> c = Class.forName(clazz);
			Class<?>[] params = null;
			if(parameters != null) {
				params = new Class[parameters.length];
			
				for(int i = 0; i < parameters.length; i++) {
					if(parameters[i].equals(String.class.toString())) {
						params[i] = String.class;
					} else {
						params[i] = Class.forName(parameters[i]);
					}
				}
			}
			Method m = c.getDeclaredMethod(method, params);
			if(!Modifier.isStatic(m.getModifiers())) {
				Object obj = c.newInstance();
				m.invoke(obj, args);
			} else {
				m.invoke(null, args);
			}
				

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void execute2(String clazz, String method, Class<?>[] parameters, Object [] args) {
		try {
			Class<?> c = Class.forName(clazz);
			Method m = c.getDeclaredMethod(method, parameters);
			if(!Modifier.isStatic(m.getModifiers())) {
				Object obj = c.newInstance();
				m.invoke(obj, args);
			} else {
				m.invoke(null, args);
			}
				

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void execute(Class clazz, Method method, Class [] parameters, Object [] args) {
		try { 
			if(Modifier.isStatic(method.getModifiers())) {
				method.invoke(null, args);
			} else {
				Object o = clazz.newInstance();
				method.invoke(o, args);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void assignValue(Object val1, Object val2) {
		Class c1 = val1.getClass();
		Class c2 = val2.getClass();
	}
	
	/**
	 * Currently sets object from json object with reflection when key value pairs are only primatives
	 * @param to
	 * @param from
	 */
	public static void assignValue(Object to, JSONObject from)
 {
		System.out.println(to.getClass().toString());
		try {
			System.out.println(from.names());
			JSONArray arr = from.names();
			for(int i = 0; i < arr.length(); i++) {
				System.out.println(i + " " + from.get(arr.get(i).toString()));
				
				JSONObject jsonobj = from.optJSONObject(arr.getString(i));
				
				if(jsonobj == null) {
					Object o = from.get(arr.get(i).toString());
					String key = arr.optString(i);
					Class c = to.getClass();
					Field f = c.getDeclaredField(arr.getString(i));
					f.set(to, o);
					
				} else {
					System.out.println(i + " is JSONObject");
					Class c = to.getClass();
					Field f = c.getDeclaredField(arr.getString(i));
					System.out.println(f.toGenericString());
					Object o = f.getDeclaringClass().newInstance();
					//TODO - recursive for object here!
					assignValue(o, jsonobj);
					
					f.set(to, o);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private static Object assignValue(Class clazz, JSONObject data) {
		
		Object obj = null;
		System.out.println(clazz.toString());
		try {
			obj = clazz.newInstance();
			System.out.println(data.names());
			JSONArray arr = data.names();
			for(int i = 0; i < arr.length(); i++) {
				System.out.println(i + " " + data.get(arr.get(i).toString()));
				
				JSONObject jsonobj = data.optJSONObject(arr.getString(i));
				
				Field f = clazz.getDeclaredField(arr.getString(i));
				if(jsonobj == null) {
					Object o = data.get(arr.get(i).toString());
					f.set(obj, o);
					
				} else {
					System.out.println(i + " is JSONObject");
					System.out.println(f.toGenericString());
					Object o = f.getDeclaringClass().newInstance();
					//recursive for object here!
					assignValue(o, jsonobj);
					f.set(obj, o);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return obj;
	}
	
	private static JSONObject assignValueToJSON(Object object) {
		Field [] fields = object.getClass().getDeclaredFields();
		JSONObject json = new JSONObject();
		try {
			for(Field field:fields) {
				
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		Person p = new Person("Dave", 15, 12343214);
		
		execute("Person", "yell", null, null);
		execute("Person", "yell", new String[]{String.class.toString()}, new Object[]{"Hush"});
		execute2("Person", "yell", new Class[]{String.class}, new Object[]{"Shush"});
		
		new JSONObject();
		Map <String, Object> map = new HashMap<String, Object>();
		Map <String, Object> map2 = new HashMap<String, Object>();
		map.put("name", "Dave");
		map.put("age", 14);

		//map.put("object3", new JSONObject(map2));
		map.put("ssNumber", 123534);
		map2.put("name", "Doug");
		map2.put("age", 16);
		map2.put("ssNumber", 543134);
		map.put("friend", new JSONObject(map2));
		
		JSONObject dummy = new JSONObject(map);
		try {
			System.out.println(dummy.toString(1));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assignValue(p, dummy);
		Object c = assignValue(Person.class, dummy);
		Person b = (Person) c;
		System.out.println(b.age + " " + b.name + " " + b.ssNumber + " " + b.friend.name + " " + b.friend.age + " " + b.friend.ssNumber);
	}
	


}
