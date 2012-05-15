function loadOptions(opts,series) {
    var options = {
		chart: {
			renderTo: 'container',
			defaultSeriesType: 'line',
			marginRight: 50,
			marginBottom: 40
		},
		title: {
			text: opts.title,
			x: -20 //center
		},
		subtitle: {
			text: opts.description,
			x: -20
		},
		xAxis: {
		    title: {text: opts.xAxisTitle},
			//categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
			//	'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
		},
		yAxis: {
			title: {text: opts.yAxisTitle}
		},
		tooltip: {
			formatter: function() {
					return '<b>'+ this.series.name +'</b><br/>'+
					this.x +': '+ this.y;
			}
		},
		legend: {
			layout: 'vertical',
			align: 'right',
			verticalAlign: 'top',
			x: -10,
			y: 100,
			borderWidth: 0
		},
		series: []
    };
    options.series = series
    return options;
}
