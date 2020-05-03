function showDataCoalIn(json,chartId,isShowTable){
    $.messager.progress('close');

    var monthDatas = new Array();//x轴
    var monthDatasRows = new Array();//每个月有多少条记录
    var coalWeights = new Array();//来煤量

    var tempMonthOld = "-1";
    var tempWeight = 0;
    var tempRows = 0;

    if(statLatTypeFlag == "3"){//日报，需要合并数据和单元格
        for (var i = 0; i < json.length; i++) {
            if((tempMonthOld != "-1" && tempMonthOld == json[i].statLat && i>0)|| i== 0){
                if(json[i].statResult5 != null && json[i].statResult5 != ""){
                    tempWeight += Number(json[i].statResult5);
                    tempRows += 1;
                }
            }else{
                monthDatas.push(tempMonthOld);
                coalWeights.push(tempWeight);
                monthDatasRows.push(tempRows);
                tempRows = 0;
                tempRows += 1;
                tempWeight = 0;
                tempWeight +=Number(json[i].statResult5);
            }
            tempMonthOld = json[i].statLat;
        }
        monthDatas.push(tempMonthOld);
        coalWeights.push(tempWeight);
        monthDatasRows.push(tempRows);
    }else if(statLatTypeFlag == "2"){//月报，不需要合并数据和单元格
        for (var i = 0; i < json.length; i++) {
            monthDatas.push(json[i].statLat);
            coalWeights.push(json[i].statResult5);
        }
    }

    if(isShowTable){
        if(statLatTypeFlag == "3"){//日报，需要合并数据和单元格
            loadTable(json,monthDatasRows);
        }else if(statLatTypeFlag == "2"){//月报，不需要合并数据和单元格
            loadTable(json,null);
        }
    }

    var myChart = echarts.init(document.getElementById(chartId));
    var option = {
        title: {
            text: '入厂来煤量统计'
        },
        tooltip: {
            trigger: 'axis'
        },
        calculable : true,
        toolbox: {
            show : true,
            feature : {
                magicType : {show: true, type: ['line', 'bar']},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        legend: {
            data: ['来煤量']
        },
        xAxis: [
            {
                type: 'category',
                data: monthDatas
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: '吨'
            }
        ],
        series: [
            {
                "name": "来煤量",
                "type": "bar",
                "data": coalWeights,
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'}
                    ]
                }
            }
        ]
    };
    myChart.setOption(option);
}