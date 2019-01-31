{
"title": {
"text": "${name}"
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
    "text": ""
    }
    },
"legend": {
"layout": "vertical",
"align": "right",
"verticalAlign": "middle"
},
"series": [{
"name": "dm1",
"data": [<#list dm1 as x>${x?c}<#sep>, </#list>]
},
    {
    "name": "dm2",
    "data": [<#list dm2 as x>${x?c}<#sep>, </#list>]
},
    {
    "name": "dm3",
    "data": [<#list dm3 as x>${x?c}<#sep>, </#list>]
}]
}