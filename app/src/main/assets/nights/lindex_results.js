window.onload=card_skeleton(0,'');
callLoad();
//callServer('http://43.142.122.229/lindex.html?s=一')


//通知APP加载数据
function callLoad(){
    app.autoLoad()

}



var eurl=''
//传递参数
function getUrl(url){
    eurl=url;
    if(navigator.onLine){
        setTimeout(function(){
            callServer(eurl);
        },200)
    }else{
        card_skeleton(2,url);
    }
    
}

//骨架屏幕加载
function card_skeleton(mode,reloadTag){
    var cardLayout_root=document.getElementById('cardLayout');
    cardLayout_root.style.fontFamily="han";
    //var seleton_layout=document.getElementById('skeleton');
    var normal_skeleton='<div class="card m-2 border-0"><div class="card-header"><div class="row align-items-center"><div class="col-3"><figure class="text-center"><p class="text-primary" style="font-Size:46px;">...</p></figure></div><div class="col-9"><p>笔画：<span style="color:#495050;">...</span></p><p>普通话：<span style="color:#495050;">...</span></p></div></div></div><div class="card-body" style="color:#495050;"><div class="row"><div class="col">...</div></div></div></div>';
    var normal_skeleton_footer='<div class="row m-2"><div class="col p-0"><div class="accordion mt-2"><div class="accordion"><div class="accordion-item border-0"><button class="accordion-button btn p-3 collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#sw" aria-expanded="false">说文解字</button><div class="accordion-collapse collapse mt-0" id="sw"><div class="accordion-body">......</div></div></div></div></div><div class="accordion mt-2"><div class="accordion"><div class="accordion-item border-0"><button class="accordion-button btn p-3 collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#kx" aria-expanded="false"><h6>康熙字典</h6></button><div class="accordion-collapse collapse mt-0" id="kx"><div class="accordion-body">......</div></div></div></div></div><div class="accordion mt-2"><div class="accordion"><div class="accordion-item border-0"><button class="accordion-button btn p-3 collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#hd" aria-expanded="false"><h6>汉语字典</h6></button><div class="accordion-collapse collapse mt-0" id="hd"><div class="accordion-body">......</div></div></div></div></div><div class="accordion mt-2"><div class="accordion"><div class="accordion-item border-0"><button class="accordion-button btn p-3 collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#ltc_mc" aria-expanded="false"><h6>切韵</h6></button><div class="accordion-collapse collapse mt-0" id="ltc_mc"><div class="accordion-body">......</div></div></div></div></div></div>';
    var skeleton_failed='<div class="card m-2 border-0"><div class="card-header m-2"><div class="row"><div class="col"><h5 style="color:#495050;"><span class="text-primary text-bold">数据加载失败...</span></h5></div></div></div><div class="card-body  pt-0"><a class="btn btn-primary" href="hkapp://reload_/'+reloadTag+'">重新加载数据</a></div></div>'
    var skeleton_net_failure='<div class="card m-2 border-0"><div class="card-header m-2 "><div class="row"><div class="col"><h5 style="color:#495050;"><span class="text-primary text-bold">无法连接服务器</span></h5></div></div></div><div class="card-body text-white pt-0">连接断开了，请检查网络是否畅通.....<div class="w-100"></div><a class="btn btn-primary mt-2" href="hkapp://reload_/'+reloadTag+'">刷新重试</a></div></div>'
    switch(mode){
        case 0:
            //seleton_layout.innerHTML=normal_skeleton+normal_skeleton_footer;
            cardLayout_root.innerHTML=normal_skeleton+normal_skeleton_footer;
            break;
        case 1:
            //seleton_layout.innerHTML=skeleton_failed;
            cardLayout_root.innerHTML=skeleton_failed;
            break;
        case 2:
            cardLayout_root.innerHTML=skeleton_net_failure;
            break;
        default:
            //seleton_layout.innerHTML=normal_skeleton+normal_skeleton_footer;
            cardLayout_root.innerHTML=normal_skeleton+normal_skeleton_footer;
            break;
    }
    
}

//组装卡片布局
function mergeCardLayout(sjo){
    var arrays=window.app.returnAlls().split(',');
    var asr=[];
    for(var d=0;d<arrays.length;d++){
        var sd=parseInt(arrays[d])
        asr.push(sd);
    }
    var cardLayout_root=document.getElementById('cardLayout');
    var cards=generateCard(sjo);
    var foots=returnLists(sjo,asr);
    cardLayout_root.innerHTML=cards+foots;
    //cardLayout_root.style.fontFamily="han"
}

