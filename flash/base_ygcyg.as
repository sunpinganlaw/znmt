package 
{

	import flash.display.MovieClip;
	import flash.external.ExternalInterface;
	import flash.events.Event;
	import flash.net.URLLoader;
	import flash.net.URLRequest;
	import com.adobe.serialization.json.JSON;
	import common.HashMap; 
	import common.CabinetProcessBar;
	import common.Nept;
	import flash.events.MouseEvent;
	import fl.controls.ComboBox;
	import flash.text.TextFormat;
	import flash.utils.Timer;   
    import flash.events.TimerEvent; 

	public class base_ygcyg extends Nept
	{  
	 
		// constructor code  
		public function base_ygcyg()
		{
			trace("子类初始化"); 
			
			hashMap = new HashMap  ;
			hashMap.put("0","white");
			hashMap.put("1","red");
			hashMap.put("2","yellow");
			hashMap.put("3","green");
			hashMap.put("9","white");
			
			
			cabinetBar1.setBarValue(1,1,2,4,8);
			cabinetBar1.gotoAndStop(2); 
			cabinetBar2.setBarValue(10,10,25,40,100);
			cabinetBar2.gotoAndStop(2); 
			cabinetBar3.setBarValue(20,10,25,40,100);
			cabinetBar3.gotoAndStop(2); 
			cabinetBar5.setBarValue(5,10,25,40,100);
			cabinetBar5.gotoAndStop(2); 
			cabinetBar6.setBarValue(6,6,25,40,100);
			cabinetBar6.gotoAndStop(2); 
		}
	}

}