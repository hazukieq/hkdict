//清除组件函数
function clearPreviews(arg){
    //清除任何之前存在的组件；
    let ele;
    while((ele=arg.firstChild)){
        ele.remove();
    }
}

//封装获取tag、id函数
function $et(Nid){
    return document.getElementById(Nid)
}

function $tag(Nid){
    return document.getElementsByTagName(Nid)
}

function $tag(pNid,Nid){
    return pNid.getElementsByTagName(Nid)
}




function offlineAjax(url,success_callback,failed_callback){

    var request_response=app.OfAjax(url);
    loginfo(`offlineAjax_onResponse type: ${typeof(request_response)},content: ${request_response}`)
    if(request_response==''|request_response==null|request_response==undefined|request_response==' '){
        failed_callback()
    }else{
        try {
            success_callback(request_response)
        } catch (error) {
            failed_callback()
            loginfo(error)
        }
    }
}

//封装AJax异步请求函数
var timer;
function sendAjax(url,timeout,success_callback,failed_callback){
    var xhr=new XMLHttpRequest()

    var timedout=false;
    timer=setTimeout(()=>{
        console.log('请求终止！请求地址为:'+url)
        xhr.abort();
        failed_callback();
        timedout=true;
       // timeout_callback();
    },timeout);

    xhr.onreadystatechange=()=>{
        if(xhr.readyState!=4) return;
        if(timedout) return;
        clearTimeout(timer)
        if(xhr.readyState==4&&xhr.status==200){
            if(xhr.responseText.startsWith('<')){
                console.log(xhr.responseText)
                console.log('验证返回文本失败，已回调失败处理函数！')
                xhr.abort()
                failed_callback()
                return;
            }
            else success_callback(xhr.responseText);

        }else{
            failed_callback();
        }
        
    };
    
    xhr.open('GET',url);
    xhr.send();
}

function loginfo(text){
    console.log(`输出详细级别日志__${text}`)
}

function logwarning(text){
    console.log(`输入警告级别日志__${text}`)
}


class Ajax{
    constructor(obj){
        this.method=obj.method;
        this.url=obj.url;
        this.data=obj.data;
        this.success=obj.success;
        this.failure=obj.failure;
        this.timeout=obj.timeout;
        this.async=obj.async;
    }

    send(){
        let success=this.success;
        let failure=this.failure;
        let timeout=this.timeout;
        let isAsync=this.async;
        var xhr=new XMLHttpRequest()

        var timedout=false;
        var timer=setTimeout(()=>{
            timedout=true;
            failure();
            xhr.abort();
        },timeout);

        
        xhr.onreadystatechange=()=>{
            if(xhr.readyState!=4) return;
            if(timedout) return;
            clearTimeout(timer)
            if(xhr.readyState==4&&xhr.status==200){
                if(xhr.responseText.startsWith('<')){
                    console.log(xhr.responseText)
                    console.log('验证返回文本失败，已回调失败处理函数！')
                    xhr.abort();
                    failure();
                    return;
                }
                else success(xhr.responseText);

            }else if(xhr.readyState==4&&xhr.status!=200){
                failure();
            }
        }

        switch(this.method){
            case 'GET':
                xhr.open(this.method,this.url,true)
                xhr.send()
                break;
            case 'POST':
                xhr.open(this.method,this.url,isAsync)
                xhr.setRequestHeader('Content-Type','text/plain')
                xhr.send(JSON.stringify(this.data))
                break;
            default:
                console.log('未识别其请求标识:this.method-->'+this.method)
                break;
        }
    }
}

//export{$et,$tag,Ajax,sendAjax,loginfo,logwarning}