//卡片布局生成代码,根据sjo数据生成具体数据布局代码
function generateCard(sjo){
    var hz=sjo.hz;
    var bh=sjo.bh;
    var cmnp=sjo.cmn_p;

    var cardHead='<div class="card m-2 border-0">';
    var cardHeader='<div class="card-header">'+
                        '<div class="row align-items-center">'+
                            '<div class="col-3">'+
                                '<figure class="text-center"><p class="text-primary" style="font-Size:46px;" id="bighz">'+hz+'</p></figure>'+
                            '</div>'+
                            '<div class="col-9">'+
                                '<p class="normalText">笔画：<span class="text-white">'+bh+'</span></p>'+
                                '<p class="normalText">普通话：<span class="text-white">'+cmnp+'</span></p>'+
                            '</div>'+
                        '</div>'+
                    '</div>';

    var cardBody_head='<div class="card-body p-0 text-white" ><div class="row"><div class="col">';
    var cardBody_content=''+allHakkaspagers();
    var cardBody_end='</div></div></div>'; 
    var cardEnd='</div>';

    return cardHead+cardHeader+cardBody_head+cardBody_content+cardBody_end+cardEnd;
}

//生成各地客家话读音分布布局代码
function allHakkaspagers(){
    var alis=generateTab();
    var pagerTab='<div class="row align-items-start p-0">'+
                    '<div class="col-4"><div class="list-group p-0" id="areas">'+alis+'</div></div>'+
                    '<div class="col-8"><div class="list-group scrollsby-list" id="pagers"></div></div>'+
                 '</div>'
    return pagerTab;
}

function generateTab(){
    var alis=''
    var hanzs=['粤东','粤西','粤珠','粤北','广西','海南','川湘','江西','福建','台湾','海外','其他']
    //var hanzs_code=['jetd','jets','jetz','jetb','guongs','hoin','gongs','fukg','hoing','kit']
    for(var s=0;s<hanzs.length;s++){
        //onclick="changeSoundVisible()"
        if(s==0) var alist='<a class="list-group-item text-white border-start-0 border-top-0 border-bottom-0 rounded-0 text-center m-0 active" onclick="changeSoundVisible('+s+')">'+hanzs[s]+'</a>'
        else var alist='<a class="list-group-item text-white border-start-0 border-top-0 border-bottom-0 rounded-0 text-center m-0" onclick="changeSoundVisible('+s+')">'+hanzs[s]+'</a>'
        alis+=alist;
    }
    return alis;
}

//各地客家话读音页面
//sound为json模型，包含detailHakka和sou
function returnPager(sound){
    
    var names=sound.hknames;
    var pins=sound.hkpins
    //console.log(pins)
    var contents='';
    if(sound!=''){
        for(var f=0;f<names.length;f++){
            //添加origin-datas标签以保持转换前数据，方便请求服务器
            contents+='<li class="list-group-item normalText border-0 pe-s ps-0"><h6>'+names[f]+'</h6><p class="aslin p-0" style="letter-spacing:0.6px;">'+pins[f]+'<p/></li>'
        }
    }else{
        for(var f=0;f<10;f++){
            //添加origin-datas标签以保持转换前数据，方便请求服务器
            contents+='<li class="list-group-item border-0 normalText">第'+f+'客家读音:&nbsp;<a class="aslin">加载失败<a/></li>'
        }
    }

    return contents;
}

//客家话读音请求
function requesthakkas(){
    
    var pager_root=document.getElementById('pagers')
    var hz=document.getElementById('bighz').innerText;

    var type=app.returnHk_type()
    var toney=app.returnHk_toney()
    var request_url='http://43.142.122.229/lindex/getpins?value='+hz+'&hkarea=jetd&type='+type+'&toney='+toney;
    
    sendAjax(request_url,2000,
        (text)=>{
            var jsons=JSON.parse(text)
            console.log(jsons.hkpins)
            pager_root.innerHTML=returnPager(jsons)
        },
        ()=>{
            pager_root.innerHTML='<li class="list-group border-0 normalText"><p class="p-3">加载失败<br/><a class="btn btn-primary mt-2" onclick="changeSoundVisible(0)">请重试</a><p></li>'
        });
    /*var xmlhttp=new XMLHttpRequest();

    xmlhttp.timeout=1500
    xmlhttp.onreadystatechange=()=>{
        if(xmlhttp.readyState==4&&xmlhttp.status==200){
            var jsons=JSON.parse(xmlhttp.responseText)
            console.log(jsons.hkpins)
            pager_root.innerHTML=returnPager(jsons)
        }else if(xmlhttp==4&&xmlhttp.status!=200){
            pager_root.innerHTML='<li class="list-group border-0 normalText"><p class="p-3">加载失败<br/><a class="btn btn-primary mt-2" onclick="changeSoundVisible(0)">请重试</a><p></li>'
            
        }
    };

    xmlhttp.ontimeout=(e)=>{
        xmlhttp.abort()
        pager_root.innerHTML='<li class="list-group border-0 normalText"><p class="p-3">加载失败<br/><a class="btn btn-primary mt-2" onclick="changeSoundVisible(0)">请重试</a><p></li>'
        
    }

    xmlhttp.open('GET',requestApi);
    xmlhttp.send();*/
   
}


