function showDataAutoWorkRate(json,chartId,isShowTable){
    $.messager.progress('close');

    if(isShowTable){
        loadTable(json);
    }

    var monthDatas = new Array();//x轴
    var calDatas = new Array();//计量
    var sampleDatas = new Array();//采样
    var samplingDatas = new Array();//制样
    var totalDatas = new Array();//综合

    for (var i = 0; i < json.length; i++) {
        monthDatas.push(Number(json[i].statLat));
        calDatas.push(Number(json[i].statResult1));
        sampleDatas.push(Number(json[i].statResult2));
        samplingDatas.push(Number(json[i].statResult3));
        totalDatas.push(Number(json[i].statResult4));
    }

    var myChart = echarts.init(document.getElementById(chartId));
    var option = {
        title: {
            text: '设备投运率统计'
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
            data: ['计量','采样','制样','综合']
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
                name: '投运率'
            }
        ],
        series: [
            {
                "name": "计量",
                "type": "bar",
                "data": calDatas,
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
                "name": "采样",
                "type": "bar",
                "data": sampleDatas,
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
                "name": "制样",
                "type": "bar",
                "data": samplingDatas,
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
                "name": "综合",
                "type": "bar",
                "data": totalDatas,
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