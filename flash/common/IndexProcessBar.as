package common  {
	
	import flash.display.MovieClip;
	import flashx.textLayout.formats.Float;
	
	
	public class IndexProcessBar extends MovieClip {
		
		public function setBarValue (value:int,totalValue:int) {
			var resultValue:int = 0.0;
			resultValue = value/totalValue*100;
			this.DispProcess.width=resultValue; 
			trace(this.DispProcess.width);
		}
		
		public function setValue (value:int) { 
			this.DispProcess.width=value; 
			trace(this.DispProcess.width);
		}
		
		public function IndexProcessBar() {
			// constructor code
			//trace("MyProcessBar");
		}
	}
	
}
