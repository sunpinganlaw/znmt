Highcharts.setOptions({
    colors: ['#058DC7', '#FF9655', '#ED561B', '#DDDF00', '#24CBE5', '#64E572', '#50B432', '#FFF263', '#6AF9C4']
});
//饼图
function initChartPie(div, seriesName, jsonArray, url) {
    $('#' + div).highcharts({
        chart: {
            type: 'pie',
            options3d: {
                enabled: true,
                alpha: 45,
                beta: 0,
                margin: [0, 0, 0, 0] //距离上下左右的距离值
            }
        },
        title: {
            text: ''
        },
        tooltip: {
            pointFormat: '{series.name}:<b>{point.percentage:.1f}%</b>'
        },
        exporting: {
            buttons: {
                contextButton: {
                    menuItems: [
                        {
                            text: '功能链接',
                            onclick: function () {
                                parent.addTab(seriesName + "||icon-sys||" + WEB_GLOBAL_CTX + url);
                            }
                        },
                        {
                            separator: true
                        }
                    ]
                        .concat(Highcharts.getOptions().exporting.buttons.contextButton.menuItems)
                }
            }
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                depth: 35,
                dataLabels: {
                    enabled: true,
                    format: '{point.name}'
                }
            }
        },
        series: [
            {
                type: 'pie',
                name: seriesName,
                data: jsonArray
            }
        ]
    });
}

