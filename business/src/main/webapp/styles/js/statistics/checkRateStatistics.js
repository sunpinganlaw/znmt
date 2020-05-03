function showDataCheckRate(json,chartId,isShowTable){
    $.messager.progress('close');

    if(isShowTable){
        loadTable(json);
    }

    var monthDatas = new Array();//x轴
    var totalBatch = new Array();//总批次
    var checkWeightBatch = new Array();//检斤批次
    var checkQualityBatch = new Array();//检质批次
    var checkWeightRate = new Array();//检斤率
    var checkQualityRate = new Array();//检质率

    for (var i = 0; i < json.length; i++) {
        monthDatas.push(Number(json[i].statLat));
        totalBatch.push(Number(json[i].statResult1));
        checkWeightBatch.push(Number(json[i].statResult2));
        checkQualityBatch.push(Number(json[i].statResult3));
        checkWeightRate.push(Number(json[i].statResult4));
        checkQualityRate.push(Number(json[i].statResult5));
    }

    var myChart = echarts.init(document.getElementById(chartId));
    var option = {
        title: {
            text: '检斤检质率统计'
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
            data: ['检斤率','检质率']
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
                name: '来煤检斤检质率'
            }
        ],
        series: [
            {
                "name": "检斤率",
                "type": "bar",
                "data": checkWeightRate,
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
            },
            {
                "name": "检质率",
                "type": "bar",
                "data": checkQualityRate,
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