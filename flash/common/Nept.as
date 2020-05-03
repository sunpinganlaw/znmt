package  common
{

	import flash.display.MovieClip;
	import flash.external.ExternalInterface;
	import flash.events.Event;
	import flash.net.URLLoader;
	import flash.net.URLRequest;
	import com.adobe.serialization.json.JSON;
	import common.HashMap;
	import common.IndexProcessBar;
	import common.CabinetProcessBar;
	import flash.events.MouseEvent;
	import fl.controls.ComboBox;
	import flash.text.TextFormat;
	import flash.utils.Timer;   
    import flash.events.TimerEvent; 

	public class Nept extends MovieClip
	{

		//测点集
		private var measurePoints:Array;

        //用于存放影片剪辑的颜色转
		protected var hashMap:HashMap;
		
		//用于存放灯的颜色
		protected var lightMap:HashMap;

        //用于存放开关状态
		protected var switchMap:HashMap; 
		
		//用于存放三通皮带
		protected var teeBeltMap:HashMap; 
		
		//测点缓存
		private var cacheMap:HashMap; 
		
		//翻译内容测点
		protected var translateMap1:HashMap;
		protected var translateMap2:HashMap;
		protected var translateMap3:HashMap;

		//下来命令菜单
		private var myComboBox:ComboBox;

		//得到json配置
		private function decodeJSON(evt:Event):void
		{
		  var returnValue:String = "SUCCESS";	
		  try 
		   {	 
			measurePoints = com.adobe.serialization.json.JSON.decode(URLLoader(evt.target).data);//在这里,就可以通过操作数组的方式 使用JSon数据了 
			/*
			hashMap = new HashMap  ;
			hashMap.put("0","white");
			hashMap.put("1","red");
			hashMap.put("2","yellow");
			hashMap.put("3","green");
			hashMap.put("9","white");
			*/
			//通过测点配置，初始化页面各元素（目前：主要是点击和右键事件初始化）;
			for (var i = 0; i < measurePoints.length; i++)
			{
				if (measurePoints[i].onClick != undefined)
				{
					if (! MovieClip(this.getChildByName(measurePoints[i].flashId)).buttonMode)
					{
						MovieClip(this.getChildByName(measurePoints[i].flashId)).buttonMode = true;
					}
					this.getChildByName(measurePoints[i].flashId).addEventListener(MouseEvent.CLICK, clickHandler(measurePoints[i].onClick));
				}
				if (measurePoints[i].rightClick != undefined)
				{
					if (! MovieClip(this.getChildByName(measurePoints[i].flashId)).buttonMode)
					{
						MovieClip(this.getChildByName(measurePoints[i].flashId)).buttonMode = true;
					}
					//元件宽度,高度
					//trace("debug:"+this.getChildAt(j).width+ ":"+this.getChildAt(j).height);
					//元件X坐标,Y坐标
					//trace("debug:"+this.getChildAt(j).x+ ":"+this.getChildAt(j).y);
					//元件X坐标+元件宽度的一半，元件的Y坐标-元件高度的一半,前提：元件参考坐标点位于元件中心位置,此时，下拉框位于元件的右上角
					this.getChildByName(measurePoints[i].flashId).addEventListener(MouseEvent.RIGHT_CLICK, rightClickHandler(measurePoints[i].rightClick,this.getChildByName(measurePoints[i].flashId).x+this.getChildByName(measurePoints[i].flashId).width/2,this.getChildByName(measurePoints[i].flashId).y-this.getChildByName(measurePoints[i].flashId).height/2));

				}
				if (measurePoints[i].constantColor != undefined)
				{
					changeBColorByName(this.getChildByName(measurePoints[i].flashId),measurePoints[i].constantColor); 
				}
				if (measurePoints[i].constantValue != undefined)
				{ 
					writeText(this.getChildByName(measurePoints[i].flashId),measurePoints[i].constantValue);
				}
				if (measurePoints[i].cache != undefined)
				{
					if (cacheMap == null)
					    cacheMap = new HashMap;
					cacheMap.put(measurePoints[i].measurePoint,measurePoints[i].cache);
				} 
			}
           }
		   catch (e:Error)
		   {
			   returnValue = e.toString(); 
		   }
			//允许flash调用js函数 参数1：js函数名称 参数2：向js函数传递的参数  ;
			ExternalInterface.call("loadJsonConfigFinish",returnValue);

			
			/*var tt = {"Command":"RTDATA","DEVICE":"RC1RF001","VALUE":"1","CH":"66","CC":"RC-20150109-01"};
			setFlashBySoketCallBackJson(tt);
			tt = {"Command":"RTDATA","DEVICE":"FC1RF001","VALUE":"1","CH":"66","CC":"RC-20150109-01"};
			setFlashBySoketCallBackJson(tt);
			tt = {"Command":"RTDATA","DEVICE":"FC1ZT001","VALUE":"1","CH":"88","CC":"RC-20150109-01"};
			setFlashBySoketCallBackJson(tt);
			tt = {"Command":"RTDATA","DEVICE":"HC1ZT001","VALUE":"1","CH":"66","CC":"RC-20150109-01"};
			setFlashBySoketCallBackJson(tt);
			tt = {"Command":"RTDATA","DEVICE":"ZY1ZT001","VALUE":"1","CH":"66","CC":"RC-20150109-01"};
			setFlashBySoketCallBackJson(tt);
			tt = {"Command":"RTDATA","DEVICE":"PD1ZT001","VALUE":"1","CH":"66","CC":"RC-20150109-01"};
			setFlashBySoketCallBackJson(tt);
			tt = {"Command":"RTDATA","DEVICE":"PD1ZT002","VALUE":"1","CH":"66","CC":"RC-20150109-01"};
			setFlashBySoketCallBackJson(tt);
			tt = {"Command":"RTDATA","DEVICE":"PD1ZT003","VALUE":"1","CH":"66","CC":"RC-20150109-01"};
			setFlashBySoketCallBackJson(tt);
			tt = {"Command":"RTDATA","DEVICE":"PD1ST001","VALUE":"2","CH":"66","CC":"RC-20150109-01"};
			setFlashBySoketCallBackJson(tt);
			tt = {"Command":"RTDATA","DEVICE":"PD1ZT004","VALUE":"1","CH":"66","CC":"RC-20150109-01"};
			setFlashBySoketCallBackJson(tt);
			tt = {"Command":"RTDATA","DEVICE":"PD2ZT004","VALUE":"1","CH":"66","CC":"RC-20150109-01"};
			setFlashBySoketCallBackJson(tt);
			tt = {"Command":"RTDATA","DEVICE":"PD1ZT005","VALUE":"1","CH":"66","CC":"RC-20150109-01"};
			setFlashBySoketCallBackJson(tt);
			tt = {"Command":"RTDATA","DEVICE":"PD2ZT005","VALUE":"2","CH":"66","CC":"RC-20150109-01"};
			setFlashBySoketCallBackJson(tt);
			tt = {"Command":"RTDATA","DEVICE":"PC1YG102","VALUE":"200","CH":"66","CC":"RC-20150109-01"};
			setFlashBySoketCallBackJson(tt);
			tt = {"Command":"RTDATA","DEVICE":"RC1GD002","VALUE":"50","CH":"66","CC":"RC-20150109-01"};
			setFlashBySoketCallBackJson(tt);
			tt = {"Command":"RTDATA","DEVICE":"RC1GD003","VALUE":"150","CH":"66","CC":"RC-20150109-01"};
			setFlashBySoketCallBackJson(tt);
			tt = {"DEVICE":"SUMMARY_TRAIN_INFO","VALUE":{"CARS_CNT":"444","CY_CARS_CNT":"666","CARS_BAT_CNT":"777","ZY_BAT_CNT":"888","HY_BAT_CNT":"55","SUM_NET_QTY":"66","STORE_QTY":"77","SC_QTY":"88"}};
			setFlashBySoketCallBackJson(tt);
			tt = {"DEVICE":"TRAIN_SUM_TABLE_INFO","VALUE":{"GD1":{"cc":"123","js":"456","pz":"789","jz":"44"},"GD2":{"cc":"123","js":"456","pz":"789","jz":"44"}}};
			setFlashBySoketCallBackJson(tt);  
			tt = {"Command":"RTDATA","DEVICE":"DC1RC101","VALUE":"34567","CH":"66","CC":"RC-20150109-01"};
			setFlashBySoketCallBackJson(tt);
			tt = {"Command":"RTDATA","DEVICE":"DC1RC102","VALUE":"rrrrrr","CH":"66","CC":"RC-20150109-01"};
			setFlashBySoketCallBackJson(tt);
			var tt = {"DEVICE":"ZY_DEVICE","VALUE":[{"MACHINE_CODE":"ZY01","SUBDEVICE01":"0", "SUBDEVICE02" : "0", "SUBDEVICE03" : "0", "SUBDEVICE04" : "0", "SUBDEVICE05" : "", "SUBDEVICE06" : "0", "SUBDEVICE07" : "0",  "SUBDEVICE08" : "","SUBDEVICE09" : "0", "SUBDEVICE10" : "0", "SUBDEVICE11" : "0", "SUBDEVICE12" : "0", "SUBDEVICE13" : "0", "SUBDEVICE14" : "0", "SUBDEVICE15" : "0", "SUBDEVICE16" : "0", "SUBDEVICE17" : "0", "SUBDEVICE18" : "0",  "SUBDEVICE19" : "0","SUBDEVICE20" : "0", "SUBDEVICE21" : "0", "SUBDEVICE22" : "0", "SUBDEVICE23" : "0", "SUBDEVICE24" : "0",  "SUBDEVICE25" : "0", "SUBDEVICE26" : "0", "SUBDEVICE27" : "0", "SUBDEVICE28" : "0", "SUBDEVICE30" : "1", "SUBDEVICE31" : "0", "SUBDEVICE32" : "0", "SUBDEVICE33" : "0", "SUBDEVICE34" : "0", "SUBDEVICE35" : "0", "SUBDEVICE36" : "0", "SUBDEVICE37" : "0"}]}
			//var tt = {"DEVICE":"ZY_DEVICE","VALUE":[{"SUBDEVICE29":"0","SUBDEVICE30":"1"}]};
			setFlashBySoketCallBackJson(tt);
			//测试何鑫亮的需求
			var tt ; 
			tt = {"Command":"RTDATA","DEVICE":"PD1ZT002","VALUE":"0"};
			setFlashBySoketCallBackJson(tt);
			tt = {"Command":"RTDATA","DEVICE":"PD1ZT0021","VALUE":"1"};
			setFlashBySoketCallBackJson(tt);
			tt = {"Command":"RTDATA","DEVICE":"PD1ZT002","VALUE":"1"};
			setFlashBySoketCallBackJson(tt);
			*/
			var tt ; 
            tt = {"Command":"RTDATA","DEVICE":"PC1YG102","VALUE":"200","CH":"66","CC":"RC-20150109-01"};
            setFlashBySoketCallBackJson(tt);
			

		} 
		private function loadJsonConfigCallBack(webGlobalCtx:String,jsonFileName:String,jsonFilePath:String,ip:String,port:String )
		{
			var loader:URLLoader = new URLLoader  ;
			//这里是你要获取JSON的路径,本地   ;  
			//loader.load(new URLRequest("json.txt"));
			//这里是你要获取JSON的路径，远程;
			//trace("http://localhost:8080"+webGlobalCtx+"/htmlresource/DY_INDEX/json.txt"); 
			loader.load(new URLRequest("http://"+ip+":"+port+webGlobalCtx+"/htmlresource/"+jsonFilePath+"/"+jsonFileName));   
			loader.addEventListener(Event.COMPLETE,decodeJSON);
			return "SUCCESS";
		}
		//单击处理函数
		private function clickHandler(jsMethod:String):Function
		{
			var fun:Function=function(e:MouseEvent)
			{   
			   ExternalInterface.call(jsMethod); 
			   trace("clickHandler:"+jsMethod+":");
			};
			return fun;
		}
		protected function setFlashTitleCallBack(flashTile:String )
		{
			 return flashTile;
		}
		//右键处理函数
		private function rightClickHandler(para:Object,x1:Number,y1:Number):Function
		{
			var fun:Function=function(e:MouseEvent)
			{       
			   if (myComboBox ==null) {
			   myComboBox = new ComboBox();  
			   myComboBox.addItem({label:"请选择所需操作"});
			   for (var i:int = 0; i < para["cmdNum"]; i++) {  
			        myComboBox.addItem({label:para["cmd"+i]["commandName"],data:para["cmd"+i]["commandCode"]+","+para["cmd"+i]["machineCode"]+","+para["cmd"+i]["machineType"]}); 
			   }
			   myComboBox.move(x1, y1);
			   myComboBox.width= 108;
			   myComboBox.addEventListener("change", changeHandler);  
			   addChild(myComboBox);  
			   /*
			   //5秒自动关闭，暂时不用，现在改用，滑出自动删除combobox控件
			   var myTimer:Timer= new Timer(5000, 1);  
               myTimer.addEventListener("timer", timerHandler);    
               myTimer.start();
			   */
			   }
			};
			return fun;
		} 
		private function removeCombobox(e:MouseEvent):void
		{
			trace("removeCombobox:"+myComboBox); 
			
			if (myComboBox != null)
			{ 
				var min_x = myComboBox.x-50;
				var max_x = myComboBox.x+myComboBox.width;
				var min_y = myComboBox.y-myComboBox.height/2;
				var max_y = Math.max(myComboBox.y+myComboBox.height/2+50,myComboBox.y+myComboBox.height/2+myComboBox.dropdown.length*myComboBox.height);
				if (e.stageX >= min_x && e.stageX <=max_x && e.stageY>=min_y && e.stageY <= max_y) {
					trace("无任何动作！！！");
				}  else {
					trace("删除！"); 
					myComboBox.close();
				    myComboBox.removeAll();
				    removeChild(myComboBox);
			        myComboBox = null;
				}  
			}  
		}
		private function timerHandler(event:TimerEvent):void {    
			trace("timerHandler:"+myComboBox); 
			if (myComboBox != null) {
				myComboBox.close();
				myComboBox.removeAll();
				removeChild(myComboBox);
			    myComboBox = null;
			}
        } 
		//combobox值改变处理函数
		private function changeHandler(e:Event):void
		{

			trace("changeHandler:");
			trace(ComboBox(e.target).selectedItem.data);
			var comboBoxValue:String = ComboBox(e.target).selectedItem.data;
			ExternalInterface.call("submitCommand",comboBoxValue.split(",")[0],comboBoxValue.split(",")[1],comboBoxValue.split(",")[2]);
			removeChild(myComboBox);
			myComboBox = null;
		}

		/*
		tagType 说明
		COLOR：测点VALUE值设置颜色;
		VALUE：测点VALUE值设置TEXT;通过map的配置转变显示文字
		        例子：
		        {"measurePoint":"PD1ZT001","flashId":"beltC1A","tagType":"COLOR","mapValue":{"0":"0","1":"1"},"dependent":{"measurePoint":"PD1ZT0011","measureValue":"0"}},
                {"measurePoint":"PD1ZT0011","flashId":"beltC1A","tagType":"COLOR","cache":"0","mapValue":{"1":"2"}},
		CC：测点CC值设置TEXT;
		CH：测点CH值设置TEXT;
		CP：测点CP值设置TEXT;
		JSON：测点JSON值设置TEXT;
		LIGHT:测点VALUE设置的信号灯颜色显示
		SWITCH:测点VALUE设置的开关量;
		VISIBLE:对象可见设置
		TWOMP:特殊二测点控制一个影片 根据两个测点的值控制同一个flashId的颜色
		SIMPLE_ANIMATION:简单动画
		TEE:三通
		TEEBELT:三通皮带（与三通用同一个测点）
		JSONS.COLOR：测点JSON数组颜色显示;
		JSONS.VALUE：测点JSON数组显示文本;
		..：TODO
		
		注意：json["VALUE"] == json.VALUE
		*/
		private function setFlashBySoketCallBackJson(json:Object)
		{ 
			var dependValue;
			var mapValue;
			var jsonNum:Number = 0; 
			//trace("缓存区大小："+cacheMap.length()); 
			for (var i = 0; i < measurePoints.length; i++)
			{
				if (measurePoints[i].measurePoint == json.DEVICE)
				{
					if (measurePoints[i].tagType == "COLOR" || measurePoints[i].tagType == "TEE"|| measurePoints[i].tagType == "TEEBELT"|| measurePoints[i].tagType == "SWITCH"|| measurePoints[i].tagType == "LIGHT"||measurePoints[i].tagType == "TWOMP")
					{
						if (measurePoints[i].tagType == "COLOR"|| measurePoints[i].tagType == "SWITCH"|| measurePoints[i].tagType == "LIGHT"||measurePoints[i].tagType == "TWOMP")
						{  
							if (measurePoints[i].dependent != undefined)
							{ 
								trace(cacheMap.get(measurePoints[i].dependent.measurePoint));
							}
							if (measurePoints[i].tagType == "SWITCH") 
							{ 
								dependValue = json.VALUE; 
							} 
							try
							{   
								//处理何鑫亮两个测点对同一个flashId且存在依赖关系的缓存数据--20160114
								if (measurePoints[i].cache != undefined)
				                { 
					                cacheMap.put(measurePoints[i].measurePoint,json.VALUE);
									if (getJSONValue(json.VALUE,measurePoints[i].mapValue) != null) 
									{
										mapValue = hashMap.get(getJSONValue(json.VALUE,measurePoints[i].mapValue));
									} 
				                }  
								else if (measurePoints[i].dependent != undefined && cacheMap.get(measurePoints[i].dependent.measurePoint) == measurePoints[i].dependent.measureValue)
				                {  
									mapValue = hashMap.get(getJSONValue(json.VALUE,measurePoints[i].mapValue)); 
				                }
								else 
								{
									mapValue = measurePoints[i].tagType == "COLOR"?hashMap.get(json.VALUE).toString():(measurePoints[i].tagType == "SWITCH"?switchMap.get(json.VALUE).toString():(measurePoints[i].tagType == "LIGHT"?lightMap.get(json.VALUE).toString():measurePoints[i].setColor));
								}
								if (mapValue != undefined)
								    changeBColorByName(this.getChildByName(measurePoints[i].flashId),mapValue);
							}
							catch (e:Error)
							{
								trace("测点ID："+measurePoints[i].flashId+",测点类型："+measurePoints[i].tagType+"：错误编码："+e.toString());
							}
						}
						else if (measurePoints[i].tagType == "TEE")
						{
							jsonNum=Number(json.VALUE) ;
							try
							{
								changeBColorByNum(this.getChildByName(measurePoints[i].flashId),jsonNum+1);

							}
							catch (e:Error)
							{
								trace("测点ID："+measurePoints[i].flashId+",测点类型："+measurePoints[i].tagType+"：错误编码："+e.toString());
							}
						} 
						else if (measurePoints[i].tagType == "TEEBELT")
						{
							try
							{
								if (measurePoints[i].closeValue == json.VALUE) 
								{
									mapValue = 'white';
									changeBColorByName(this.getChildByName(measurePoints[i].flashId),mapValue);
								} 
								else if (measurePoints[i].dependValue == json.VALUE)
								{
									mapValue = teeBeltMap.get(json.VALUE).toString()
								    changeBColorByName(this.getChildByName(measurePoints[i].flashId),mapValue);
								    if (measurePoints[i].closeFlashId  != '') 
								    {
									   mapValue = 'white';
									   changeBColorByName(this.getChildByName(measurePoints[i].closeFlashId),mapValue);
								    } 
								}
								
							}
							catch (e:Error)
							{
								trace("测点ID："+measurePoints[i].flashId+",测点类型："+measurePoints[i].tagType+"：错误编码："+e.toString());
							}
						} 
					}
					else if (measurePoints[i].tagType == "VISIBLE")
					{
						if (json.VALUE == measurePoints[i].visibleValue) 
						{
							this.getChildByName(measurePoints[i].flashId).visible = true;
						} 
						else 
						{
							this.getChildByName(measurePoints[i].flashId).visible = false; 
						}
					}
					else if (measurePoints[i].tagType == "SIMPLE_ANIMATION" && dependValue == measurePoints[i].dependValue)
					{
					     try
							{ 
								playAnimation(this.getChildByName(measurePoints[i].flashId),Number(measurePoints[i].frameNum));

							}
							catch (e:Error)
							{
								trace("测点ID："+measurePoints[i].flashId+",测点类型："+measurePoints[i].tagType+"：错误编码："+e.toString());
							}
					}
					else if (measurePoints[i].tagType == "PLAY_ANIMATION")
					{
					    try
							{
								if (json.ActionTag == "PLAY")
					    		{
					    			playAnimation(this.getChildByName(measurePoints[i].flashId),json.VALUE);
					    		} 
					    		else if (json.ActionTag == "STOP") 
					    		{
					    			changeBColorByName(this.getChildByName(measurePoints[i].flashId),json.VALUE)
					    		}
							}
							catch (e:Error)
							{
								trace("测点ID："+measurePoints[i].flashId+",测点类型："+measurePoints[i].tagType+"：错误编码："+e.toString());
							}
					}
					else if (measurePoints[i].tagType == "TRANSLATE1")
					{
					    try
							{
								
					    		mapValue = translateMap1.get(json.VALUE).toString();
								changeBColorByName(this.getChildByName(measurePoints[i].flashId),mapValue);
							}
							catch (e:Error)
							{
								trace("测点ID："+measurePoints[i].flashId+",测点类型："+measurePoints[i].tagType+"：错误编码："+e.toString());
							}
					}
					else if (measurePoints[i].tagType == "TRANSLATE2")
					{
					    try
							{
					    		mapValue = translateMap2.get(json.VALUE).toString();
								changeBColorByName(this.getChildByName(measurePoints[i].flashId),mapValue);
							}
							catch (e:Error)
							{
								trace("测点ID："+measurePoints[i].flashId+",测点类型："+measurePoints[i].tagType+"：错误编码："+e.toString());
							}
					}
					else if (measurePoints[i].tagType == "TRANSLATE3")
					{
					    try
							{
					    		mapValue = translateMap3.get(json.VALUE).toString();
								changeBColorByName(this.getChildByName(measurePoints[i].flashId),mapValue);
							}
							catch (e:Error)
							{
								trace("测点ID："+measurePoints[i].flashId+",测点类型："+measurePoints[i].tagType+"：错误编码："+e.toString());
							}
					}
					else
					{
						if (typeof json.VALUE == "string")
						{ 
							try
							{
								if (measurePoints[i].divisorValue != undefined) 
								{
									mapValue = Number(json[measurePoints[i].tagType])/Number(measurePoints[i].divisorValue);
								} 
								else if (measurePoints[i].mapValue != undefined) //获取value值的时候可以通过map的配置转变显示文字
								{
									mapValue = getJSONValue(json.VALUE,measurePoints[i].mapValue);
								} 
								else 
								{
									mapValue = json[measurePoints[i].tagType];
								} 
								
								writeText(this.getChildByName(measurePoints[i].flashId),mapValue);

							}
							catch (e:Error)
							{
								writeText(this.getChildByName(measurePoints[i].flashId),e.toString());
								trace("测点ID："+measurePoints[i].flashId+",测点类型："+measurePoints[i].tagType+"：错误编码："+e.toString());
							}
						}
						else if (typeof json.VALUE == "object")
						{
							if (measurePoints[i].tagType == "JSON")
							{  
							  try
							   {
								//writeText(this.getChildAt(j),json["VALUE"][measurePoints[i].subMeasurePoint]);
								writeText(this.getChildByName(measurePoints[i].flashId),getJSONValue(measurePoints[i].subMeasurePoint,json.VALUE));

							   }
							   catch (e:Error)
							   {
								writeText(this.getChildByName(measurePoints[i].flashId),e.toString());
								trace("测点ID："+measurePoints[i].flashId+",测点类型："+measurePoints[i].tagType+"：错误编码："+e.toString());
							   } 
							} 
							else if (measurePoints[i].tagType == "JSONS.COLOR") 
							{
							   try
							   { 
								 mapValue = hashMap.get(getJSONArrayValue(measurePoints[i].subMeasurePoint,json.VALUE,measurePoints[i].IDX));  
								 changeBColorByName(this.getChildByName(measurePoints[i].flashId),mapValue);
							   }
							   catch (e:Error)
							   { 
								trace("测点ID："+measurePoints[i].flashId+",测点类型："+measurePoints[i].tagType+"：错误编码："+e.toString());
							   }
							
							}
							else if (measurePoints[i].tagType == "JSONS.COLOR2") //根据测点json对象的某一个值改变图的颜色
							{
							   try
							   { 
								 mapValue = hashMap.get(getJSONValue(measurePoints[i].subMeasurePoint,json.VALUE));  
								 changeBColorByName(this.getChildByName(measurePoints[i].flashId),mapValue);
							   }
							   catch (e:Error)
							   { 
								trace("测点ID："+measurePoints[i].flashId+",测点类型："+measurePoints[i].tagType+"：错误编码："+e.toString());
							   }
							
							}		
							else if (measurePoints[i].tagType == "JSONS.VALUE") 
							{  try
							   { 
								writeText(this.getChildByName(measurePoints[i].flashId),getJSONArrayValue(measurePoints[i].subMeasurePoint,json.VALUE,measurePoints[i].IDX)); 
							   }
							   catch (e:Error)
							   {
								writeText(this.getChildByName(measurePoints[i].flashId),e.toString());
								trace("测点ID："+measurePoints[i].flashId+",测点类型："+measurePoints[i].tagType+"：错误编码："+e.toString());
							   } 
							}
						}
						else
						{
							trace("测点ID："+measurePoints[i].flashId+",测点类型："+measurePoints[i].tagType);
						}
					}

				}

			}

		}
		//解析json
		private function getJSONValue(subMeasurePoint:String,obj:Object)
		{
			//trace(((("subMeasurePoint：" + subMeasurePoint) + ",Object:") + obj));
			var subMeasurePointTemp:String = "";
			var returnValue:String = "";
			var subMeasurePoints:Array = subMeasurePoint.split(".");
			if (subMeasurePoints.length > 1)
			{
				for (var i:int = 0; i < subMeasurePoints.length; i++)
				{
					if (i > 0)
					{
						subMeasurePointTemp +=  i == 1 ? subMeasurePoints[i]:"." + subMeasurePoints[i];
					}
				}
				returnValue = getJSONValue(subMeasurePointTemp,obj[subMeasurePoints[0]]);
			}
			else
			{
				returnValue = obj[subMeasurePoints[0]];
			}
			return returnValue;
		}
		//解析jsonArray
		private function getJSONArrayValue(subMeasurePoint:String,obj:Object,arrayIndex:int)
		{
			trace(((("subMeasurePoint：" + subMeasurePoint) + ",Object:") + obj[arrayIndex])); 
			var returnValue:String = "";
			returnValue = getJSONValue(subMeasurePoint,obj[arrayIndex]);
			return returnValue;
		}
		//设置对象的TEXT
		private function writeText(obj:Object,textValue:String)
		{
			obj.text = textValue;
		}

		//切换对象的颜色（帧）
		private function changeBColorByNum(obj:Object,frameNum:int)
		{
			obj.gotoAndStop(frameNum);
		}

		//切换对象的颜色（帧名）
		private function changeBColorByName(obj:Object,frameName:String)
		{
			obj.gotoAndStop(frameName);
		}
		//运行动画
		private function playAnimation(obj:Object,frameNum:int)
		{    
			obj.gotoAndPlay(frameNum);
		}
		// constructor code  
		public function Nept()
		{
			trace("父类初始化");
			//初始化
			init();
			//本地测试用
			//loadJsonConfigCallBack("","","","","");

			function init()
			{

				//允许js调用flash中的函数 参数1：要调用flash函数的js函数，参数2：被调用flash函数    
				ExternalInterface.addCallback("loadJsonConfig",loadJsonConfigCallBack);

				//允许js调用flash中的函数 参数1：要调用flash函数的js函数，参数2：被调用flash函数    ;
				ExternalInterface.addCallback("setFlashBySoketJson",setFlashBySoketCallBackJson); 
				
				//允许js调用flash中的函数 参数1：要调用flash函数的js函数，参数2：被调用flash函数    
			    ExternalInterface.addCallback("setFlashTitle",setFlashTitleCallBack);
				

				stage.addEventListener(MouseEvent.MOUSE_MOVE, removeCombobox);

			}
		}
	}

}