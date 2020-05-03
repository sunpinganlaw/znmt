package  {
	
	import flash.display.MovieClip;
	import flash.external.ExternalInterface;
	import flash.events.Event;
	import flash.net.URLLoader;
	import flash.net.URLRequest;
	import com.adobe.serialization.json.JSON;
	import common.HashMap;
	import common.IndexProcessBar;
	import common.Nept;
	import flash.events.MouseEvent;
	import fl.controls.ComboBox;
	import flash.text.TextFormat;
	import flash.utils.Timer;   
    import flash.events.TimerEvent; 
	
	
	public class cy_index extends Nept {
		
		
		// constructor code  
		public function cy_index()
		{
			trace("子类初始化");
			hashMap = new HashMap  ;
			hashMap.put("0","white");
			hashMap.put("1","red");
			hashMap.put("2","yellow");
			hashMap.put("3","green");
			hashMap.put("9","white");
			
			lightMap = new HashMap  ;
			lightMap.put("0","red");
			lightMap.put("1","green"); 
			
			switchMap = new HashMap  ;
			switchMap.put("0","close");
			switchMap.put("1","open"); 
			
			teeBeltMap = new HashMap  ;
			teeBeltMap.put("0","white");
			teeBeltMap.put("1","red");  
			teeBeltMap.put("2","red");  
		}
	}
	
}
