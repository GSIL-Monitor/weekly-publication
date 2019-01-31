{
"title": {
"text": "24小时分布图"
},
"credits": {
"enabled": false
},
"chart": {
"height": 500,
"width": 1000
},
"subtitle": {
"text": "数据来源：途鸽信息"
},
"xAxis": {
"categories": [<#list time as x>"${x}"<#sep>, </#list>]
},
"yAxis": {
"title": {
"text": "次数"
}
},
"legend": {
"layout": "vertical",
"align": "right",
"verticalAlign": "middle"
},
"series": [{
"name": "${name}",
"data": [<#list data as x>${x?c}<#sep>, </#list>]
}]
}