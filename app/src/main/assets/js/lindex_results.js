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

function card_tepmplate_(type,reloadTag,body_code){
    var card_skeleton_one=
            `<div class="card m-2 border-0">
                <div class="card-header m-2 ">
                    <div class="row g-2">
                        <div class="col-3 align-self-center">
                            <p class="ps-2 pe-2 card_primary" style="font-Size:46px;">...</p>
                        </div>
                        <div class="col-9">
                            <p class="m-1">笔画：<span class="card_text">...</span></p>
                            <p class="m-1">部首：<span class="card_text">...</span></p>
                            <p class="m-1">普通话：<span class="card_text">...</span></p>
                            <p class="m-1">异体字：<span class="card_text">...</span></p>
                        </div>
                    </div>
                </div>
                <div class="card-footer border-0 p-0 ">
                    <div class="row mb-2">
                        <div class="col ms-3 p-2">
                            ${reloadTag}
                        </div>
                    </div>
                </div>
            </div>`;

    var card_skeleton_two=
        `<div class="card m-2 border-0">
            <div class="card-header m-2 ">
                <div class="row">
                    <div class="col">
                        <h5 class="card_text">
                            <span class="card_primary text-bold">${reloadTag}</span>
                        </h5>
                    </div>
                </div>
            </div>
            <div class="card-body  pt-0">
                ${body_code}
            </div>
        </div>`;

        switch(type){
            case 0:
                return card_skeleton_one;
            case 1:
                return card_skeleton_two;
        }
}

