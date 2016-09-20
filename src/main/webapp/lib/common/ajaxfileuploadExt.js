
/**
 * 
 * @param paramObj : 属性有:
 * 
 * $ajaxFileUpload 	方法自带属性，
 * progressUrl:		查看上传进度的url
 * progressParam:	查看上传进度请求参数对象
 * deleteUrl:		删除上传文件的url
 * deleteParam:		删除上传文件请求参数对象
 * spaceDiv:		上传结果与进度的显示位置标签id
 * getResultFn:		指定获取上传结果状态的函数，参数为返回结果的data对象:function(data){return data.success;}
 * getFileNameFn:	指定获取上传文件名的函数，参数为返回结果的data对象:function(data){}
 */
function fileUploadWithProgress(paramObj) {
	var width = paramObj.spaceWidth;
	if (!width) {
		width = '150px';
	}
	if (typeof width == 'number') {
		width = width + 'px';
	}
	var showResult = $('<div style="padding-top:15px;" class="showResultClass"></div>');
	var progressBar = $('<div style="width:' + width + '; height:8px; border:1px solid #66F; display:block;"></div>');
	var progress = $('<div style="width:0px; height:8px; background:#66F; font-size:15; color:red; text-align:right;">0%</div>');
	
	progressBar.append(progress);
	showResult.append(progressBar);
	paramObj.spaceDiv.append(showResult);

	jQuery.ajaxFileUpload({
    	url: paramObj.url,  
    	data: paramObj.data,
        secureuri: paramObj.secureuri,
        fileElementId: paramObj.fileElementId,
        dataType: paramObj.dataType,
        success: function (data, status) {
        	if (paramObj.getResultFn(data)){        		
        		var fileName = '';
        		if (paramObj.getFileNameFn) {
        			fileName = paramObj.getFileNameFn(data);
        		}
        		showUploadSuccess(showResult, fileName, paramObj.deleteUrl, paramObj.deleteParam);
        	}
        	paramObj.success(data, status); 
        }
 	});
	
	showUploadProgress(progress, paramObj.progressUrl, paramObj.progressParam, parseInt(width.replace('px', '')));
}

function showUploadProgress(progress, progressUrl, param, width) {
	
	var timer = setInterval(function(){
		fn(new Date().getTime());
	}, 500);
	
	var fn = function(startTime){
		if (new Date().getTime() - startTime > 120000){
			clearInterval(timer);
		}
		
		$.ajax({
			type: "GET",
			url: progressUrl,
			data: param,
	        dataType : "json",
			success: function(data){
				if (data.success){
					if (!data.value.theBytesRead || !data.value.contentLength) {
						return;
					} 
					var rate = data.value.theBytesRead / data.value.contentLength;
					if (rate == 1){						
						clearInterval(timer);
						progress.parent().remove();
					} else {						
						progress.css({
							width : width * rate + "px"
						});
						progress.html(parseInt(100 * rate) + "%");
					}
				}
			}
		});
	};
}

function showUploadSuccess(showResult, fileName, deleteUrl, deleteParam){
	var deleteA = $('<a style="color:blue;cursor:pointer;" href="javascript:void(0);">删除</a>');
	showResult.append('<label for="">' + fileName + '</label>&nbsp;');
	showResult.append('<span style="color:green; display:inline;">上传成功</span>&nbsp;');
	showResult.append(deleteA);
	
	deleteA.on('click', function(e){
		$.ajax({
			type: "GET",
			url: deleteUrl,
			data: deleteParam,
	        dataType : "jsonp",
	        jsonp: "callback",//服务端用于接收callback调用的function名的参数  
			success: function(data){
				if (data.success){
					showResult.remove();
				}
			}
		});
	});
}