//读音显示格式切换请求API，通过请求服务器以获得转换后的数据，并重新加载
function changeSoundVisible(area_codes){
    var pager_root=document.getElementById('pagers')
    var hz=document.getElementById('bighz').innerText;

    var hanzs_code=['jetd','jets','jetz','jetb','guongs','hoin','gongs','fukg','hoing','kit']
    var type=app.returnHk_type()
    var toney=app.returnHk_toney()
    var area_code=''
    switch(area_codes){
        case 0:
            area_code=hanzs_code[area_codes];
            break;
        default:
            area_code='';
            break;

    }

    var sounds_root=document.getElementById('areas')
    var allsounds=sounds_root.getElementsByTagName('a')
    //遍历所有<li>标签以获得所有的读音数据
    for(var t=0;t<allsounds.length;t++){
        allsounds[t].classList.remove('active')
    }
    allsounds[area_codes].classList.add('active')

    pager_root.innerHTML='<li class="list-group border-0 normalText"><p class="p-3">数据加载中...<p></li>'


    //请求API，通过请求参数来请求不同的显示数据
    var api_request='http://43.142.122.229/lindex/getpins?value='+hz+'&hkarea='+area_code+'&type='+type+'&toney='+toney;
    
    if(area_code=='jetd'){
        sendAjax(api_request,2000,
            (text)=>{
                var jsons=JSON.parse(text)
                //console.log(jsons.hkpins)
                pager_root.innerHTML=returnPager(jsons)
            },
            ()=>{
                pager_root.innerHTML='<li class="list-group border-0 normalText"><p class="p-3">加载失败<br/><a class="btn btn-primary mt-2" onclick="changeSoundVisible('+area_codes+')">请重试</a><p></li>'
            })
    }else{
        pager_root.innerHTML='<li class="list-group-item border-0 normalText "><h6 class="p-3">内容为空...<br/>管理员太懒了orz<h5/></li>'
    }
    /*var xmlhttp=new XMLHttpRequest()
    xmlhttp.ontimeout=200;

    xmlhttp.ontimeout=(e)=>{
        xmlhttp.abort()
        pager_root.innerHTML='<li class="list-group border-0 normalText"><p class="p-3">加载失败<br/><a class="btn btn-primary mt-2" onclick="changeSoundVisible('+area_codes+')">请重试</a><p></li>'
    }

    //请求API，通过请求参数来请求不同的显示数据
    var api_request='http://43.142.122.229/lindex/getpins?value='+hz+'&hkarea='+area_code+'&type='+type+'&toney='+toney;
    xmlhttp.onreadystatechange=function(){
        if(xmlhttp.readyState==4&&xmlhttp.status==200){
            var jsons=JSON.parse(xmlhttp.responseText)
            //console.log(jsons.hkpins)
            pager_root.innerHTML=returnPager(jsons)
        }else if(xmlhttp==4&&xmlhttp.status!=200){
            console.log('failed')
            pager_root.innerHTML='<li class="list-group border-0 normalText"><p class="p-3">加载失败<br/><a class="btn btn-primary mt-2" onclick="changeSoundVisible('+area_codes+')">请重试</a><p></li>'
        }
    };

    if(area_code=='jetd'){
        if(navigator.onLine){
            setTimeout(()=>{
                xmlhttp.open('GET',api_request);
                xmlhttp.send();
            },100)
        }else{
            setTimeout(()=>{
                pager_root.innerHTML='<li class="list-group border-0 normalText"><p class="p-3">加载失败<br/><a class="btn btn-primary mt-2" onclick="changeSoundVisible('+area_codes+')">请重试</a><p></li>'
            },200)
        }

    }else{
        pager_root.innerHTML='<li class="list-group-item border-0 normalText "><h6 class="p-3">内容为空...<br/>管理员太懒了orz<h5/></li>'
    }*/

    
}