//骨架屏幕加载
function card_skeleton(mode,reloadTag){
    var cardLayout_root=$et('cardLayout');
    cardLayout_root.style.fontFamily="han";

    var alls=['说文解字','康熙字典','汉语字典','切韵']
    var alls_c=['sw','kx','hd','ltc']
    var accordions=''
    for(var i=0;i<4;i++){
        var all=alls[i]
        var all_c=alls_c[i]
        var accordio=
            `<div class="accordion mt-2">
                <div class="accordion">
                    <div class="accordion-item border-0">
                        <button class="accordion-button btn p-3 collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#${all_c}" aria-expanded="false"><h6>${all}</h6></button>
                        <div class="accordion-collapse collapse mt-0" id="${all_c}"><div class="accordion-body">......</div>
                    </div>
                </div>
            </div>`
            accordions+=accordio
    }
    var normal_skeleton_footer=
            `<div class="row m-2">
                <div class="col p-0">
                ${accordions}
                </div>
            </div>`;

    var normal_skeleton=card_tepmplate_(0,'数据加载中...','')+normal_skeleton_footer
    var skeleton_failed=`
            <div class="card m-2 border-0">
                <div class="card-header m-2 ">
                    <div class="row">
                        <div class="col">
                            <h5 class="card_text">
                                <span class="card_primary text-bold">数据加载失败...</span>
                            </h5>
                        </div>
                    </div>
                </div>
                <div class="card-body  pt-0">
                    <a class="btn btn-primary" href="hkapp://reload_/${reloadTag}">重新加载数据</a>
                </div>
            </div> `

    var skeleton_net_failure=`
            <div class="card m-2 border-0">
                <div class="card-header m-2 ">
                    <div class="row">
                        <div class="col">
                            <h5 class="card_text">
                                <span class="card_primary text-bold">无法连接服务器</span>
                            </h5>
                        </div>
                    </div>
                </div>
                <div class="card-body  pt-0">连接断开了，请检查网络是否畅通.....<div class="w-100"></div><a class="btn btn-primary mt-2" href="hkapp://reload_/${reloadTag}">刷新重试</a>
                </div>
            </div>`

    switch(mode){
        case 0:
            cardLayout_root.innerHTML=normal_skeleton;
            break;
        case 1:
            cardLayout_root.innerHTML=skeleton_failed;
            break;
        case 2:
            cardLayout_root.innerHTML=skeleton_net_failure;
            break;
        default:
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
    var cardLayout_root=$et('cardLayout');
    var cards=generateCard(sjo);
    var foots=returnLists(sjo,asr);
    cardLayout_root.innerHTML=cards+foots;
}

//卡片布局生成代码,根据sjo数据生成具体数据布局代码
function generateCard(sjo){
    var hz=sjo.hz;
    var bh=sjo.bh;
    var ps=sjo.ps;
    var cmnp=(sjo.cmn_p!='')?sjo.cmn_p:'缺';
    var vas=sjo.va;
    var vas_ext=(vas!=null)?`<p class="m-1">异体字：<span class="card_text">${vas}</span></p>`:'';
    var cardBody_content=''+allHakkaspagers();
    var card_=
                `<div class="card m-2 border-0">
                    <div class="card-header ">
                        <div class="row align-items-center">
                            <div class="col-3">
                                <figure class="text-center"><p class="card_primary" style="font-Size:46px;" id="bighz">${hz}</p></figure>
                            </div>
                            <div class="col-9">
                                <p class="m-1">笔画：<span class="card_text">${bh}</span></p>
                                <p class="m-1">部首：<span class="card_text">${ps}</span></p>
                                <p class="m-1">普通话：<span class="card_text">${cmnp}</span></p>
                                ${vas_ext}
                            </div>
                        </div>
                    </div>

                    <div class="card-body p-0" class="card_text">
                                ${cardBody_content}
                    </div>
                </div>`;


    return card_;
}

//生成各地客家话读音分布布局代码
function allHakkaspagers(){
    var alis=generateTab();
    var pagerTab=`<div class="row g-0">
                    <div class="col-4">
                        <div class="list-group p-0" id="areas">${alis}</div>
                    </div>
                    <div class="col-8">
                        <div class="list-group scrollsby-list ps-2 pt-0 m-0" id="pagers">
                        </div>
                    </div>
                 </div>`
    return pagerTab;
}

function generateTab(){
    var alis=''
    var hanzs=['粤东','粤西','粤珠','粤北','广西','海南','川湘','江西','福建','台湾','海外','其他']
    //var hanzs_code=['jetd','jets','jetz','jetb','guongs','hoin','gongs','fukg','hoing','kit']
    for(var s=0;s<hanzs.length;s++){
        //onclick="changeSoundVisible()"
        if(s==0) var alist='<a class="list-group-item border-start-0 border-top-0 border-bottom-0 rounded-0 text-center m-0 active" onclick="changeSoundVisible('+s+')">'+hanzs[s]+'</a>'
        else var alist='<a class="list-group-item border-start-0 border-top-0 border-bottom-0 rounded-0 text-center m-0" onclick="changeSoundVisible('+s+')">'+hanzs[s]+'</a>'
        alis+=alist;
    }
    return alis;
}



//各地客家话读音页面
//sound为json模型，包含detailHakka和sou
var source_ext=""
function returnPager(sound){
    //console.log(sound)
    var names=sound.hknames;
    var pins=sound.hkpins
    var source=sound.source
    var tones_=sound.tones
    
    source_ext=""
    for(var t=0;t<names.length;t++){
        //<li><font color="#d63384;"></font></li>
        var tource_=tones_[t].replace(/^\d/,'第1声 ')
        var ource_=tource_.replace(/,(\d)/g,'<br/>第$1声 ');
        source_ext+=`${names[t]}：<font color="#d63384">${source[t].replace('来源：','')}</font><br/><font color="#495050">声调</font><br/><small>${ource_}</small><br/><br/>`
   }
   
    var contents='';

    if(sound!=''){
         contents+='<li class="list-group-item normalText border-0 p-0 m-0"><a onclick="CheckSource()" class="float-end">数据来源&nbsp;</a></li>'
        for(var f=0;f<names.length;f++){
            //添加origin-datas标签以保持转换前数据，方便请求服务器
            contents+=`
                    <li class="list-group-item normalText border-0 p-0">
                        <h6>
                            ${names[f]}
                        </h6>
                        <p class="aslin p-0 m-0" style="letter-spacing:0.6px;">${pins[f]}<p/>
                    </li>`
        }
    }else{
        for(var f=0;f<10;f++){
            //添加origin-datas标签以保持转换前数据，方便请求服务器
            contents+='<li class="list-group-item border-0 normalText">第'+f+'客家读音:&nbsp;<a class="aslin">加载失败<a/></li>'
        }
    }


    return contents;
}

function CheckSource(){
    //console.log("checkSource_ls:"+source_ext)
    app.showsource(source_ext)
}

//客家话读音请求
function requesthakkas(){
    
    var pager_root=$et('pagers')
    var hz=$et('bighz').innerText;

    var type=app.returnHk_type()
    var toney=app.returnHk_toney()
    var request_url=`http://dict.hazukieq.top/searchapis/getpins?value=${hz}&hkarea=jetd&type=${type}&toney=${toney}`;//'http://dict.hazukieq.top/lindex/getpins?value='+hz+'&hkarea=jetd&type='+type+'&toney='+toney;

    sendAjax(request_url,2000,
        (text)=>{
            var jsons=JSON.parse(text)
            pager_root.innerHTML=returnPager(jsons)
        },
        ()=>{
            pager_root.innerHTML='<li class="list-group normalText border-0 p-0 m-0"><p class="p-3">加载失败<br/><a class="btn btn-primary mt-2" onclick="changeSoundVisible(0)">请重试</a><p></li>'
        });
}

//读音显示格式切换请求API，通过请求服务器以获得转换后的数据，并重新加载
function changeSoundVisible(area_codes){
    var pager_root=$et('pagers')
    var hz=$et('bighz').innerText;
    var hanzs_code=['jetd','jets','jetz','jetb','gongs','hoin','cont','gongx','fukg','toiv','hoing','kit']
    var type=app.returnHk_type()
    var toney=app.returnHk_toney()
    var area_code=''
    switch(area_codes){
        case 0:
        case 1:
        case 3:
        case 4:
        case 7:
        case 8:
        case 10:
            area_code=hanzs_code[area_codes];
            break;
        default:
            area_code='';
            break;

    }

    var sounds_root=$et('areas')
    var allsounds=$tag(sounds_root,'a')
    //遍历所有<li>标签以获得所有的读音数据
    for(var t=0;t<allsounds.length;t++){
        allsounds[t].classList.remove('active')
    }
    allsounds[area_codes].classList.add('active')

    pager_root.innerHTML='<li class="list-group border-0 normalText"><p class="p-3">数据加载中...<p></li>'


    //请求API，通过请求参数来请求不同的显示数据
    var api_request=`http://dict.hazukieq.top/searchapis/getpins?value=${hz}&hkarea=${area_code}&type=${type}&toney=${toney}`;//'http://dict.hazukieq.top/lindex/getpins?value='+hz+'&hkarea='+area_code+'&type='+type+'&toney='+toney;

    if(area_code!=''){
        sendAjax(api_request,2000,
            (text)=>{
                var jsons=JSON.parse(text)
                //console.log(jsons.hkpins)
                pager_root.innerHTML=returnPager(jsons)
            },
            ()=>{
                pager_root.innerHTML='<li class="list-group normalText border-0 p-0 m-0"><p class="p-3">加载失败<br/><a class="btn btn-primary mt-2" onclick="changeSoundVisible('+area_codes+')">请重试</a><p></li>'
            })
    }else{
        pager_root.innerHTML='<li class="list-group-item normalText border-0 p-0 m-0"><h6 class="p-3">内容为空...<br/>管理员太懒了orz<h5/></li>'
    }
    
}


//额外卡片，比如说文解字等等根组件代码,根据aso进行解析生成布局
var direction=['','vertext']
var vertainer=['','verticaline']
var swfonts=['swfont-lg','swfont-md','swfont-sm']
var unifonts=['unifont-lg','unifont-md','unifont-sm']
var lettersp=['letters-lg','letters-md','letters-sm']
var lineheight=['lh-lg','','lh-sm']
var bgcolor=['bgcolor-white','bgcolor-yellow','bgcolor-gray']
function returnLists(aso,arrals){
    var swext='';
    var kxext='';
    var hdext='';
    var ltc_mcext='';
    var sw=aso.sw.replace(/[\r\n]/g,'<br/>');
    var kx=aso.kx.replace(/[\r\n]/g,'<br/>');
    var hd=aso.hd.replace(/[\r\n]/g,'<br/>');
    var ltcmc=aso.ltc_mc.replace(/[\r\n]/g,'<br/>');


    if(!sw=='') swext+=generateLists('sw',"说文解字",sw,vertainer[arrals[0]],direction[arrals[0]],swfonts[arrals[1]],lettersp[arrals[2]],lineheight[arrals[3]],bgcolor[arrals[4]]);
    if(!kx=='') kxext+=generateLists('kx',"康熙字典",kx,vertainer[arrals[5]],direction[arrals[5]],unifonts[arrals[6]],lettersp[arrals[7]],lineheight[arrals[8]],bgcolor[arrals[9]]); 
    if(!hd=='') hdext+=generateLists('hd',"汉语大字典",hd,vertainer[arrals[10]],direction[arrals[10]],unifonts[arrals[11]],lettersp[arrals[12]],lineheight[arrals[13]],bgcolor[arrals[14]]);
    if(!ltcmc=='') ltc_mcext=generateLists('ltc','切韵',ltcmc,vertainer[arrals[15]],direction[arrals[15]],unifonts[arrals[16]],lettersp[arrals[17]],lineheight[arrals[18]],bgcolor[arrals[19]]);
    
    var List_='<div class="row m-2"><div class="col pb-2 pe-0 ps-0">'
    var List_end='</div></div>';
    return List_+swext+kxext+hdext+ltc_mcext+List_end;
}


//生成说文、康熙、汉语字典等解释卡片，注意这几个卡片是独立存在的
//bg背景颜色，mode竖排或横排容器，vertext竖排或横排，lt字距，lhw行距
function generateLists(list_id,list_name,content,mode,vertext,ft,lt,lhw,bg){
    all=ft+' '+lt+' '+lhw
    var List_item=`<div class="accordion mt-2">
                        <div class="accordion">
                            <div class="accordion-item border-0">
                                <button class="accordion-button btn p-3 collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#${list_id}" aria-expanded="false">${list_name}</button>
                                <div class="accordion-collapse collapse mt-0 ${bg}" id="${list_id}">
                                    <div class="accordion-body ${mode}"><p class="${vertext} ${all}">${content}<p></div>
                                </div>
                            </div>
                        </div>
                    </div>`;
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
}

