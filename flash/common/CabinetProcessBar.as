package common {
	
	import flash.display.MovieClip;
	import flashx.textLayout.formats.Float;
	
	
	public class CabinetProcessBar extends MovieClip {
		
		public function setBarValue (value0:int,value1:int,value2:int,value3:int,totalValue:int) {
			var resultValue:int = 0.0;
			var xValue:int = 0.0;
			resultValue = value0/totalValue*100;
			this.DispProcess0.width=resultValue;
			this.DispProcess0.x = xValue;
			resultValue = value1/totalValue*100;
			this.DispProcess1.width=resultValue;
			this.DispProcess1.x = xValue+value0/totalValue*100;
			resultValue = value2/totalValue*100;
			this.DispProcess2.width=resultValue;
			this.DispProcess2.x = xValue+value0/totalValue*100+value1/totalValue*100;
			resultValue = value3/totalValue*100;
			this.DispProcess3.width=resultValue;
			this.DispProcess3.x = xValue+value0/totalValue*100+value1/totalValue*100+value2/totalValue*100;
			trace(this.DispProcess0.width);
			trace(this.DispProcess1.width);
			trace(this.DispProcess2.width);
			trace(this.DispProcess3.width);
		}
		
		public function setValue (value:int) { 
			this.DispProcess0.width=value; 
			trace(this.DispProcess0.width);
		}
		
		public function CabinetProcessBar() {
			// constructor code
			trace("YGCYProcessBar");
		}
	}
	
}
