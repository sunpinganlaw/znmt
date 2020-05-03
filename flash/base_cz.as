package 
{

	import flash.display.MovieClip;
	import flash.external.ExternalInterface;
	import flash.events.Event;
	import flash.net.URLLoader;
	import flash.net.URLRequest;
	import com.adobe.serialization.json.JSON;
	import common.HashMap;
	import common.MyProcessBar;
	import common.Nept;
	import flash.events.MouseEvent;
	import fl.controls.ComboBox;
	import flash.text.TextFormat;
	import flash.utils.Timer;   
    import flash.events.TimerEvent; 

	public class base_cz extends Nept
	{  
	
		private function setFlashTitleCallBack(flashTile:String )
		{
			 flashTitle.text = flashTile;
			 return "TEST";
		}
		// constructor code  
		public function base_cz()
		{
			trace("子类初始化"); 
			
			lightMap = new HashMap  ;
			lightMap.put("0","red");
			lightMap.put("1","green"); 
			
			switchMap = new HashMap  ;
			switchMap.put("0","close");
			switchMap.put("1","open");  
		}
	}

}