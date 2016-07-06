<#include "view-head.ftl"/>
<div id="main" style="height:400px"></div>
<!-- ECharts单文件引入 -->
<script src="http://echarts.baidu.com/build/dist/echarts.js"></script>
<script type="text/javascript">
    drawOneLineChar('main', ${datas}, "点击次数");
    function drawOneLineChar(id, dates, title) {
        var showDatas = 'dates';
        showDatas = eval("(" + showDatas +")");
        // 路径配置
        require.config({
            paths: {
                echarts: 'http://echarts.baidu.com/build/dist'
            }
        });

        // 使用
        require(
                [
                    'echarts',
                    'echarts/chart/line',
                ],
                function (ec) {
                    myChart = ec.init(document.getElementById(id));
                    option = {
                        tooltip : {
                            trigger: 'axis'
                        },
                        legend: {
                            data:[title]
                        },
                        toolbox: {
                            show : true,
                            feature : {
                                mark : {show: true},
                                dataView : {show: true, readOnly: false},
                                magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                                restore : {show: true},
                                saveAsImage : {show: true}
                            }
                        },
                        calculable : true,
                        xAxis : [
                            {
                                type : 'category',
                                boundaryGap : false,
                                data : showDatas.cols
                            }
                        ],
                        yAxis : [
                            {
                                type : 'value'
                            }
                        ],
                        series : [
                            {
                                name:'邮件营销',
                                type:'line',
                                stack: '总量',
                                data: showDatas.values
                            }
                        ]
                    };

                    // 为echarts对象加载数据
                    myChart.setOption(option);
                }
        );
    }

</script>