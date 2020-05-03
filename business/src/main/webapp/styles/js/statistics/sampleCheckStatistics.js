function showDataSampleCheck(json,chartId,isShowTable){
    $.messager.progress('close');

    if(isShowTable){
        loadTable(json);
    }

    var monthDatas = new Array();//x轴
    var sampleCheck = new Array();//抽检率
    var qualityCheck = new Array();//检质率

    for (var i = 0; i < json.length; i++) {
        monthDatas.push(Number(json[i].statLat));
        sampleCheck.push(Number(json[i].statResult1));
        qualityCheck.push(Number(json[i].statResult2));
    }

    var myChart = echarts.init(document.getElementById(chartId));
    var option = {
        title: {
            text: '存查样抽检统计'
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
            data: ['抽检率','检质率']
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
                name: '存查样抽检率'
            }
        ],
        series: [
            {
                "name": "抽检率",
                "type": "bar",
                "data": sampleCheck,
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
                "data": qualityCheck,
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