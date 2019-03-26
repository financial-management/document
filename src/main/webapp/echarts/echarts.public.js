

var myChart = echarts.init(document.getElementById('main'),echartPub.theme);

var option = {
            title: {
                text: echartPub.title
            },
            tooltip: {
				
			},
			legend: {
                data:echartPub.legend
            },
            xAxis: {
                data: echartPub.x
            },
            yAxis: {},
            
            series: {
				name: echartPub.name,
                type: echartPub.type,
                data: echartPub.data
            }
        };

myChart.setOption(option);