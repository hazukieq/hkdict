//加载结束时调用
window.onload=function(){
    app.LoadSettings();
}


//打开设置界面
function openSettings(){

}

//读取默认设置属性值，并根据其进行设置
function getSettingParams(ints){
    var input=document.getElementById('search');
    if(ints==0){
        input.removeAttribute('oninput');
    }
}
//打开侧边菜单栏
function openLeftDrawer(){
  app.openDrawer();
}

//一键复制文本
function copytxt(str){
   //console.log('copy_text-->'+str);
   app.copyAll(str);
}


//APP设置预览音节模式是否开启
function setpinPreview(isSwitch){
  //  var lswitch=document.getElementById('pin_switch');
    var input=document.getElementById('search');
    var preview_root=document.getElementById('convertVisible');
    clearPreviews(preview_root);

    if(isSwitch==0){
        input.removeAttribute('oninput');
    }else if(isSwitch==1){
        input.setAttribute('oninput','previewVisibleOrNot()');
    }
}




//监测输入框输入，当输入数据类型为普通话拼音时自动显示预览音节
function previewVisibleOrNot(){
    var inputd=document.getElementById('search');
    var wantConvertStr=inputd.value;
    var input_format=document.getElementById('selectop').getAttribute('datas');

    var convertVisible_root=document.getElementById('convertVisible_root'); 

    if(input_format=='cmnp'){
        if(wantConvertStr.includes(' ')){
            wantConvertStr=wantConvertStr.replace(/x $/,'1')
            wantConvertStr=wantConvertStr.replace(/q $/,'2')
            wantConvertStr=wantConvertStr.replace(/h $/,'3')
            wantConvertStr=wantConvertStr.replace(/s $/,'4')
        }
    }

    inputd.value=wantConvertStr

    var wantConvertSt=inputd.value;
    switch(input_format){
        case 'hz':
        case 'cmnipa':
        case 'hkipa':
        case 'moi':
        case 'uni':
            convertVisible_root.setAttribute('class','mt-1 col visually-hidden');
            break;
            //convertVisible_root.setAttribute('class','mt-1 col');
            //调用转换函数
            //loadInputpreview(wantConvertStr,0);
            //break;
        case 'cmnp':
            convertVisible_root.setAttribute('class','mt-1 col');
            //调用转换函数
            var regex=/[\u4e00-\u9fff`~@#*()_+<>:"\/^%&',.;=?$[\]{}～，、：‘’‵（）。；？“”！]{1,}/g
            if(regex.test(wantConvertSt))
                convertVisible_root.setAttribute('class','mt-1 col visually-hidden');
            else
                loadInputpreview(wantConvertSt,1);
            break;
    }
    //当输入为空时，预览音节组件自动隐藏
    if(wantConvertSt=='') convertVisible_root.setAttribute('class','mt-1 col visually-hidden');
}

//清除组件函数
function clearPreviews(arg){
    //清除任何之前存在的组件；
    let ele;
    while((ele=arg.firstChild)){
        ele.remove();
    }
}

//控制预览音节组件隐藏
function controlPreviewHidden(){
    var convertVisible_root=document.getElementById('convertVisible_root');
    convertVisible_root.setAttribute('class','mt-1 col visually-hidden');
}

//传入参数到预览框，并设置点击事件
function loadInputpreview(arg,mode){
    //为防止之前存在有预览音节组件，所以需要调用清除函数移除存在的所有标签
    var preview_root=document.getElementById('convertVisible');
    clearPreviews(preview_root);

    var preview_layout=document.createElement('div');
    preview_layout.setAttribute('class','alert alert-info alert-dismissible fade show');
    preview_layout.setAttribute('role','alert');
    //var preview_text=document.createElement('p');
    var convertedArg='';
    if(mode==1){
       convertedArg+=convertCPin(arg);
    }
    preview_layout.innerHTML='<h6>音节预览</h6>'+
                            '<p class="text-black">'+convertedArg+'<p>'+
                            '<button type="button" class="btn-close btn" data-bs-dismiss="alert" aria-label="Close"></button>';
    preview_root.appendChild(preview_layout);
}


//控制输入框提示语切换
function changeInputHints(data){
    controlPreviewHidden();
    var input=document.getElementById('search');
    //判断传入类型；
    var type=data.getAttribute('datas')
    var button=document.getElementById('selectop');

    var inputhead='请输入';
    switch(type){
        case 'hz':
        case 'cmnp':
        case 'cmnipa':
            controlAppendsVisibleOrNot(0);
            break;
        //case 'moi':
        case 'uni':
        case 'hkipa':
            controlAppendsVisibleOrNot(1);
            break;
        default:
            break;
    }
    input.setAttribute('placeholder',inputhead+data.innerHTML);
    button.setAttribute('datas',type);
    button.innerHTML=data.innerHTML;
}

//控制附加查询组件(第二个筛选布局)显示
function controlAppendsVisibleOrNot(tyep){
    var willVisibleView=document.getElementById('filters');
    let les;
    while((les=willVisibleView.firstChild)){
     les.remove();
    }

   //0为不显示；1为显示；
   switch(tyep){
    case 0:
        break;
    case 1:
       categoryLayout(willVisibleView)
        break;
   }

}

//查询条件布局代码
function categoryLayout(willVisibleView){
    var firstRow=document.createElement('div');
        firstRow.setAttribute('class','row g-3');
        var secondCol4=document.createElement('div');
        secondCol4.setAttribute('class','col-4');
        secondCol4.innerHTML='<a class="text-light btn p-2">查询条件</a>';

        var secondCol8=document.createElement('div');
        secondCol8.setAttribute('class','col-8');


        //标记tag，默认为梅县客家话(moihk)
        var tag=' datas="jetd_gajin_hk"';
        var details_head_head='<div class="dropdown">'+
                                    '<button class="btn dropdown-toggle p-2 text-light" data-bs-toggle="dropdown" style="letter-spacing: 2px;" id="detailhakkas"'+tag+'>';
        //当有额外需求时可在此载入数据；默认为梅县客家话；
        var details_head_body='粤东嘉应客语';
        var details_head_end='</button>';

        var details_end_head='<ul class="dropdown-menu border-0" id="cates">';
        var details_end_body='';
        var details_end_end='</ul></div>';

        var dil=['粤东嘉应客语']
        var il=['jetd_gajin_hk']
        //这里传入解析数据；
        for(var i=0;i<1;i++){
            //载入数据节点；
            let hk_category=dil[i];
            details_end_body+='<li>'+
                                '<a class="dropdown-item" onclick="changeCategoryHint(this)" href="javascript:void(0)" datas="'+il[i]+'">'+hk_category+'</a>'+
                                '</li>';
        }

        secondCol8.innerHTML=details_head_head+details_head_body+details_head_end+details_end_head+details_end_body+details_end_end;
        firstRow.appendChild(secondCol4);
        firstRow.appendChild(secondCol8);
        willVisibleView.appendChild(firstRow);
        
        AsyncCategories()

}

//控制查询条件显示
function changeCategoryHint(datas){
    var detailbutton=document.getElementById('detailhakkas');

    var tag=datas.getAttribute('datas');
    detailbutton.setAttribute('datas',tag);
    detailbutton.innerHTML=datas.innerHTML;
}


//获取搜索框数据和筛选条件，传递搜索参数
function getInput(){
    var input=document.getElementById('search').value;
    var selectop=document.getElementById('selectop');
    //第一个参数value，表示实际输入字符；format表示输入格式；appendArea表示第二层分类；
    var first_arg='';

    if(input=='') first_arg='?value=kull';
    else first_arg='?value='+input;
   
    var type=selectop.getAttribute('datas');
    var second_arg='';
    var isAccess=true;
    var infos=document.getElementById('infos')

    switch(type){
        case 'hz':
            var lregex=/[A-Za-z0-9`~@#*()_+<>:"\/^%&',.;=?${}～，、：‘’‵（）。；？“”！]{1,}/g
            if(lregex.test(input)){
                //alert('检测到非法字符，请输入汉字！')
                infos.innerHTML='<div class="alert alert-info alert-dismissible fade show m-0 p-2" role="alert" id="infod">检测到非法字符，请输入汉字！'
                                +'</div>'

            setTimeout(()=>{
                var myAlert = document.getElementById('infod')
                var bsAlert = new bootstrap.Alert(myAlert)
                bsAlert.close()
            },2500)
            isAccess=false;
            }
            break;

        case 'uni':
        case 'hkipa':
        case 'cmnp':
        case 'cmnipa':
            var regex=/[\u4e00-\u9fff`~@#*()_+<>:"\/^%&',.;=?$[\]{}～，、：‘’‵（）。；？“”！]{1,}/g
            if(regex.test(input)){
                //alert('检测到非法字符，只能输入字母和数字！')
                infos.innerHTML='<div class="alert alert-info alert-dismissible fade show m-0 p-1" role="alert" id="infod">检测到非法字符，只能输入字母和数字！'
                            +'</div>'
                setTimeout(()=>{
                    var myAlert = document.getElementById('infod')
                    var bsAlert = new bootstrap.Alert(myAlert)
                    bsAlert.close()
                },2500)
                isAccess=false
            break;
    }

}


    var url='';
    if(isAccess){
        url=combinationUrl(first_arg,type,second_arg)
    }
    else url='kull'

    return url
}

//生成请求链接
function combinationUrl(first_arg,type,second_arg){
    var selectop=document.getElementById('selectop');
    switch(type){
        case 'hz':
            first_arg+='&format=hz&appendArea=kull';
            break;
        case 'hkipa':
        case 'moi':
        case 'uni':
            var detailhakka=document.getElementById('detailhakkas');
            first_arg+='&format='+selectop.getAttribute('datas');
            second_arg+='&appendArea='+detailhakka.getAttribute('datas');
            break;
        case 'cmnipa':
        case 'cmnp':
            first_arg+='&format='+selectop.getAttribute('datas')+'&appendArea=kull';
            break;
        default:
    }

    //这里写入网站api；
    var final_url=''
    if(second_arg==''){
        final_url+=first_arg;
    }else{
        final_url+=first_arg+second_arg;
    }
    return final_url;

}


//请求服务器数据，并解析JSON数据
function AlinkService(){
    //获取输入文本，向服务器发送请求
    var url=getInput();
    if(url!='kull'){
        //这里写入网站api；
        var final_url=''
        if (url.includes('format=hz&appendArea=kull')){
            final_url='http://43.142.122.229/lindex/search_hz'+url;
        }
        else{
            final_url='http://43.142.122.229/lindex/search_pin'+url;
        }
    
        if(navigator.onLine){
            AsyncJSON(final_url);
        }else{
            setTimeout(()=>{
                returnSkeleton(4,final_url);
            },200);
        }
    }
}


var jos='';
function AsyncJSON(ue){

    if(ue.includes("format=hz&appendArea=kull")){
        returnSkeleton(1,'');
    }else{
        returnSkeleton(2,'');
    }

    //参数url,timeout,success_callback,failed_callback
    sendAjax(ue,2500,

        //请求成功
        (text)=>{
        jos=JSON.parse(text);
        //console.log('--jos:'+jos.hz)
        setTimeout(()=>{
        controlResultLayout(jos)
        },100);
    },
        //请求失败
        ()=>{
            if(!navigator.onLine){
                setTimeout(()=>{
                    returnSkeleton(4,ue);
                },200);
            }else{
                setTimeout(()=>{
                    returnSkeleton(3,ue);
               },200);
        }
    });
}

//请求动态客家话分类数据，返回JSON数据
function AsyncCategories(){
    var xmlhttp=new XMLHttpRequest()
    
    var hks=''
    var uls=document.getElementById('cates');
    var body=''

    var ajax=new Ajax({
        method:'GET',
        url:'http://43.142.122.229/lindex/gethkarea',
        data:'',
        success:(text)=>{
            hks=JSON.parse(text)
            var dil=hks.names
            var il=hks.codes
            //这里传入解析数据；
            for(var i=0;i<hks.names.length;i++){
                //载入数据节点；
                let hk_category=dil[i];
                body+='<li>'+
                        '<a class="dropdown-item" onclick="changeCategoryHint(this)" href="javascript:void(0)" datas="'+il[i]+'">'+hk_category+'</a>'+
                       '</li>';
            }
            uls.innerHTML=body;
        },
        failure:()=>{

        },
        timeout:1000
    })
    ajax.send()
}


//清空搜索布局中组件

//控制搜索布局返回结果
function controlResultLayout(jso){

    //控制显示模式，1为汉字模式，0为音节模式；jso为原生jsons，利于不同模式解析不同数据；
    //var chs=generateJson(jso);
    switch(jso.input_type){
        case "0":
            resultLayout.innerHTML=HanzCards(jso);
            break;
        case "1":
            resultLayout.innerHTML=PinzCards(jso);
            break;
    }
}


//加载完成前展示骨架屏幕；加载失败时也时如此；
function returnSkeleton(mode,url){
    var resultLayout=document.getElementById('resultLayout');
    var loadTag='数据加载中';
    var reloadTag='hkapp://reload_/'+url;
    //mode分为1，2，3三种；第一种用于显示汉字卡片，第二种用于显示音节卡片，第三种用于显示网络加载失败时的卡片；
    var skeleton_one='<div class="card m-2 border-0"><div class="card-header m-2"><div class="row g-2"><div class="col-3 align-self-center"><p class="ps-2 pe-2 text-primary" style="font-Size:46px;">...</p></div><div class="col-9"><p>笔画：<span style="color:#495050;">...</span></p><p>普通话：<span class="text-white">...</span></p></div></div></div><div class="card-footer border-0"><div class="row mb-2 p-0"><div class="col-8"></div><div class="col-4 p-0"><a class="btn text-primary p-2" href="'+reloadTag+'">'+loadTag+'</a></div></div></div></div>';
    var skeleton_tow='<div class="card m-2 border-0"><div class="card-header m-2"><div class="row"><div class="col"><h5><span class="text-primary text-bold">数据加载中</span></h5></div></div></div><div class="card-body pt-0"><p>......</p></div></div>';
    var skeleton_three='<div class="card m-2 border-0"><div class="card-header m-2"><div class="row"><div class="col"><h5><span class="text-primary text-bold">数据加载失败...</span></h5></div></div></div><div class="card-body pt-0"><a class="btn btn-primary" href="'+reloadTag+'">重新加载数据</a></div></div>'
    var skeleton_failure='<div class="card m-2 border-0"><div class="card-header m-2 "><div class="row"><div class="col"><h5><span class="text-primary text-bold">无法连接服务器</span></h5></div></div></div><div class="card-body  pt-0 text-white">连接断开了，请检查网络是否畅通.....<div class="w-100"></div><a class="btn btn-primary mt-2" href="'+reloadTag+'">刷新重试</a></div></div>'
    switch(mode){
        case 1:
            resultLayout.innerHTML=skeleton_one;
            break;
        case 2:
            resultLayout.innerHTML=skeleton_tow;
            break;
        case 3:
            resultLayout.innerHTML=skeleton_three;
            break;
        case 4:
            resultLayout.innerHTML=skeleton_failure
            break;
    }
}

//生成汉字搜索布局
function HanzCards(chs){
    var card=ResultCard(chs);
    return card;
}

//生成音节搜索布局
function PinzCards(chz){

    var pincard=ResultPinCard(chz);
    return pincard;
}


//单字卡片布局,注意chract是对象
function ResultCard(chract){

    var cardHead='<div class="card m-2 border-0">';
    var cardEnd='</div>'
    var cardHeader=''
    var cardBody=''


if(chract.hz=='...'){
    cardHeader='<div class="card-header m-2"><h5 class="text-light">加载失败</h5></div>'
    cardBody='<div class="card-footer border-0 p-0">'+
                    '<div><p class="text-danger p-2">数据库找不到相关数据哦~</p></div>'+
                 '</div>'
}else{
    //详情查看链接；默认请求url为：api_url+"?value=空&format=hz"; APP拦截协议链接为app://detail?value=空&format=hz;
    var hz=chract.hz;
    var bh=chract.bh;
    var cmn=chract.cmn_p;
    var detail_link=chract.link;

    //卡片头部
    var cardHeader='<div class="card-header m-2">'+
    '<div class="row g-2">'+
        '<div class="col-3 align-self-center">'+
            '<p class="ps-2 pe-2 text-primary" style="font-Size:46px;">'+hz+'</p>'+
        '</div>'+
        '<div class="col-9">'+
            '<p class="text-white">笔画：<span class="text-light">'+bh+'</span></p>'+
            '<p class="text-white">普通话：<span class="text-light">'+cmn+'</span></p>'+
        '</div>'+
    '</div>'+
    '</div>';

    //卡片尾部
    var cardBody='<div class="card-footer border-0 p-0">'
                        +'<div class="row mb-2 p-0">'+
                                '<div class="col-8"></div>'+
                                '<div class="col-4 p-0">'+
                                    '<a class="btn text-primary p-2"'+'href='+detail_link+'>查看更多</a>'+
                                '</div>'+
                        '</div>'+
                '</div>'

    return cardHead+cardHeader+cardBody+cardEnd;
    }
}


//单音节卡片布局
function ResultPinCard(pinract){

    //音节JSON格式：origin--用户搜索音节；pins--命中音节数组:pin命中具体音节；hanzs--pins下属数组，相同音节的所有汉字;links--pins下属数组，相同音节的所有汉字对应数组;

    //拼音数组,注意拼音数组中具体音节下又附带汉字数组和链接数组;
    var pins=pinract.pins;
    //获取jsons中搜索原始拼音,用origin标识；
    //注意，这里是为了演示才使用转换功能！！！
    var Targetpin=pinract.origin;

    var cardHead='<div class="card m-2 border-0">';


    var cardEnd='</div>'

    //卡片头部
    var cardHeader='<div class="card-header m-2">'+
    '<div class="row">'+
        '<div class="col">'+
            '<h5 class="text-light"><span class="text-primary text-bold">'+Targetpin+'</span>&nbsp;的所有集合</h5>'+
        '</div>'+
    '</div>'+
    '</div>';

    //卡片主体
    var cardBody_head='<div class="card-body pt-0">';
                    //'<div class="row"><div class="col">'
    var cardBody_end='</div>';

    var cardBody_main='';


    if (pins==null){
        cardBody_main+='<div><p class="text-danger pt-2">数据库找不到相关数据哦~</p></div>';
    }else{

        var cycledNums=pins.length
        for(var f=0;f<cycledNums-1;f++){
            for(var k=0;k<cycledNums-f-1;k++){
                var ftr=parseInt(pins[k].pin.charAt(pins[0].pin.length-1))
                var ktr=parseInt(pins[k+1].pin.charAt(pins[0].pin.length-1))
                if(ftr>ktr){
                    var temp=pins[k];
                    var tr=pins[k+1]
                    pins[k+1]=temp
                    pins[k]=tr
                }
            }
        }
        for(j in pins){
            //详情查看链接；默认请求url为：api_url+"?value=空&format=hz"; APP拦截协议链接为app://detail?value=空&format=hz;
            var links=pins[j].links;
            //拼音下所属具体汉字数组
            var hanzs=pins[j].hanzs;
            var tag= pins[j].pin;
            //一键复制功能，复制所有相同读音的汉字，返回分隔开、形式完好的复制文本；
            var copyText_foot='<div class="col offset-9 p-0"><a class="acopy p-0" onclick="returnCopys('+tag+')"><small>复制文本</small></a></div>'
            var cardBody_main_end='</ul>'+copyText_foot+'</div></div></div></div></div>';
            var cardBody_main_head='<div class="accordion mt-2">'+
            '<div class="accordion">'+
                '<div class="accordion-item border-0">'+
                    '<button class="accordion-button btn p-3 collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#'+tag+'" aria-expanded="false">'+
                        '<h6>'+tag+'</h6></button>'+
                        '<div class="accordion-collapse collapse mt-0" id="'+tag+'">'+
                        '<div class="accordion-body p-2"><ul class="list-inline" id="ul_'+tag+'">';
            cardBody_main+=cardBody_main_head+generateList(hanzs,links)+cardBody_main_end;
        }
    }
    

    return cardHead+cardHeader+cardBody_head+cardBody_main+cardBody_end+cardEnd;
}


//生成单个item代码
function generateList(hanzs,qinks){
    var reR='';
    for(s in hanzs){
        reR+='<li class="list-inline-item p-2"><a class="aslink pt-2 pb-2" href="hkapp://search_/?s='+qinks[s]+'  ">'+hanzs[s]+'</a></li>';
    }
    return reR;
}

//复制文本函数
function returnCopys(main){
    console.log('returnCopys argument-->'+main.id);
    var ulq=document.getElementById("ul_"+main.id);
    var lis=ulq.getElementsByTagName('li');
    console.log('getlis-->'+lis.length);
    var copys='复制文本：';

    console.log('detail li\'s txt-->'+lis[0].innerText)
    for(var i=0;i<lis.length;i++){
        copys+=lis[i].innerText+"\t";
    }
    //传入文本数据到APP端复制文本函数
    copytxt(copys);
}



//普通话映射资源
var Dan_tones=[
    "ā","á","ǎ","à","a",
    "ō","ó","ǒ","ò","o",
    "ē","é","ě","è","e",
    "ī","í","ǐ","ì","i",
    "ū","ú","ǔ","ù","u",
    "ǖ","ǘ","ǚ","ǜ",];

var Dan_tones_Al=[
    "a1","a2","a3","a4","a",
    "o1","o2","o3","o4","o",
    "e1","e2","e3","e4","e",
    "i1","i2","i3","i4","i",
    "u1","u2","u3","u4","u",
    "v1","v2","v3","v4",
];

var Double_tones_Al=[
    "ai1","ai2","ai3","ai4","ai",
    "ei1","ei2","ei3","ei4","ei",
    "ao1","ao2","ao3","ao4","ao",
    "ou1","ou2","ou3","ou4","ou",
    "er1","er2","er3","er4","er",
    "y1","y2","y3","y4"];

var Double_tones=[
        "āi","ái","ǎi","ài","ai",
        "ēi","éi","ěi","èi","ei",
        "āo","áo","ǎo","ào","ao",
        "ōu","óu","ǒu","òu","ou",
        "ēr","ér","ěr","èr","er",
        "ǖ","ǘ","ǚ","ǜ",];

var Ang_Tones=[
            "ān","án","ǎn","àn","an",
            "āng","áng","ǎng","àng","ang",
    
            "īn","ín","ǐn","ìn","in",
            "īng","íng","ǐng","ìng","ing",
        
            "ūn","ún","ǔn","ùn","un",
        
            "ēn","én","ěn","èn","en",
            "ēng","éng","ěng","èng","eng",
    
            "ōng","óng","ǒng","òng","ong"];
var Ang_Tones_Al=[
            "an1","an2","an3","an4","an",
            "ang1","ang2","ang3","ang4","ang",
        
            "in1","in2","in3","in4","in",
            "ing1","ing2","ing3","ing4","ing",
        
            "un1","un2","un3","un4","un",
        
            "en1","en2","en3","en4","en",
            "eng1","eng2","eng3","eng4","eng",
            "ong1","ong2","ong3","ong4","ong"];

//转换普通话工具
function convertCPin(wantConvertStr){
        var strb='';
        var nw="";
        var f="";
        
        if(wantConvertStr==null|wantConvertStr==''){

        }
        else {
            h=wantConvertStr.split(" ");
            for (var i = 0; i < h.length; i++) {
                f=h[i];
                for (var sh = 0; sh < Double_tones_Al.length; sh++) {
                    var double_rex=new RegExp(Double_tones_Al[sh]+'$');
                    if (f.match(double_rex)) {
                        //console.log('matched double phonetics-->'+Double_tones_Al[sh]);
                        nw = f.replace(Double_tones_Al[sh], Double_tones[sh]);
                        //这个l为标记符号表示：双元音已经处理；
                        f = "l";
                        continue;
                    }
                }

                var rex=new RegExp(/n[1234]$/);
                var rexh=new RegExp(/ng[1234]$/);
                if (f=="l") {
                    strb+=nw+' ';
                } else if (f.match(rex)||f.match(rexh)) {
                    //console.log('matched -n/-ng!');
                    for (var y = 0; y < Ang_Tones_Al.length; y++) {
                        if (f.endsWith(Ang_Tones_Al[y])) {
                            var w = f.replace(Ang_Tones_Al[y], Ang_Tones[y]);
                            f = w;
                        }
                    }
                    strb+=f+' ';
                } else {
                    for (var j = 0; j < Dan_tones_Al.length; j++) {
                        var dan_regx=new RegExp(Dan_tones_Al[j]+'$');
                        if (f.match(dan_regx)) {
                            //console.log('matched single phonetics!')
                            var jk = f.replace(Dan_tones_Al[j], Dan_tones[j]);
                            f = jk;
                        }
                    }
                    strb+=f+' ';
                }
            }
        }
        return strb;
    }