	

	String.prototype.startWith = function(str){
		var reg=new RegExp("^"+str);     
		return reg.test(this);
	};
	
	String.prototype.endWith = function(str){
		if(str==null||str==""||this.length==0||str.length>this.length)
		  	return false;
		if(this.substring(this.length-str.length)==str)
		 	return true;
		else
		  	return false;
		return true;
	};
	
	String.prototype.trim = function(){
		return this.replace(/(^\s*)|(\s*$)/g,"");
	};
	
	/**
	 * 	 * 获取项目的根路径
	 * @param postPathNull boolean 项目路径名是否为空
	 * @returns
	 */
	function getRootPath(postPathNull) {
		if (window.document.rootPath){
			return window.document.rootPath;
		} else {			
			var fullPath = window.document.location.href;
			var pathName = window.document.location.pathname;
			var pos = fullPath.indexOf(pathName);
			var prePath = fullPath.substring(0, pos);
			if (postPathNull){
				return prePath;
			}
			var postPath = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
			return prePath + postPath;
		}
	}
	
	/*function addWantedLabelRootPath() {
		addLabelRootPathByConfig([ [ 'img', 'src' ], [ 'input', 'src' ],
			[ 'script', 'src' ], [ 'style', 'src' ], [ 'frame', 'src' ],
			[ 'iframe', 'src' ], [ 'a', 'href' ], [ 'link', 'href' ],
			[ 'form', 'action' ] ]);
	}
	
	function addLabelRootPathByConfig(configArray) {
		for (var i = 0; i < configArray.length; i++){
			var config = configArray[i];
			var tags = document.getElementsByTagName(config[0]);
			for (var j = 0; j < tags.length; j++){
				addLabelRootPath(tags[j], config[1]);
			}
		}
	}

	function addLabelRootPath(element, url) {
		if (element[url]){
			if (element[url].startWith('/'))
				element[url] += getRootPath();
			else if (element[url].indexOf('$(rootPath)') >= -1)
				element[url] = element[url].replace('$(rootPath)', getRootPath());
		}
	}*/

	function composeSelect(entryList, value, attribute) {
		var composeOptions = function (entryList, value) {
			var html = "";
			for (var i = 0; i < entryList.length; i++){
				html += '<option value="' + entryList[i].key + '" ';
				if (entryList[i].key == value){
					html += ' selected="selected"';
				}
				html += '>' + entryList[i].value + '</option>';
			}
			return html;
		}
		
		var html = '<select ';
		for (key in attribute){
			html += key;
			html += '="' + attribute[key] + '" ';
		}
		html += '>';
		html += composeOptions(entryList, value);
		html += '</select>'
		return html;
	}
	
	function readModeSelect(entryList, value, showName, valueName){
		var text = '';
		for (var i = 0; i < entryList.length; i++){
			var obj = entryList[i];
			if (obj[valueName] == value){
				text = obj[showName];
			}
		}
		return '<span class="read_mode">' + text + '</span>';
	}
	
	
	function ajaxRequest(url, data, succCallback, failCallback) {
		$.ajax({
			type : "GET",
			url : url,
			data : data,
			dataType : "json",
			success : function(result) {
				if (result.message){					
					alert(result.message);
				}
				if (result.success) {
					if (succCallback) {
						succCallback(result);
					}
				} else {
					if (failCallback) {
						failCallback(result);
					}
				}
			}
		});
	}
	
	
	function slipOutDiv(id, divWidth, divHeight) {
		var div = $("#" + id);
		if (div.css("display") == 'block'){
			div.animate({top: '-5000px'}, 'fast', function(){
				div.css({display: 'none'});
			});
		} else {				
			var left = (document.body.clientWidth - divWidth) / 2 + 'px';
			var top = (document.body.clientHeight - divHeight) / 2.5 + 'px';
			div.css({display: 'block'});
			div.animate({left: left, top: top}, 'fast');
		}
	}
	
	function getFormData(outId, codeFn){
		var data = {};
		var inputs = $("#" + outId).find('input, select');
		for (var i = 0; i < inputs.length; i++){
			var obj = inputs[i];
			if (obj.name){				
				data[obj.name] = typeof(obj.value) == "undefined" ? '' : codeFn ? codeFn(obj.value) : (obj.value);
			}
		}
		return data;
	}
	
	/**
	 * 
	 * @param form	form标签对象
	 * @returns		键值对拼接的字符串
	 */
	function getFormParams(outId, condeFn) {  
		var elements = $("#" + outId).find('input, select');
	    var paramEntryArray = [];  
	    for (var i = 0; i < elements.length; i++) {  
	        var paramEntry = getParamEntry(elements[i]);  
	        if (paramEntry && paramEntry[0]) {  
	          	paramEntryArray.push(paramEntry[0] + '=' + condeFn(paramEntry[1]));  
	        } 
	    }
	    return paramEntryArray.join('&');
	}

	/**
	 * 
	 * @param element	form表单元素对象
	 * @returns			键值对
	 */
	function getParamEntry(element) {  
		if (element.tagName.toLowerCase() == 'select')
			return [element.name, element.value];
	    switch (element.type.toLowerCase()) {  
	      	case 'submit':  
	    	    return false;  
	      	case 'hidden':  
	      		return [element.name, element.value];  
	      	case 'password':  
	      		return [element.name, element.value];  
	      	case 'text': 
	      		return [element.name, element.value];   
	      	case 'checkbox': 
	      		if (element.checked)  
	    	     	return [element.name, element.value];   
	      	case 'radio':  
	      		if (element.checked)  
	    	     	return [element.name, element.value];   
	    }  
	    return null;  
	}
	

	/**
	 * 校验类型vtype属性
	 * digit:	只能输入阿拉伯数字，过滤掉所有非数字字符
	 * int:		只能输入无符号位整数,
	 * -int:	可以输出负符号位,
	 * +int:	可以输出负符号位
	 * +-int:	可以输出正负符号位
	 * letter:	只能输入字母，过滤所有非字母字符
	 **/
	function validateType(obj, noticeHandle){
		var val = $(obj).val();
		var vtype = $(obj).attr('vtype');
		
		if (vtype && val){
			var type = vtype.toLowerCase();
			var regxNum = /^[-+]?([1-9]\d*)?$|^[-+]?0(\.\d*)?$|^[-+]?[1-9](\d*\.\d*)?$/; 
			var regxLetter = /^[A-Za-z]*$/;	
			if (type == 'number' && !regxNum.test(val)) {//只能输入正负数字包括小数，截掉不符合往后所有字符
				if (!/^[0-9]|^[-+]/.test(obj.value)) {
					obj.value = '';
				} else {
					obj.value = cutByRegx(obj.value, regxNum);
				}
				noticeHandle('只能输入数字，可以含正负号，包括小数', val, obj);
			} else if (type.indexOf("int") != -1){
				var noticeStr = '只能输入整数';
				var regxInt = /^[1-9]\d*?$|^0$/;
				if (type.indexOf("-") != -1 && type.indexOf("+") != -1) {
					regxInt = regxInt = /^[-+]?([1-9]\d*)?$|^0$/;
					noticeStr += '，可以含正负号';
				} else if (type.indexOf("-") != -1) {
					regxInt = regxInt = /^-?([1-9]\d*)?$|^0$/;
					noticeStr += '，可以含负号';
				} else if (type.indexOf("+") != -1) {
					regxInt = regxInt = /^[+]?([1-9]\d*)?$|^0$/;
					noticeStr += '，可以含正号';
				}
				var v = cutByRegx(obj.value, regxInt);
				if (obj.value != v){
					obj.value = v;
					noticeHandle(noticeStr, val, obj);
				}
				
			} else if (type == 'letter' && !regxLetter.test(val)){
				obj.value = obj.value.replace(/[^a-zA-Z]{2,}/g,'');
				noticeHandle('只能输入英文字母', val, obj);
			} else if (type == 'digit' && isNaN(val)){
				obj.value = obj.value.replace(/\D/g, '');
				noticeHandle('只能输入数字', val, obj);
			} 
		}
	}
	
	function cutByRegx(str, regx){
		for (var i = 1; i <= str.length; i++) {
			if (!regx.test(str.substr(0, i))) {
				return str.substr(0, i - 1);
			}
		}
		return str;
	}
	
	/**
	 * 阻塞线程
	 * time:	    每多少秒检查一次，是否需要打断阻塞
	 * booleanFn: 是否打断阻塞状态的函数
	function blockThread(booleanFn, time){
		if (!time)
			time = 50;
		if (typeof booleanFn != "function")
			throw new Error('判断是否打断阻塞的函数为空，或者不是函数');
			
		var timer = null;
		var stop = function (){
			clearInterval(timer);
		}
		timer = setInterval(function(){
			if (timer && booleanFn()){
				stop();
			}
		}, time);
	}
	 **/
	function blockThread(booleanFn, time){
		if (!time)
			time = 50;
		if (typeof booleanFn != "function")
			throw new Error('判断是否打断阻塞的函数为空，或者不是函数');
		
		if (booleanFn()){
			return;
		}
		setTimeout(function(){
			blockThread(booleanFn, time);
		}, time);
	}

	/**
	 * 判断触摸屏被触摸
	 * @returns
	 */
	function isTouch() {
	    return navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i) ? !0 : !1
	}
	
	