//柱图
function initChartColumn(div, seriesName, jsonArrayName, jsonArrayData, url) {
    $('#' + div).highcharts({
        chart: {
            type: 'column',
            margin: 75,
            options3d: {
                enabled: true,
                alpha: 15,
                beta: 15,
                depth: 50,
                viewDistance: 25
            }
        },
        title: {
            text: ''
        },
        exporting: {
            buttons: {
                contextButton: {
                    menuItems: [
                        {
                            text: '功能链接',
                            onclick: function () {
                                parent.addTab(seriesName + "||icon-sys||" + WEB_GLOBAL_CTX + url);
                            }
                        },
                        {
                            separator: true
                        }
                    ]
                        .concat(Highcharts.getOptions().exporting.buttons.contextButton.menuItems)
                }
            }
        },
        plotOptions: {
            column: {
                depth: 25
            }
        },
        xAxis: { categories: jsonArrayName
        },
        series: [
            {
                name: seriesName,
                data: jsonArrayData
            }
        ]
    });
}
//伏特图
function initSolidgauge(div, seriesName, maxNum, nowNum, url) {

    var gaugeOptions = {

        chart: {
            type: 'solidgauge'
        },

        title: null,
        exporting: {
            buttons: {
                contextButton: {
                    menuItems: [
                        {
                            text: '功能链接',
                            onclick: function () {
                                parent.addTab(seriesName + "||icon-sys||" + WEB_GLOBAL_CTX + url);
                            }
                        },
                        {
                            separator: true
                        }
                    ]
                        .concat(Highcharts.getOptions().exporting.buttons.contextButton.menuItems)
                }
            }
        },
        pane: {
            center: ['50%', '85%'],
            size: '140%',
            startAngle: -90,
            endAngle: 90,
            background: {
                backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || '#EEE',
                innerRadius: '60%',
                outerRadius: '100%',
                shape: 'arc'
            }
        },

        tooltip: {
            enabled: false
        },

        // the value axis
        yAxis: {
            stops: [
                [0.1, '#55BF3B'], // green
                [0.5, '#DDDF0D'], // yellow
                [0.9, '#DF5353'] // red
            ],
            lineWidth: 0,
            minorTickInterval: null,
            tickPixelInterval: 400,
            tickWidth: 0,
            title: {
                y: -70
            },
            labels: {
                y: 16
            }
        },

        plotOptions: {
            solidgauge: {
                dataLabels: {
                    y: 5,
                    borderWidth: 0,
                    useHTML: true
                }
            }
        }
    };

    // The speed gauge
    $('#' + div).highcharts(Highcharts.merge(gaugeOptions, {
        yAxis: {
            min: 0,
            max: maxNum,
            title: {
                text: ''
            }
        },

        credits: {
            enabled: false
        },

        series: [
            {
                name: '',
                data: [nowNum],
                dataLabels: {
                    format: '<div style="text-align:center"><span style="font-size:25px;color:' +
                        ((Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black') + '">{y}</span>' +
                        '</div>'
                },
                tooltip: {
                    valueSuffix: ''
                }
            }
        ]

    }));
}

//表格
function initChartTable(Tablediv, url, tableHead) {
    $(function () {
        $("#" + Tablediv).datagrid({
            url: url,
            idField: 'id',
            width: 410,
            height: 258,
            singleSelect: true,
            columns: eval(tableHead)
        });
    });
}

//折线
function initLine(div, seriesName,csv, url) {
    // Get the CSV and create the chart
    $('#' + div).highcharts({
        data: {
            csv: csv
        },

        title: {
            text: seriesName
        },
        exporting: {
            buttons: {
                contextButton: {
                    menuItems: [
                        {
                            text: '功能链接',
                            onclick: function () {
                                parent.addTab(seriesName + "||icon-sys||" + WEB_GLOBAL_CTX + url);
                            }
                        },
                        {
                            separator: true
                        }
                    ]
                        .concat(Highcharts.getOptions().exporting.buttons.contextButton.menuItems)
                }
            }
        },
        xAxis: {
            tickInterval: 7 * 24 * 3600 * 1000, // one week
            tickWidth: 0,
            gridLineWidth: 1,
            labels: {
                align: 'left',
                x: 3,
                y: -3,
                formatter: function () {
                    return Highcharts.dateFormat('%m.%d', this.value);
                }
            }
        },

        yAxis: [
            { // left y axis
                title: {
                    text: null
                },
                labels: {
                    align: 'left',
                    x: 3,
                    y: 16,
                    format: '{value:.,0f}'
                },
                showFirstLabel: false
            },
            { // right y axis
                linkedTo: 0,
                gridLineWidth: 0,
                opposite: true,
                title: {
                    text: null
                },
                labels: {
                    align: 'right',
                    x: -3,
                    y: 16,
                    format: '{value:.,0f}'
                },
                showFirstLabel: false
            }
        ],

        legend: {
            align: 'left',
            verticalAlign: 'top',
            y: 20,
            floating: true,
            borderWidth: 0
        },

        tooltip: {
            shared: true,
            crosshairs: true
        },

        plotOptions: {
            series: {
                cursor: 'pointer',
                point: {
                    events: {
                        click: function (e) {
                            hs.htmlExpand(null, {
                                pageOrigin: {
                                    x: e.pageX || e.clientX,
                                    y: e.pageY || e.clientY
                                },
                                headingText: this.series.name,
                                maincontentText: Highcharts.dateFormat('%Y-%m-%d', this.x) + ':<br/> ' + this.y + '￥',
                                width: 200
                            });
                        }
                    }
                },
                marker: {
                    lineWidth: 1
                }
            }
        },

        series: [
            {
                name: '',
                lineWidth: 4,
                marker: {
                    radius: 4
                }
            },
            {
                name: ''
            }
        ]
    });
}


//顺序列图
function initColumn(div,seriesName,subName,csv,url) {
    $('#'+div).highcharts({
        chart: {
            type: 'column',
            margin: 75,
            options3d: {
                enabled: true,
                alpha: 15,
                beta: 15,
                depth: 50,
                viewDistance: 25
            }
        },
        plotOptions: {
            column: {
                depth: 25
            }
        },
        data: {
            csv: csv
        },
        title: {
            text: ''
        },
        subtitle: {
            text: subName
        },
        exporting: {
            buttons: {
                contextButton: {
                    menuItems: [
                        {
                            text: '功能链接',
                            onclick: function () {
                                parent.addTab(seriesName + "||icon-sys||" + WEB_GLOBAL_CTX + url);
                            }
                        },
                        {
                            separator: true
                        }
                    ]
                        .concat(Highcharts.getOptions().exporting.buttons.contextButton.menuItems)
                }
            }
        },
        xAxis: {
            type: 'category',
            labels: {
                rotation: -45,
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: seriesName+'人民币 ￥'
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            pointFormat: '<b> {series.name} : {point.y:.1f}￥</b>'
        },
        series: [
            {
                name: '',
                lineWidth: 4,
                marker: {
                    radius: 4
                }
            },
            {
                name: ''
            }
        ]
    });
}
