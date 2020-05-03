function showDataEquipmentCheckRate(json,chartId,isShowTable){
    $.messager.progress('close');

    if(isShowTable){
        loadTable(json);
    }

    var monthDatas = new Array();//x轴
    var equipmentCheckRate = new Array();//设备检定率

    for (var i = 0; i < json.length; i++) {
        monthDatas.push(Number(json[i].statLat));
        equipmentCheckRate.push(Number(json[i].statResult1));
    }

    var myChart = echarts.init(document.getElementById(chartId));
    var option = {
        title: {
            text: '设备检定率统计'
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
            data: ['设备检定率']
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
                name: '设备检定率'
            }
        ],
        series: [
            {
                "name": "设备检定率",
                "type": "bar",
                "data": equipmentCheckRate,
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
