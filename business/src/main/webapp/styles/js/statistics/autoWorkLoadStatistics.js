/**
 * 因为统计分析主页需要调用各个页面的showDataXX方法，所以剥离开到单个js中
 */
function showDataAutoWork(json,chartId,isShowTable){
    $.messager.progress('close');

    if(isShowTable){
        loadTable(json);
    }

    var monthDatas = new Array();//x轴
    var calDatas = new Array();//计量
    var sampleDatas = new Array();//采样
    var samplingDatas = new Array();//制样

    for (var i = 0; i < json.length; i++) {
        monthDatas.push(Number(json[i].statLat));
        calDatas.push(Number(json[i].statResult1));
        sampleDatas.push(Number(json[i].statResult2));
        samplingDatas.push(Number(json[i].statResult3));
    }

    var myChart = echarts.init(document.getElementById(chartId));
    var option = {
        title: {
            text: '自动化工作量统计'
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
            data: ['计量','采样','制样']
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
            }
        ]
    };
    myChart.setOption(option);
}