var direction=['','vertext']
var vertainer=['','verticaline']
var swfonts=['swfont-lg','swfont-md','swfont-sm']
var unifonts=['unifont-lg','unifont-md','unifont-sm']
var lettersp=['letters-lg','letters-md','letters-sm']
var lineheight=['lh-lg','','lh-sm']
var bgcolor=['bgcolor-white','bgcolor-yellow','bgcolor-gray']
//额外卡片，比如说文解字等等根组件代码,根据aso进行解析生成布局
function returnLists(aso,arrals){
    var swext='';
    var kxext='';
    var hdext='';
    var ltc_mc='';
    var sw=aso.sw.replace(/[\r\n]/g,'<br/>');
   // var rex=/(?:`)(.*)(?:`)(?=<br\/>)/ig
    //sw=sw.replace(rex,'<small>$1</small>')
    var kx=aso.kx.replace(/[\r\n]/g,'<br/>');
    var hd=aso.hd.replace(/[\r\n]/g,'<br/>');
    var ltcmc=aso.ltc_mc.replace(/[\r\n]/g,'<br/>');


    if(!sw=='') swext+=generateLists('sw',"说文解字",sw,vertainer[arrals[0]],direction[arrals[0]],swfonts[arrals[1]],lettersp[arrals[2]],lineheight[arrals[3]],bgcolor[3]);
    if(!kx=='') kxext+=generateLists('kx',"康熙字典",kx,vertainer[arrals[5]],direction[arrals[5]],unifonts[arrals[6]],lettersp[arrals[7]],lineheight[arrals[8]],bgcolor[3]); 
    if(!hd=='') hdext+=generateLists('hd',"汉语大字典",hd,vertainer[arrals[10]],direction[arrals[10]],unifonts[arrals[11]],lettersp[arrals[12]],lineheight[arrals[13]],bgcolor[3]);
    if(!ltcmc=='') ltc_mc=generateLists('ltc','切韵',ltcmc,vertainer[arrals[15]],direction[arrals[15]],unifonts[arrals[16]],lettersp[arrals[17]],lineheight[arrals[18]],bgcolor[3]);
    
    var List_Head='<div class="row m-2"><div class="col pb-2 pe-0 ps-0">';
    var List_End='</div></div>';
    return List_Head+swext+kxext+hdext+ltc_mc+List_End;
}


//生成说文、康熙、汉语字典等解释卡片，注意这几个卡片是独立存在的
//bg背景颜色，mode竖排或横排容器，vertext竖排或横排，lt字距，lhw行距
function generateLists(list_id,list_name,content,mode,vertext,ft,lt,lhw,bg){
    all=ft+' '+lt+' '+lhw


    var List_item='<div class="accordion mt-2">'+
                        '<div class="accordion">'+
                            '<div class="accordion-item border-0">'+
                                '<button class="accordion-button btn p-3 collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#'+list_id+'" aria-expanded="false">'+list_name+'</button>'+
                                '<div class="accordion-collapse collapse mt-0 '+bg+'" id="'+list_id+'">'+
                                    '<div class="accordion-body '+mode+'"><p class="'+vertext+" "+all+'">'+content+'<p></div>'+
                                '</div>'+
                            '</div>'+
                        '</div>'+
                    '</div>';
            
    return List_item;
}

//请求服务器发送数据，并进行解析返回生成布局
function callServer(url){
    //console.log("已经启动....");
    AsyncJSON(url);
}

var aqo=''
//处理具体请求逻辑
function AsyncJSON(url){

    sendAjax(url,2000,
        (text)=>{
            var joss=JSON.parse(text);
            //生成具体客家话读音布局
            mergeCardLayout(joss);
            requesthakkas();
        },
        ()=>{
            setTimeout(function(){
                card_skeleton(0,url);
               },300);
        })
    /*var xmlhttp=new XMLHttpRequest();

    xmlhttp.timeout=4600
    xmlhttp.onreadystatechange=function(){
        if(xmlhttp.readyState==4&&xmlhttp.status==200){

            aqo=xmlhttp.responseText
            var joss=JSON.parse(xmlhttp.responseText);

            //生成具体客家话读音布局
            mergeCardLayout(joss);
            requesthakkas();
        }else if(xmlhttp.readyState==4&&xmlhttp.status!=200){
           setTimeout(function(){
            card_skeleton(0,url);
           },400);
        }
    };

    xmlhttp.ontimeout=function(e){
        xmlhttp.abort()
        setTimeout(function(){
            card_skeleton(1,url);
           },400);
           xmlhttp.abort()
    }
    xmlhttp.open('GET',url)
    xmlhttp.send();*/
}