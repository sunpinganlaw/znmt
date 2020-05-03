package 
{

	import flash.display.MovieClip;
	import flash.external.ExternalInterface;
	import flash.events.Event;
	import flash.net.URLLoader;
	import flash.net.URLRequest;
	import com.adobe.serialization.json.JSON;
	import common.HashMap;
	//import common.MyProcessBar;
	import common.Nept;
	import flash.events.MouseEvent;
	import fl.controls.ComboBox;
	import flash.text.TextFormat;
	import flash.utils.Timer;   
    import flash.events.TimerEvent; 

	public class base_kyzy extends Nept
	{  
	 
		// constructor code  
		public function base_kyzy()
		{
			trace("子类初始化"); 
			
			hashMap = new HashMap  ;
			hashMap.put("0","white");
			hashMap.put("1","red");
			hashMap.put("2","yellow");
			hashMap.put("3","green");
			hashMap.put("9","white");
		}
	}

}